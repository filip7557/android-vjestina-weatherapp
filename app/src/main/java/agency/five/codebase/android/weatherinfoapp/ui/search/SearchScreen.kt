package agency.five.codebase.android.weatherinfoapp.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchRoute(
    viewModel: SearchViewModel,
    onDoneClick: (Pair<Double, Double>) -> Unit
) {
    val searchViewState: SearchViewState by viewModel.searchViewState.collectAsState()

    SearchScreen(
        searchViewState,
        onDoneClick = {
            onDoneClick( viewModel.onDoneClick() )
        },
        onValueChange = {
            viewModel.onValueChange(it)
        }
    )
}

@Composable
fun SearchScreen(
    searchViewState: SearchViewState,
    onDoneClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    Card(
        shape = AbsoluteRoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
    ) {
        TextField(
            value = searchViewState.value,
            onValueChange = { value ->
                onValueChange(value)
            },
            keyboardActions = KeyboardActions(onDone = { onDoneClick() }),
            label = { Text("Search by city") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(1f)
        )
    }
}
