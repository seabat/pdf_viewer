package dev.seabat.android.composepdfviewer.ui.screen.pdfviewer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.ui.UiStateType
import dev.seabat.android.composepdfviewer.ui.component.WrapLoadingComponent
import dev.seabat.android.composepdfviewer.ui.theme.viewer_background

@Composable
fun PdfViewerScreen(
    modifier: Modifier = Modifier,
    viewModel: PdfViewerViewModel = hiltViewModel(),
    pdf: PdfEntity,
) {
    val uiState by viewModel.uiState.collectAsState()
    PdfViewerScreenContent(
        uiState = uiState,
        readPage = { pageNo -> viewModel.readAhead(pageNo) },
        extractPageCount = { viewModel.extractPageCount() }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PdfViewerScreenContent(
    uiState: PdfViewerUiState,
    modifier: Modifier = Modifier,
    extractPageCount: () -> Int,
    readPage: (pageNo: Int) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            extractPageCount()
        }
    )

    LaunchedEffect(pagerState.currentPage) {
        readPage(pagerState.currentPage)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.background(viewer_background).fillMaxSize()
        ) { pageIndex ->
            uiState.bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp).fillMaxSize().background(Color.White),
                    contentScale = ContentScale.Fit
                )
            }
        }
        if (uiState.state == UiStateType.Loading) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                WrapLoadingComponent()
            }
        }
    }
}