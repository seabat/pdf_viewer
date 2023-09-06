package dev.seabat.android.composepdfviewer.screen.recentness

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RecentnessScreen(
    modifier: Modifier = Modifier,
    viewModel: RecentnessViewModel = RecentnessViewModel(),
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Greeting(
            name = "全てのファイルへ",
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
    dev.seabat.android.composepdfviewer.screen.favorite.Greeting(
        name = "全てのファイルへ",
        onClick = { /* Do nothing */ }
    )
}