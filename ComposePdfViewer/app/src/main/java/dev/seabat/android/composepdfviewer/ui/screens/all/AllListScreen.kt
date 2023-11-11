package dev.seabat.android.composepdfviewer.ui.screens.all

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.seabat.android.composepdfviewer.R
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.ui.components.bottomsheet.BottomSheetMenu
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerAppBar
import dev.seabat.android.composepdfviewer.ui.components.ErrorComponent
import dev.seabat.android.composepdfviewer.ui.components.LoadingComponent
import dev.seabat.android.composepdfviewer.ui.components.PdfListItem
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerBottomNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AllListScreen(
    modifier: Modifier = Modifier,
    viewModel: AllListViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    var selectingPdf by remember { mutableStateOf<PdfEntity?>(null) }
    val hostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.reload()
    }

    if (showSheet) {
        AllListBottomSheetMenu(
            pdf = selectingPdf,
            viewModel = viewModel,
            closeSheet = {
                showSheet = false
            },
            showSnackBar = {
                hostState.showSnackbar(it)
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
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
            goViewer = { pdf ->
                val jsonString = PdfEntity.convertObjectToJson(pdf)
                navController.navigate("pdf_viewer" + "/?pdf=${jsonString}")
            },
            showBottomSheetMenu = { pdf ->
                showSheet = true
                selectingPdf = pdf
            }
        )
    }
}

@Composable
fun AllListBottomSheetMenu(
    pdf: PdfEntity?,
    viewModel: AllListViewModel,
    closeSheet: () -> Unit,
    showSnackBar: suspend (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    BottomSheetMenu(
        onDismiss = {
            closeSheet()
        },
        onFavoriteClick = {
            coroutineScope.launch {
                // NOTE： BottomSheet が閉じるのを待ってから showSheet を false にする
                delay(100)
                closeSheet()
            }
            pdf?.let {
                viewModel.addFavorite(it)
                coroutineScope.launch {
                    showSnackBar("${it.title}${context.resources.getString(R.string.all_add_favorite)}")
                }
            }
        },
        onDeleteClick = {
            coroutineScope.launch {
                // NOTE： BottomSheet が閉じるのを待ってから showSheet を false にする
                delay(100)
                closeSheet()
            }
            pdf?.let {
                viewModel.deletePdfFile(it)
                coroutineScope.launch {
                    showSnackBar("${it.title}${context.resources.getString(R.string.all_delete)}")
                }
                viewModel.reload()
            }
        }
    )
}

@Composable
fun AllListScreenContent(
    uiState: AllListUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    goViewer: (PdfEntity) -> Unit,
    showBottomSheetMenu: (PdfEntity) -> Unit

) {
    when (uiState.state) {
        is ScreenStateType.Loading -> {
            LoadingComponent()
        }
        is ScreenStateType.Loaded -> {
            LazyColumn(modifier) {
                uiState.pdfs.forEach { pdf ->
                    item { PdfListItem(pdf = pdf, onClick = goViewer, onMoreHorizClick = showBottomSheetMenu) }
                    item { HorizontalDivider(Modifier.padding(start = 16.dp, end = 16.dp)) }
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