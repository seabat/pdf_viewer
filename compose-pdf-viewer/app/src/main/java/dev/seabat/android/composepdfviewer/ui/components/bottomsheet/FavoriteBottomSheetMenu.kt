package dev.seabat.android.composepdfviewer.ui.components.bottomsheet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.seabat.android.composepdfviewer.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteBottomSheetMenu(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onDeleteFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
        ) {
            FavoriteMenuItem(
                drawableRes = R.drawable.baseline_remove_circle_outline_24,
                stringRes = R.string.menu_delete_favorite,
            ) {
                coroutineScope.launch { modalBottomSheetState.hide() }
                onDeleteFavoriteClick()
            }
            FavoriteMenuItem(
                drawableRes = R.drawable.baseline_delete_24,
                stringRes = R.string.menu_delete
            ) {
                onDeleteClick()
            }
        }
    }
}

@Composable
private fun FavoriteMenuItem(
    @DrawableRes drawableRes: Int,
    @StringRes stringRes: Int,
    modifier: Modifier = Modifier,
    onClick : () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Image(painter = painterResource(id = drawableRes), contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(id = stringRes))
    }
}
@Preview
@Composable
private fun FavoriteMenuItemPreview() {
    FavoriteMenuItem(R.drawable.baseline_delete_24, R.string.menu_delete) {}
}

