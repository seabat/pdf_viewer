package dev.seabat.android.composepdfviewer.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.seabat.android.composepdfviewer.R
import timber.log.Timber

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = FavoriteViewModel(),
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = "Aligned to bottom end"
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
