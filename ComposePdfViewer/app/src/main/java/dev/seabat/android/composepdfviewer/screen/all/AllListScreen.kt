package dev.seabat.android.composepdfviewer.screen.all

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AllListScreen(
    modifier: Modifier = Modifier,
    viewModel: AllListViewModel = AllListViewModel(),
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Greeting(
            name = "お気に入りファイルへ",
            onClick = onClick
        )
    }
}

@Composable
fun Greeting(name: String,  onClick: () -> Unit, modifier: Modifier = Modifier) {
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
    dev.seabat.android.composepdfviewer.screen.favorite.Greeting(
        name = "お気に入りファイルへ",
        onClick = { /* Do nothing */ }
    )
}