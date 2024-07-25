package com.selincengiz.ritmo.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selincengiz.ritmo.presentation.Dimens
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.common.CustomSearchBar
import com.selincengiz.ritmo.presentation.home.components.AlbumCard
import com.selincengiz.ritmo.presentation.search.components.ListedRitmo
import com.selincengiz.ritmo.ui.theme.BlueButtonColor

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier, state: SearchState,
    event: (SearchEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1,
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding2))

        CustomSearchBar(
            modifier = Modifier.padding(10.dp),
            text = state.searchQuery,
            glowingColor = BlueButtonColor,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onClick = {},
            onSearch = { event(SearchEvent.SearchRitmo) }
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        state.ritmo?.let {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                contentPadding = PaddingValues(all = ExtraSmallPadding2)
            ) {
                items(count = state.ritmo?.size ?: 0) {
                    state.ritmo?.get(it)?.let { track ->
                        ListedRitmo(trackUI = track) {

                        }
                    }
                }
            }
        }

    }
}


@Preview
@Composable
private fun SearchPrev() {
    SearchScreen(state = SearchState(), event = { SearchEvent.SearchRitmo })
}