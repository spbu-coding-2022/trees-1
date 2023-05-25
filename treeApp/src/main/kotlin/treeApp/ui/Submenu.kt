package treeApp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import treeApp.controller.Controller

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateMenu(
    expandedNested: MutableState<Boolean>,
    controller: treeApp.controller.Controller,
    activeTree: MutableState<Boolean>,
    createTreeState: MutableState<Boolean>
) {

    val trees = listOf("Red black tree", "AVL tree", "Binary tree")
    val defaultTreesNames = listOf("rbTree", "avlTree", "binTree")

    val backgroundColorState = List(3) { remember { mutableStateOf(false) } }

    DropdownMenu(
        expanded = expandedNested.value,
        onDismissRequest = { expandedNested.value = false },
        offset = DpOffset(150.dp, 0.dp),
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {

        repeat(3) { index ->
            DropdownMenuItem(text = { Text(trees[index]) },
                onClick = {
                    controller.createTree(defaultTreesNames[index], index)
                    createTreeState.value = true
                    activeTree.value = true
                    expandedNested.value = false
                },
                modifier = Modifier
                    .onPointerEvent(PointerEventType.Enter) { backgroundColorState[index].value = true }
                    .onPointerEvent(PointerEventType.Exit) { backgroundColorState[index].value = false }
                    .background(color = MenuItemBackgroundColor(backgroundColorState[index])))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OpenMenu(
    expandedNested: MutableState<Boolean>,
    treesNames: List<List<String>>,
    activeTree: MutableState<Boolean>,
    openTreeState: MutableState<Boolean>,
    controller: treeApp.controller.Controller
) {
    val trees = listOf("Red black tree", "AVL tree", "Binary tree")

    val expandedTreesNames = List(3) { remember { mutableStateOf(false) } } // !!
    val backgroundColorState = List(3) { remember { mutableStateOf(false) } } // !!

    val selectedTreeName = remember { mutableStateOf("") }
    val selectedTreeID = remember { mutableStateOf(0) }

    DropdownMenu(
        expanded = expandedNested.value,
        onDismissRequest = {
            expandedNested.value = !expandedNested.value
        },
        offset = DpOffset(100.dp, 0.dp),
        modifier = Modifier.width(150.dp).background(MaterialTheme.colorScheme.background)
    ) {
        repeat(3) { index ->
            DropdownMenuItem(
                text = {
                    Text(
                        trees[index],
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.height(50.dp),
                        maxLines = 1
                    )
                },
                onClick = {
                    expandedTreesNames[index].value = !expandedTreesNames[index].value
                    selectedTreeID.value = index
                },
                trailingIcon = { ArrowIcon() },
                modifier = Modifier.onPointerEvent(PointerEventType.Enter) { backgroundColorState[index].value = true }
                    .onPointerEvent(PointerEventType.Exit) { backgroundColorState[index].value = false }
                    .background(color = MenuItemBackgroundColor(backgroundColorState[index])),
                colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary))

            SelectTree(
                expandedTreesNames[index],
                expandedNested,
                treesNames[index],
                selectedTreeID,
                150.dp,
                selectedTreeName,
                activeTree,
                openTreeState,
                controller
            )
        }
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SelectTree(
    expandedNested: MutableState<Boolean>,
    expandedOpenNested: MutableState<Boolean>,
    treesNames: List<String>,
    selectedTreeID: MutableState<Int>,
    offset: Dp,
    selectedTreeName: MutableState<String>,
    activeTree: MutableState<Boolean>,
    openTreeState: MutableState<Boolean>,
    controller: treeApp.controller.Controller
) {

    val dirPath = System.getProperty("user.dir") + "/saved-trees" //  !!
    val dirFiles = listOf("$dirPath/RB-trees", "$dirPath/AVL-trees", "$dirPath/BIN-trees") // !!

    val backgroundColorState = List(treesNames.size) { remember { mutableStateOf(false) } } // !!

    AnimatedVisibility(visible = expandedNested.value) {
        DropdownMenu(
            expanded = expandedNested.value,
            onDismissRequest = { expandedNested.value = false },
            offset = DpOffset(offset, (-50).dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            SearchItem(dirFiles[selectedTreeID.value], expandedNested, selectedTreeName, expandedOpenNested)

            repeat(treesNames.size) { index ->
                DropdownMenuItem(onClick = {
                    selectedTreeName.value = treesNames[index]
                    expandedNested.value = false
                    expandedOpenNested.value = false
                },
                    text = { Text(treesNames[index]) },
                    modifier = Modifier
                        .onPointerEvent(PointerEventType.Enter) { backgroundColorState[index].value = true }
                        .onPointerEvent(PointerEventType.Exit) { backgroundColorState[index].value = false }
                        .background(color = MenuItemBackgroundColor(backgroundColorState[index])),
                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary))
            }
        }
    }

    if (selectedTreeName.value.isNotEmpty()) {
        controller.createTree(selectedTreeName.value, selectedTreeID.value)
        openTreeState.value = true
        activeTree.value = true
        selectedTreeName.value = ""
    }

}
