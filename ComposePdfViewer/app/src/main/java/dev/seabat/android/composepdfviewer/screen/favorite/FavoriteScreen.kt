package dev.seabat.android.composepdfviewer.screen.favorite

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = FavoriteViewModel(),
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Greeting(
            name = "最近見たファイルへ",
            onClick = onClick
        )
    }
}

@Composable
fun Greeting(name: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    ElevatedButton(onClick = { onClick() }) {
        Text(
            text = "$name!",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting(
        name = "最近見たファイルへ",
        onClick = { /* Do nothing */ }
    )
}
