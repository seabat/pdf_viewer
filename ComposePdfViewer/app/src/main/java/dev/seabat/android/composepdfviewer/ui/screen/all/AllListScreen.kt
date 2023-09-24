package dev.seabat.android.composepdfviewer.ui.screen.all

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.ui.UiStateType
import dev.seabat.android.composepdfviewer.ui.component.ErrorComponent
import dev.seabat.android.composepdfviewer.ui.component.LoadingComponent
import dev.seabat.android.composepdfviewer.ui.screen.recentness.PdfItem

@Composable
fun AllListScreen(
    modifier: Modifier = Modifier,
    viewModel: AllListViewModel,
    navigateToPdfViewer: (PdfEntity) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AllListScreenContent(
        uiState = uiState,
        onRefresh = { viewModel.reload() },
        onClick = {
            viewModel.addRecentness(it) {
                navigateToPdfViewer(it)
            }
        }
    )
}

@Composable
fun AllListScreenContent(
    uiState: AllListUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (PdfEntity) -> Unit,
) {
    when (uiState.state) {
        is UiStateType.Loading -> {
            LoadingComponent()
        }
        is UiStateType.Loaded -> {
            LazyColumn(
//            modifier.fillMaxSize()
            ) {
                uiState.pdfs.forEach { pdf ->
                    item { PdfItem(pdf = pdf, onClick = onClick) }
                    item { Divider(Modifier.padding(start = 16.dp, end = 16.dp)) }
                }
            }
        }
        is UiStateType.Error -> {
            ErrorComponent(uiState.state.e) {
                onRefresh()
            }
        }
    }
}