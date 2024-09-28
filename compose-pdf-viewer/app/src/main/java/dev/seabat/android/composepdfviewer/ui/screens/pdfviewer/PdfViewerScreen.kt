package dev.seabat.android.composepdfviewer.ui.screens.pdfviewer

import android.app.Activity
import android.graphics.Insets
import android.view.WindowInsets
import android.view.WindowMetrics
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.seabat.android.composepdfviewer.R
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerAppBar
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerBottomNavigation
import dev.seabat.android.composepdfviewer.ui.theme.viewer_background

const val IMAGE_VIEW_PADDING_SIZE = 16

@Composable
fun PdfViewerScreen(
    modifier: Modifier = Modifier,
    viewModel: PdfViewerViewModel = hiltViewModel<PdfViewerViewModel>(),
    navController: NavHostController,
    pdf: PdfResourceEntity
) {
    val activity = LocalContext.current as ComponentActivity

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
        PdfViewerScreenContent(
            modifier = modifier.padding(paddingValues),
            readPage = { pageIndex ->
                viewModel.readAhead(
                    pageIndex,
                    getImageViewDimensions(activity)
                )
            },
            onDoubleClick = { viewModel.changePageSize(getImageViewDimensions(activity)) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PdfViewerScreenContent(
    modifier: Modifier = Modifier,
    viewModel: PdfViewerViewModel = hiltViewModel<PdfViewerViewModel>(),
    readPage: (pageIndex: Int) -> Unit,
    onDoubleClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val renderedBitmaps by viewModel.renderedBitmaps.collectAsState()

    val pagerState =
        rememberPagerState(
            initialPage = 0,
            pageCount = { uiState.totalPageCount }
        )

    Box(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .background(viewer_background)
                .fillMaxSize()
        ) { pageIndex ->
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
                if (renderedBitmaps.contains(pageIndex)) {
                    renderedBitmaps.get(pageIndex)?.let {
                        Image(
                            bitmap = it.bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.background(Color.White),
                            contentScale = ContentScale.None
                        )
                    }
                } else {
                    readPage(pageIndex)
                    // ページが読み込み中の場合はプレースホルダーを表示
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

/**
 * Pager に配置可能な Image の最大サイズを取得する
 */
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
