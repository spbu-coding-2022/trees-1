package treeApp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import treeApp.controller.Controller
import treeApp.lightColors
import treeApp.myTypography

@Composable
fun MyTopAppBar(
    clickDeleteButton: MutableState<Boolean>,
    clickSaveButton: MutableState<Boolean>,
    clickOpenButton: MutableState<Boolean>,
    clickCreateButton: MutableState<Boolean>,
    clickMinimizeButton: MutableState<Boolean>,
    clickMaximizeButton: MutableState<Boolean>,
    clickCloseButton: MutableState<Boolean>,
    windowState: WindowState,
    controller: Controller,
    activeTree: MutableState<Boolean>,
    deleteTreeState: MutableState<Boolean>,
    openTreeState: MutableState<Boolean>,
    createTreeState: MutableState<Boolean>
) {

    val treesNames = controller.getSavedTreesNames()

    val selectFileName = remember { mutableStateOf("") }

    MaterialTheme(
        colorScheme = lightColors,
        typography = myTypography
    ) {

        val hoverDeleteButton: MutableState<Boolean> = remember { mutableStateOf(false) }
        val hoverSaveButton: MutableState<Boolean> = remember { mutableStateOf(false) }
        val hoverOpenButton: MutableState<Boolean> = remember { mutableStateOf(false) }
        val hoverCreateButton: MutableState<Boolean> = remember { mutableStateOf(false) }
        val hoverMinimizeButton: MutableState<Boolean> = remember { mutableStateOf(false) }
        val hoverMaximizeButton: MutableState<Boolean> = remember { mutableStateOf(false) }
        val hoverCloseButton: MutableState<Boolean> = remember { mutableStateOf(false) }

        TopAppBar(
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.height(50.dp),
            contentPadding = PaddingValues(0.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    DeleteDialog(
                        hoverDeleteButton,
                        clickDeleteButton,
                        controller,
                        activeTree,
                        deleteTreeState
                    )

                    SaveDialog(hoverSaveButton, clickSaveButton, selectFileName, controller, activeTree)

                    TopAppIconButton(
                        painterResource("/drawable/dir.png"),
                        hoverOpenButton,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickOpenButton
                    )
                    OpenMenu(clickOpenButton, treesNames, activeTree, openTreeState, controller)

                    TopAppIconButton(
                        rememberVectorPainter(Icons.Outlined.Add),
                        hoverCreateButton,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickCreateButton
                    )
                    CreateMenu(clickCreateButton, controller, activeTree, createTreeState)
                }

                Row(horizontalArrangement = Arrangement.End) {

                    TopAppIconButton(
                        painterResource("/drawable/minimize.png"),
                        hoverMinimizeButton,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickMinimizeButton
                    )
                    if (clickMinimizeButton.value) {
                        MinimizeWindow(windowState)
                        clickMinimizeButton.value = false
                    }

                    TopAppIconButton(
                        painterResource("/drawable/maximize.png"),
                        hoverMaximizeButton,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickMaximizeButton
                    )
                    if (clickMaximizeButton.value) {
                        MaximizeWindow(windowState)
                        clickMaximizeButton.value = false
                    }

                    TopAppIconButton(
                        rememberVectorPainter(Icons.Outlined.Close),
                        hoverCloseButton,
                        Color(233, 8, 28),
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickCloseButton
                    )

                }

            }

        }
    }
}

@Composable
private fun MinimizeWindow(state: WindowState) {
    state.isMinimized = !state.isMinimized
}

@Composable
private fun MaximizeWindow(state: WindowState) {
    if (state.placement == WindowPlacement.Maximized) {
        state.placement = WindowPlacement.Floating
    } else {
        state.placement = WindowPlacement.Maximized
    }
}
