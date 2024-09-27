package dev.seabat.android.composepdfviewer.ui.screens.pdfviewer

import android.app.Activity
import android.graphics.Insets
import android.os.Build
import android.view.WindowInsets
import android.view.WindowMetrics
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.ui.components.LoadingComponent
import dev.seabat.android.composepdfviewer.ui.components.WrapLoadingComponent
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerAppBar
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerBottomNavigation
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
import dev.seabat.android.composepdfviewer.ui.theme.viewer_background

const val IMAGE_VIEW_PADDING_SIZE = 16

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun PdfViewerScreen(
    modifier: Modifier = Modifier,
    viewModel: PdfViewerViewModel = hiltViewModel<PdfViewerViewModel>(),
    navController: NavHostController,
    pdf: PdfResourceEntity
) {
    val activity = LocalContext.current as ComponentActivity
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.addRecentPdf(pdf)
        viewModel.createRendererAndExtractPageCount(pdf.pathString)
    }

    Scaffold(
        topBar = {
            PdfViewerAppBar(
                navController = navController
            )
        },
        bottomBar = {
            PdfViewerBottomNavigation(
                navController = navController
            )
        }
    ) { paddingValues ->
        when (uiState.state) {
            is ScreenStateType.Error -> {
                // TODO: Error Screen
            }
            is ScreenStateType.Loading -> {
                LoadingComponent()
            }
            is ScreenStateType.Loaded -> {
                PdfViewerScreenContent(
                    uiState = uiState,
                    modifier = modifier.padding(paddingValues),
                    readPage = { pageNo ->
                        viewModel.readAhead(
                            pageNo,
                            getImageViewDimensions(
                                activity
                            )
                        )
                    },
                    onDoubleClick = { viewModel.changePageSize(getImageViewDimensions(activity)) }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PdfViewerScreenContent(
    uiState: PdfViewerUiState,
    modifier: Modifier = Modifier,
    readPage: (pageNo: Int) -> Unit,
    onDoubleClick: () -> Unit
) {
    val pagerState =
        rememberPagerState(
            initialPage = 0,
            pageCount = { uiState.totalPageCount }
        )

    LaunchedEffect(pagerState.currentPage) {
        readPage(pagerState.currentPage)
    }

    Box(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .background(viewer_background)
                .fillMaxSize()
        ) { pageIndex ->
            uiState.bitmap?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(IMAGE_VIEW_PADDING_SIZE.dp)
                        .fillMaxSize()
                        .background(viewer_background)
                        .verticalScroll(rememberScrollState())
                        .horizontalScroll(rememberScrollState())
                        .combinedClickable(
                            onClick = {},
                            onDoubleClick = {
                                onDoubleClick()
                            },
                            onLongClick = {}
                        )
                ) {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.background(Color.White),
                        contentScale = ContentScale.None
                    )
                }
            }
        }
        if (uiState.state == ScreenStateType.Loading) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                WrapLoadingComponent()
            }
        }
    }
}

/**
 * Pager に配置可能な Image の最大サイズを取得する
 */
@RequiresApi(Build.VERSION_CODES.R)
private fun getImageViewDimensions(activity: Activity): Dimensions {
    val windowMetrics: WindowMetrics = activity.windowManager.currentWindowMetrics
    val insets: Insets =
        windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())

    val dp: Float = activity.resources.displayMetrics.density

    val screenWidth = windowMetrics.bounds.width()
    val screenHeight = windowMetrics.bounds.height()
    val statusBar: Int = insets.top
    val navigationBar: Int = insets.bottom

    val horizontalPaddingSize = (IMAGE_VIEW_PADDING_SIZE * 2 * dp).toInt()
    val verticalPaddingSize = (IMAGE_VIEW_PADDING_SIZE * 2 * dp).toInt()
    val topAppBarHeight = (56 * dp).toInt() // Scaffold のデフォルトの topAppBar の height は 56

    return Dimensions(
        screenWidth - horizontalPaddingSize,
        screenHeight - verticalPaddingSize - statusBar - topAppBarHeight
    )
}
