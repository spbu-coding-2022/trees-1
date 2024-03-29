package treeApp.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import treeApp.controller.Controller


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteDialog(
    hoverButtonState: MutableState<Boolean>,
    clickButtonsState: MutableState<Boolean>,
    controller: treeApp.controller.Controller,
    activeTree: MutableState<Boolean>,
    deleteTreeState: MutableState<Boolean>
) {

    val deleteDialogState = remember { mutableStateOf(true) }

    if (clickButtonsState.value) {
        AlertDialog(
            backgroundColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { clickButtonsState.value = false },
            text = {
                Text(
                    text = DeleteDialogText(activeTree, controller.tree?.name ?: ""),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            buttons = {

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    BottomButtons(deleteDialogState, clickButtonsState, "Cancel", "Delete")
                }
            }
        )
    }

    if (!deleteDialogState.value && activeTree.value) {
        deleteTreeState.value = true
        deleteDialogState.value = true
        activeTree.value = false
    }

    TopAppIconButton(
        rememberVectorPainter(Icons.Outlined.Delete),
        hoverButtonState,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.background,
        clickButtonsState
    )

}

@Composable
fun DeleteDialogText(activeTree: MutableState<Boolean>, treeName: String): String {
    return if (activeTree.value) {
        "Are you sure you want to delete $treeName ?"
    } else {
        "Nothing to delete"
    }

}
