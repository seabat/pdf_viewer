package dev.seabat.android.composepdfviewer.ui.screens.favorite

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.seabat.android.composepdfviewer.R
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.components.ErrorComponent
import dev.seabat.android.composepdfviewer.ui.components.LoadingComponent
import dev.seabat.android.composepdfviewer.ui.components.PdfListItem
import dev.seabat.android.composepdfviewer.ui.components.bottomsheet.FavoriteBottomSheetMenu
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerAppBar
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerBottomNavigation
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
import dev.seabat.android.composepdfviewer.utils.getNowTimeStamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel<FavoriteViewModel>(),
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
        ScreenBottomSheetMenu(
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
        topBar = {
            PdfViewerAppBar(
                navController = navController,
                onPdfImported = { pdf ->
                    viewModel.reload()
                    val jsonString = PdfEntity.convertObjectToJson(pdf)
                    navController.navigate("pdf_viewer" + "/?pdf=$jsonString")
                }
            )
        },
        bottomBar = {
            PdfViewerBottomNavigation(
                navController = navController
            )
        }
    ) { paddingValues ->
        ScreenContent(
            uiState = uiState,
            onRefresh = { viewModel.reload() },
            modifier = modifier.padding(paddingValues),
            goViewer = { pdf ->
                val jsonString = PdfEntity.convertObjectToJson(pdf)
                navController.navigate("pdf_viewer" + "/?pdf=$jsonString")
            },
            showBottomSheetMenu = { pdf ->
                showSheet = true
                selectingPdf = pdf
            }
        )
    }
}

@Composable
private fun ScreenBottomSheetMenu(
    pdf: PdfEntity?,
    viewModel: FavoriteViewModel,
    closeSheet: () -> Unit,
    showSnackBar: suspend (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    FavoriteBottomSheetMenu(
        onDismiss = {
            closeSheet()
        },
        onDeleteFavoriteClick = {
            coroutineScope.launch {
                // NOTE： BottomSheet が閉じるのを待ってから showSheet を false にする
                delay(100)
                closeSheet()
            }
            pdf?.let {
                viewModel.deleteFavorite(it)
                coroutineScope.launch {
                    showSnackBar(
                        "${it.title}${context.resources.getString(R.string.all_add_favorite)}"
                    )
                }
                viewModel.reload()
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
private fun ScreenContent(
    uiState: FavoriteUiState,
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
                    item {
                        PdfListItem(
                            pdf = pdf,
                            onClick = goViewer,
                            onMoreHorizClick = showBottomSheetMenu
                        )
                    }
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

@Preview
@Composable
fun `Loaded状態`() {
    ScreenContent(
        uiState =
        FavoriteUiState(
            state = ScreenStateType.Loaded,
            pdfs =
            PdfListEntity(
                mutableListOf(
                    PdfEntity(
                        "title1",
                        "desc1",
                        "desc1",
                        178,
                        getNowTimeStamp(),
                        getNowTimeStamp()
                    ),
                    PdfEntity(
                        "title2",
                        "desc2",
                        "desc1",
                        298,
                        getNowTimeStamp(),
                        getNowTimeStamp()
                    ),
                    PdfEntity(
                        "title3",
                        "desc3",
                        "desc1",
                        587,
                        getNowTimeStamp(),
                        getNowTimeStamp()
                    ),
                    PdfEntity(
                        "title4",
                        "desc4",
                        "desc1",
                        319,
                        getNowTimeStamp(),
                        getNowTimeStamp()
                    ),
                    PdfEntity(
                        "title5",
                        "desc5",
                        "desc1",
                        287,
                        getNowTimeStamp(),
                        getNowTimeStamp()
                    )
                )
            )
        ),
        onRefresh = {},
        goViewer = {},
        showBottomSheetMenu = {}
    )
}

@Preview
@Composable
fun `Loading状態`() {
    ScreenContent(
        uiState =
        FavoriteUiState(
            state = ScreenStateType.Loading,
            pdfs = PdfListEntity(mutableListOf())
        ),
        onRefresh = {},
        goViewer = {},
        showBottomSheetMenu = {}
    )
}

@Preview
@Composable
fun `Error状態`() {
    ScreenContent(
        uiState =
        FavoriteUiState(
            state = ScreenStateType.Error(Exception("エラー内容")),
            pdfs = PdfListEntity(mutableListOf())
        ),
        onRefresh = {},
        goViewer = {},
        showBottomSheetMenu = {}
    )
}
