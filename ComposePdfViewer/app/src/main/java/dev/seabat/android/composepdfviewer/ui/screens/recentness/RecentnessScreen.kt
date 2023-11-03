package dev.seabat.android.composepdfviewer.ui.screens.recentness

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.seabat.android.composepdfviewer.ui.components.LoadingComponent
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.components.ErrorComponent
import dev.seabat.android.composepdfviewer.ui.components.PdfListItem
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerAppBar
import dev.seabat.android.composepdfviewer.ui.screens.PdfViewerBottomNavigation
import java.lang.Exception
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun RecentnessScreen(
    modifier: Modifier = Modifier,
    viewModel: RecentnessViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            PdfViewerAppBar(
                navController = navController
            )
        },
        bottomBar = {
            PdfViewerBottomNavigation(
                navController = navController,
            )
        }
    ) { paddingValues ->
        RecentnessScreenContent(
            uiState = uiState,
            onRefresh = { viewModel.reload() },
            modifier = Modifier.padding(paddingValues),
            onClick = {}
        )
    }
}

@Composable
fun RecentnessScreenContent(
    uiState: RecentnessUiState,
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
                   item { PdfListItem(pdf = pdf, onClick = onClick) }
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
    RecentnessScreenContent(
        uiState = RecentnessUiState(
            state = ScreenStateType.Loaded,
            pdfs = PdfListEntity(
                mutableListOf(
                    PdfEntity("title1", "desc1", "desc1",178, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
                    PdfEntity("title2", "desc2", "desc1",298, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
                    PdfEntity("title3", "desc3", "desc1",587, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
                    PdfEntity("title4", "desc4", "desc1",319, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
                    PdfEntity("title5", "desc5", "desc1",287, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
                )
            )
        ),
        onRefresh = {},
        onClick =  {},
    )
}

@Preview
@Composable
fun `Loading状態のHomeScreenContent`() {
    RecentnessScreenContent(
        uiState = RecentnessUiState(
            state = ScreenStateType.Loading,
            pdfs = PdfListEntity(mutableListOf())
        ),
        onRefresh = {},
        onClick =  {},
    )
}

@Preview
@Composable
fun `Error状態のHomeScreenContent`() {
    RecentnessScreenContent(
        uiState = RecentnessUiState(
            state = ScreenStateType.Error(Exception("エラー内容")),
            pdfs = PdfListEntity(mutableListOf())
        ),
        onRefresh = {},
        onClick =  {},
    )
}
