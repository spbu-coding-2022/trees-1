package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import arrowIcon
import controller.Controller
import lightColors
import menuItemBackgroundColor
import myTypography
import topAppIconButton

@Composable
fun myTopAppBar(clickButtonsState: List<MutableState<Boolean>>, windowState: WindowState, controller: Controller) {

    //val controller = Controller()
    val showFiles = controller.showFiles()

    val fileName = remember { mutableStateOf("") } // имя файла в диалоге save

    MaterialTheme(
        colorScheme = lightColors,
        typography = myTypography
    ) {
        val buttonState = List(7) { remember { mutableStateOf(false) } }

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

                    deleteDialog(
                        "A",
                        buttonState,
                        clickButtonsState[0]
                    ) // будем иметь имя текущего дерево и передавать его сюды

                    saveDialog(buttonState, clickButtonsState[1], fileName)
                    // после работы в контролере опять поменять filename на ""


                    topAppIconButton(
                        painterResource("/drawable/dir.png"),
                        buttonState,
                        2,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickButtonsState[2]
                    )
                    openMenu(clickButtonsState[2], showFiles)


                    topAppIconButton(
                        rememberVectorPainter(Icons.Outlined.Add),
                        buttonState,
                        3,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickButtonsState[3]
                    )
                    createMenu(clickButtonsState[3], controller)

                }

                Row(horizontalArrangement = Arrangement.End) {

                    topAppIconButton(
                        painterResource("/drawable/minimize.png"),
                        buttonState,
                        4,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickButtonsState[4]
                    )
                    if (clickButtonsState[4].value) {
                        minimizeWindow(windowState)
                        clickButtonsState[4].value = false
                    }

                    topAppIconButton(
                        painterResource("/drawable/maximize.png"),
                        buttonState,
                        5,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickButtonsState[5]
                    )
                    if (clickButtonsState[5].value) {
                        maximizeWindow(windowState)
                        clickButtonsState[5].value = false
                    }

                    topAppIconButton(
                        rememberVectorPainter(Icons.Outlined.Close),
                        buttonState,
                        6,
                        Color(233, 8, 28),
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background,
                        clickButtonsState[6]
                    )

                }

            }


        }

    }
}

@Composable
private fun minimizeWindow(state: WindowState) {
    state.placement = WindowPlacement.Floating
}

@Composable
private fun maximizeWindow(state: WindowState) {
    if (state.placement == WindowPlacement.Maximized) {
        state.placement = WindowPlacement.Floating
    } else {
        state.placement = WindowPlacement.Maximized
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun createMenu(expandedNested: MutableState<Boolean>, controller: Controller) {
    val treesNames = listOf("Red black tree", "AVL tree", "Binary tree")

    val backgroundColorState = List(3) { remember { mutableStateOf(false) } }

    DropdownMenu(
        expanded = expandedNested.value,
        onDismissRequest = { expandedNested.value = false },
        offset = DpOffset(150.dp, 0.dp),
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {

        repeat(3) { index ->
            DropdownMenuItem(text = { Text(treesNames[index]) },
                onClick = {
                    // тут определяем тип дерева и передаем в контроллер
                    controller.createTree("baseName.json")
                    expandedNested.value = false
                },
                modifier = Modifier.onPointerEvent(PointerEventType.Enter) { backgroundColorState[index].value = true }
                    .onPointerEvent(PointerEventType.Exit) { backgroundColorState[index].value = false }
                    .background(color = menuItemBackgroundColor(backgroundColorState[index])))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun openMenu(expandedNested: MutableState<Boolean>, showFiles: List<List<String>>) {
    val treesNames = listOf("Red black tree", "AVL tree", "Binary tree")

    val expandedTreesNames = List(3) { remember { mutableStateOf(false) } }

    val backgroundColorState = List(3) { remember { mutableStateOf(false) } }

    DropdownMenu(
        expanded = expandedNested.value,
        onDismissRequest = { expandedNested.value = !expandedNested.value },
        offset = DpOffset(100.dp, 0.dp),
        modifier = Modifier.width(150.dp).background(MaterialTheme.colorScheme.background)
    ) {
        repeat(3) { index ->
            DropdownMenuItem(
                text = {
                    Text(
                        treesNames[index],
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.height(50.dp),
                        maxLines = 1
                    )
                },
                onClick = {
                    expandedTreesNames[index].value = !expandedTreesNames[index].value
                },
                trailingIcon = { arrowIcon() },
                modifier = Modifier.onPointerEvent(PointerEventType.Enter) { backgroundColorState[index].value = true }
                    .onPointerEvent(PointerEventType.Exit) { backgroundColorState[index].value = false }
                    .background(color = menuItemBackgroundColor(backgroundColorState[index])),
                colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary))
            treesNames(expandedTreesNames[index], expandedNested, showFiles[index], index, 150.dp)
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun treesNames(
    expandedNested: MutableState<Boolean>,
    expandedOpenNested: MutableState<Boolean>,
    showFiles: List<String>,
    treeID: Int,
    offset: Dp
) {

    val dirPath = System.getProperty("user.dir") + "/saved-trees"
    val dirFiles = listOf("$dirPath/RB-trees", "$dirPath/AVL-trees", "$dirPath/BIN-trees")

    val selectedTree = remember { mutableStateOf("") }

    val backgroundColorState = List(showFiles.size) { remember { mutableStateOf(false) } }

    AnimatedVisibility(visible = expandedNested.value) {
        DropdownMenu(
            expanded = expandedNested.value,
            onDismissRequest = { expandedNested.value = false },
            offset = DpOffset(offset, (-50).dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            searchItem(dirFiles[treeID], expandedNested, selectedTree, expandedOpenNested)

            repeat(showFiles.size) { index ->
                DropdownMenuItem(onClick = { /*expandedNested.value = false*/ },
                    text = { Text(showFiles[index]) },
                    modifier = Modifier
                        .onPointerEvent(PointerEventType.Enter) { backgroundColorState[index].value = true }
                        .onPointerEvent(PointerEventType.Exit) { backgroundColorState[index].value = false }
                        .background(color = menuItemBackgroundColor(backgroundColorState[index])),
                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary))
            }

        }
    }

}