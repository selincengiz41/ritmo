package com.selincengiz.ritmo.presentation.profile.components

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.presentation.profile.ProfileEvent
import com.selincengiz.ritmo.presentation.profile.ProfileState

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ProfileImagePicker(
    modifier: Modifier = Modifier,
    event: (ProfileEvent) -> Unit,
    state: ProfileState
) {
    val context = LocalContext.current
    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val name = context.packageName
                context.grantUriPermission(name, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                event(ProfileEvent.PickImage(uri))
            }
        }
    )

    // Permission request launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { isGranted ->
            if (isGranted.values.all { true }) {
                imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                Toast.makeText(
                    context,
                    "To change your profile photo, you should grant the permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .clip(CircleShape)
                .clickable {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        // For Android 14+, handle the new selected photo access permission
                        permissionLauncher.launch(
                            arrayOf(
                                READ_MEDIA_IMAGES,
                                READ_MEDIA_VISUAL_USER_SELECTED
                            )
                        )
                    } else {
                        // For older versions, use the standard permission
                        permissionLauncher.launch(arrayOf(READ_EXTERNAL_STORAGE))
                    }
                },
            model = state.image,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.ic_pause),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onError = { it -> Log.i("aut", it.result.throwable.message ?: "") }
        )
    }
}