package dev.seabat.android.composepdfviewer.ui.screens.recent

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.seabat.android.composepdfviewer.ui.components.LoadingComponent
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.components.ErrorComponent
import dev.seabat.android.composepdfviewer.ui.components.PdfListItem
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerAppBar
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerBottomNavigation
import dev.seabat.android.composepdfviewer.utils.getNowTimeStamp
import java.lang.Exception

@Composable
fun RecentScreen(
    modifier: Modifier = Modifier,
    viewModel: RecentViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reload()
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
        RecentScreenContent(
            uiState = uiState,
            onRefresh = { viewModel.reload() },
            modifier = Modifier.padding(paddingValues),
            onClick = { pdf ->
                val jsonString = PdfEntity.convertObjectToJson(pdf)
                navController.navigate("pdf_viewer" + "/?pdf=${jsonString}")
            },
            onMoreHorizClick = {

            }
        )
    }
}

@Composable
fun RecentScreenContent(
    uiState: RecentUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (PdfEntity) -> Unit,
    onMoreHorizClick: (PdfEntity) -> Unit
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


@Preview
@Composable
fun `Loaded状態のHomeScreenContent`() {
    RecentScreenContent(
        uiState = RecentUiState(
            state = ScreenStateType.Loaded,
            pdfs = PdfListEntity(
                mutableListOf(
                    PdfEntity("title1", "desc1", "desc1",178, getNowTimeStamp(), getNowTimeStamp()),
                    PdfEntity("title2", "desc2", "desc1",298, getNowTimeStamp(), getNowTimeStamp()),
                    PdfEntity("title3", "desc3", "desc1",587, getNowTimeStamp(), getNowTimeStamp()),
                    PdfEntity("title4", "desc4", "desc1",319, getNowTimeStamp(), getNowTimeStamp()),
                    PdfEntity("title5", "desc5", "desc1",287, getNowTimeStamp(), getNowTimeStamp())
                )
            )
        ),
        onRefresh = {},
        onClick =  {},
        onMoreHorizClick = {}
    )
}

@Preview
@Composable
fun `Loading状態のHomeScreenContent`() {
    RecentScreenContent(
        uiState = RecentUiState(
            state = ScreenStateType.Loading,
            pdfs = PdfListEntity(mutableListOf())
        ),
        onRefresh = {},
        onClick =  {},
        onMoreHorizClick = {}
    )
}

@Preview
@Composable
fun `Error状態のHomeScreenContent`() {
    RecentScreenContent(
        uiState = RecentUiState(
            state = ScreenStateType.Error(Exception("エラー内容")),
            pdfs = PdfListEntity(mutableListOf())
        ),
        onRefresh = {},
        onClick =  {},
        onMoreHorizClick = {}
    )
}
