package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import lightColors
import java.awt.Dimension
import java.io.File

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchItem(
    dirPath: String,
    expandedNested: MutableState<Boolean>,
    selectedTree: MutableState<String>,
    expandedOpenNested: MutableState<Boolean>
) {

    val dialogState = remember { mutableStateOf(false) }
    val minWidth = remember { mutableStateOf(200) }
    var pressedState by remember { mutableStateOf(false) }
    val backgroundColorState = remember { mutableStateOf(false) }
    val enabledButtonIndex = remember { mutableStateOf(-1) }

    val files = findFiles(dirPath)

    if (dialogState.value) {
        MaterialTheme(colorScheme = lightColors) {
            Dialog(
                onCloseRequest = {
                    dialogState.value = false
                    expandedOpenNested.value = false
                    selectedTree.value =
                        if (enabledButtonIndex.value >= 0) files[enabledButtonIndex.value] else ""
                }, visible = dialogState.value,
                content = {
                    WindowDraggableArea {
                        this.window.minimumSize = Dimension(minWidth.value, 150)
                        CompleteDialogContent("Open file", dialogState, dirPath, minWidth, files, enabledButtonIndex)
                    }
                },
                undecorated = true,
                state = rememberDialogState(
                    position = WindowPosition(Alignment.Center),
                    size = DpSize(450.dp, 450.dp)
                )
            )

        }
    } else if (pressedState){
        expandedNested.value = false
        expandedOpenNested.value = false
        selectedTree.value =
            if (enabledButtonIndex.value >= 0) files[enabledButtonIndex.value] else ""

    }

    DropdownMenuItem(
        leadingIcon = { SearchIcon() },
        text = { Text("Search") },
        onClick = {
            dialogState.value = true
            pressedState = true
        },
        modifier = Modifier.onPointerEvent(PointerEventType.Enter) { backgroundColorState.value = true }
            .onPointerEvent(PointerEventType.Exit) { backgroundColorState.value = false }
            .background(color = MenuItemBackgroundColor(backgroundColorState))
    )

}

fun findFiles(dirPath: String): List<String> {

    val listFiles = File(dirPath).list() ?: throw IllegalStateException()

    return listFiles.map { it.split(".")[0] }
}

@Composable
fun CompleteDialogContent(
    title: String,
    dialogState: MutableState<Boolean>,
    dirPath: String,
    minWidth: MutableState<Int>,
    listFiles: List<String>,
    enabledButtonIndex: MutableState<Int>
) {
    Card(
        modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f).shadow(elevation = 10.dp, ambientColor = Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        shape = RectangleShape,
        border = BorderStroke(0.5.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {

            Column(
                modifier = Modifier
                    .height(50.dp)
            ) {
                TittleAndButton(title, dialogState)
            }


            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                mainBody(dirPath, listFiles, minWidth, enabledButtonIndex)
            }


            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(1f)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                BottomButtons(dialogState, dialogState,"Ok", "Cancel")
            }


        }
    }

}

@Composable
fun TittleAndButton(title: String, dialogState: MutableState<Boolean>) {

    val hoverButtonState = remember { mutableStateOf(false) }

    val clickButtonState = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 20.dp, top = 0.dp, end = 0.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)

        TopAppIconButton(
            rememberVectorPainter(Icons.Outlined.Close),
            hoverButtonState,
            Color(233, 8, 28),
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.primary,
            clickButtonState
        )
        dialogState.value = !clickButtonState.value
    }
    Divider(modifier = Modifier.height(1.dp), color = Color.LightGray)

}

@Composable
fun BottomButtons(dialogState: MutableState<Boolean>, firstButtonState: MutableState<Boolean>, firstButtonText: String, secondButtonText: String) {
    Button(
        modifier = Modifier.width(90.dp),
        shape = RoundedCornerShape(3.dp),
        onClick = {
            firstButtonState.value = !firstButtonState.value
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.background
        )
    ) {
        Text(text = firstButtonText, fontSize = 12.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, maxLines = 1)
    }
    Spacer(modifier = Modifier.width(5.dp))
    Button(
        modifier = Modifier.width(90.dp),
        shape = RoundedCornerShape(3.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(1.dp, Color(208, 210, 219)),
        onClick = { dialogState.value = false },
        contentPadding = PaddingValues(start = 2.dp, end = 2.dp)
    ) {
        Text(text = secondButtonText, fontSize = 12.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

@Composable
fun mainBody(
    dirPath: String,
    listFiles: List<String>,
    minWidth: MutableState<Int>,
    enabledButtonIndex: MutableState<Int>
) {

    val dirName = dirPath.split("/").last()

    Card(
        shape = RoundedCornerShape(3.dp),
        border = BorderStroke(1.dp, Color(47, 105, 215)),
        modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 10.dp), // для него
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            maxLines = 1,
            text = dirPath,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                .onGloballyPositioned { coordinates -> minWidth.value = coordinates.size.width },
            fontFamily = FontFamily.Monospace,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }

    Spacer(modifier = Modifier.height(1.dp).fillMaxWidth())

    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 10.dp).fillMaxHeight()
            .border(0.5.dp, Color.LightGray),
        contentPadding = PaddingValues(0.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.Bottom) {
                Icon(
                    Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Icon(
                    painterResource("/drawable/dir.png"),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "${dirName}:",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(listFiles.size) { index ->
            val checked = remember { mutableStateOf(false) }
            Button(
                onClick = {
                    checked.value = !checked.value
                    enabledButtonIndex.value = index
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (checked.value && enabledButtonIndex.value == index) MaterialTheme.colorScheme.tertiary
                    else MaterialTheme.colorScheme.background
                ),
                modifier = Modifier
                    .fillParentMaxWidth()
                    .clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = {}), // ??
                shape = RectangleShape
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillParentMaxWidth().align(Alignment.CenterStart)
                    ) {
                        Spacer(modifier = Modifier.width(25.dp))
                        Icon(FileIcon(dirName), contentDescription = null, tint = Color.Gray)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = listFiles[index], fontSize = 13.sp, fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Normal, maxLines = 1
                        )
                    }

                }
            }
        }

    }

}

@Composable
fun FileIcon(dirName: String): Painter {

    return when (dirName) {
        "RB-trees" -> painterResource("/drawable/neo4jFormat.png")
        "AVL-trees" -> painterResource("/drawable/sqlFormat.png")
        "BIN-trees" -> painterResource("/drawable/jsonFormat.png")
        else -> throw IllegalStateException()
    }

}
