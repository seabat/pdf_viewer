package dev.seabat.android.composepdfviewer.ui.screens.all

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.ui.components.bottomsheet.BottomSheetMenu
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerAppBar
import dev.seabat.android.composepdfviewer.ui.components.ErrorComponent
import dev.seabat.android.composepdfviewer.ui.components.LoadingComponent
import dev.seabat.android.composepdfviewer.ui.components.PdfListItem
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerBottomNavigation

@Composable
fun AllListScreen(
    modifier: Modifier = Modifier,
    viewModel: AllListViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.reload()
    }

    if (showSheet) {
        BottomSheetMenu(
            onDismiss = { showSheet = false },
            onFavoriteClick = {},
            onDeleteClick = {}
        )
    }

    Scaffold(
        topBar = {
            PdfViewerAppBar(
                navController = navController,
                onPdfImported = { pdf ->
                    viewModel.reload()
                    val jsonString = PdfEntity.convertObjectToJson(pdf)
                    navController.navigate("pdf_viewer" + "/?pdf=${jsonString}")
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
            onClick = { pdf ->
                val jsonString = PdfEntity.convertObjectToJson(pdf)
                navController.navigate("pdf_viewer" + "/?pdf=${jsonString}")
            },
            onMoreHorizClick = { showSheet = true }
        )
    }
}

@Composable
fun AllListScreenContent(
    uiState: AllListUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (PdfEntity) -> Unit,
    onMoreHorizClick: () -> Unit

) {
    when (uiState.state) {
        is ScreenStateType.Loading -> {
            LoadingComponent()
        }
        is ScreenStateType.Loaded -> {
            LazyColumn(modifier) {
                uiState.pdfs.forEach { pdf ->
                    item { PdfListItem(pdf = pdf, onClick = onClick, onMoreHorizClick = onMoreHorizClick) }
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