package dev.seabat.android.composepdfviewer.ui.screens.all

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerAppBar
import dev.seabat.android.composepdfviewer.ui.components.ErrorComponent
import dev.seabat.android.composepdfviewer.ui.components.LoadingComponent
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerBottomNavigation
import dev.seabat.android.composepdfviewer.ui.screens.recentness.PdfItem

@Composable
fun AllListScreen(
    modifier: Modifier = Modifier,
    viewModel: AllListViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            PdfViewerAppBar(
                navController = navController,
                onPdfImported = { pdf ->
                    navController.navigate("pdf_viewer" + "/?pdf=" + "pdfのJSONデータ")
                }
            )
        },
        bottomBar = {
            PdfViewerBottomNavigation(
                navController = navController,
            )
        }
    ) { paddingValues ->
        AllListScreenContent(
            uiState = uiState,
            onRefresh = { viewModel.reload() },
            modifier = modifier.padding(paddingValues),
            onClick = {
                viewModel.addRecentness(it) {
                    navController.navigate("pdf_viewer" + "/?pdf=" + "pdfのJSONデータ")
                }
            }
        )
    }
}

@Composable
fun AllListScreenContent(
    uiState: AllListUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (PdfEntity) -> Unit,
) {
    when (uiState.state) {
        is ScreenStateType.Loading -> {
            LoadingComponent()
        }
        is ScreenStateType.Loaded -> {
            LazyColumn(modifier) {
                uiState.pdfs.forEach { pdf ->
                    item { PdfItem(pdf = pdf, onClick = onClick) }
                    item { Divider(Modifier.padding(start = 16.dp, end = 16.dp)) }
                }
            }
        }
        is ScreenStateType.Error -> {
            ErrorComponent(uiState.state.e) {
                onRefresh()
            }
        }
    }
}