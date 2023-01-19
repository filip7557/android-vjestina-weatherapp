package agency.five.codebase.android.weatherinfoapp.ui.search

import agency.five.codebase.android.weatherinfoapp.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchRoute(
    viewModel: SearchViewModel,
    onDoneClick: (Pair<Double, Double>) -> Unit
) {
    val searchViewState: SearchViewState by viewModel.searchViewState.collectAsState()

    SearchScreen(
        searchViewState,
        onDoneClick = {
            onDoneClick(viewModel.onDoneClick())
        },
        onValueChange = {
            viewModel.onValueChange(it)
        },
        viewModel.isHomeEmpty()
    )
}

@Composable
fun SearchScreen(
    searchViewState: SearchViewState,
    onDoneClick: () -> Unit,
    onValueChange: (String) -> Unit,
    isHomeEmpty: Boolean
) {
    Column {
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
        if (isHomeEmpty) {
            Box(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = stringResource(id = R.string.homeEmptyText),
                    fontSize = 20.sp
                )
            }
        }
    }
}
