package com.selincengiz.ritmo.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selincengiz.ritmo.presentation.search.SearchEvent
import com.selincengiz.ritmo.presentation.search.SearchState

@Composable
fun SingleSelectionCheckbox(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    onCheckedChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                checked = state.selected == 1,
                onCheckedChange = {
                    if (it) event(SearchEvent.Filter(1)) else event(SearchEvent.Filter(0))
                    onCheckedChange(state.selected)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Tracks", color = Color.White)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                checked = state.selected == 2,
                onCheckedChange = {
                    if (it) event(SearchEvent.Filter(2)) else event(SearchEvent.Filter(0))
                    onCheckedChange(state.selected)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Albums", color = Color.White)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                checked = state.selected == 3,
                onCheckedChange = {
                    if (it) event(SearchEvent.Filter(3)) else event(SearchEvent.Filter(0))
                    onCheckedChange(state.selected)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Artists", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SingleSelectionCheckboxPreview() {
    SingleSelectionCheckbox(onCheckedChange = {}, state = SearchState(), event = {})
}