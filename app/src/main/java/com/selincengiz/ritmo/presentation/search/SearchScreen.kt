package com.selincengiz.ritmo.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.common.ListRitmo
import com.selincengiz.ritmo.presentation.search.components.CustomSearchBar
import com.selincengiz.ritmo.presentation.common.handlePagingResultTrack
import com.selincengiz.ritmo.presentation.search.components.SingleSelectionCheckbox
import com.selincengiz.ritmo.ui.theme.BlueButtonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToPlayer: (List<TrackUI?>, Int) -> Unit
) {
    var historyVisibility by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1,
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 20.dp)
                .align(Alignment.End)
                .clickable { showBottomSheet = !showBottomSheet },
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "filter",
            tint = Color.White
        )

        Spacer(modifier = Modifier.height(ExtraSmallPadding2))

        CustomSearchBar(
            modifier = Modifier.padding(10.dp),
            text = state.searchQuery,
            glowingColor = BlueButtonColor,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onClick = { historyVisibility = true },
            onSearch = {
                historyVisibility = false
                event(SearchEvent.SearchRitmo)
            }
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        if (historyVisibility) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                contentPadding = PaddingValues(all = ExtraSmallPadding2)
            ) {
                items(count = state.searchHistory.size) {
                    Row(modifier = Modifier.clickable {
                        event(SearchEvent.UpdateSearchQuery(state.searchHistory[it]))
                        historyVisibility = false
                        event(SearchEvent.SearchRitmo)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(MediumPadding1))
                        Text(
                            text = state.searchHistory[it],
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
        } else {
            state.ritmo?.let {
                val handlePagingResult =
                    handlePagingResultTrack(
                        tracks = it.collectAsLazyPagingItems(),
                        title = "search"
                    )
                if (handlePagingResult) {
                    ListRitmo(
                        trackUI = it.collectAsLazyPagingItems(),
                        navigateToPlayer = { list, index -> navigateToPlayer(list, index) })
                }
            }
        }



        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
            ) {

                SingleSelectionCheckbox(
                    state = state,
                    event = event
                )
            }
        }
    }
}
