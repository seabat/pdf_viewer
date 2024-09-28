package dev.seabat.android.composepdfviewer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingComponent(modifier: Modifier = Modifier) {
    Box(
        modifier.fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Preview
@Composable
fun PreviewLoadingComponent() {
    Scaffold { paddingValues ->
        LoadingComponent(
            Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}