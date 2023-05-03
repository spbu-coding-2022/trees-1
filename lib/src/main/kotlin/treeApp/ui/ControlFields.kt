package treeApp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.WindowState
import treeApp.controller.Controller
import treeApp.controller.viewPart.drawableTree.DrawTree

@Composable
fun ControlFields(
    controller: Controller,
    activeTree: MutableState<Boolean>,
    deleteTreeState: MutableState<Boolean>,
    openTreeState: MutableState<Boolean>,
    createTreeState: MutableState<Boolean>,
    dragState: MutableState<Boolean>,
    windowState: WindowState,
) {

    val addFieldState = remember { mutableStateOf(false) }
    val findFieldState = remember { mutableStateOf(false) }
    val deleteFieldState = remember { mutableStateOf(false) }
    val returnButtonClickState = remember { mutableStateOf(false) }
    val returnButtonHoverState = remember { mutableStateOf(false) }

    val value = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.offset(0.dp, 0.dp).padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        ControlField("Add", addFieldState, value, activeTree)
        Spacer(modifier = Modifier.height(2.dp))

        ControlField("Find", findFieldState, value, activeTree)
        Spacer(modifier = Modifier.height(2.dp))

        ControlField("Delete", deleteFieldState, value, activeTree)
        Spacer(modifier = Modifier.height(5.dp))

        ReturnButton(
            returnButtonHoverState,
            returnButtonClickState,
            painterResource("drawable/go-back-arrow.png"),
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.primary,
            50.dp, 50.dp,
            CircleShape
        )
    }

    var tree by remember { mutableStateOf<DrawTree?>(null) }

    if (!addFieldState.value && !findFieldState.value && !deleteFieldState.value) {
        if (deleteTreeState.value) {
            tree = controller.deleteTree()
            deleteTreeState.value = false
        }
        if (openTreeState.value) {
            tree = controller.tree
            openTreeState.value = false
        }
        if (createTreeState.value) {
            tree = controller.tree
            createTreeState.value = false
        }
        if (returnButtonClickState.value) {
            tree?.updateTree()
            tree?.repositionTree(800f, 10f)
            returnButtonClickState.value = false
            dragState.value = false
        }
        tree?.let { displayTree(it, dragState) }

        addFieldState.value = false
    }

    if (addFieldState.value) {
        tree = controller.insert(value.value)
        addFieldState.value = false
    }
    if (findFieldState.value) {
        tree = controller.find(value.value, windowState)
        findFieldState.value = false
    }
    if (deleteFieldState.value) {
        tree = controller.delete(value.value)
        deleteFieldState.value = false
    }

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ControlField(
    buttonName: String,
    buttonState: MutableState<Boolean>,
    value: MutableState<String>,
    activeTree: MutableState<Boolean>
) {

    var userInput by remember { mutableStateOf("") }

    var fieldBackgroundColor by remember { mutableStateOf(Color(206, 211, 216)) }

    OutlinedTextField(
        value = userInput,
        onValueChange = { userInput = it },
        singleLine = true,
        label = { Text(buttonName) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = fieldBackgroundColor,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
            focusedLabelColor = Color(99, 95, 95),
            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
        ),
        enabled = activeTree.value,
        modifier = Modifier
            .padding(start = 3.dp)
            .width(150.dp)
            .onFocusChanged {
                fieldBackgroundColor = if (it.isFocused) {
                    Color(255, 255, 255)
                } else {
                    Color(206, 211, 216)
                }
            }.onPreviewKeyEvent {
                when {
                    (!it.isShiftPressed && it.key == Key.Enter && it.type == KeyEventType.KeyUp) -> {
                        value.value = userInput // change !!!
                        buttonState.value = true
                        userInput = ""
                        true
                    }

                    else -> {
                        false
                    }
                }

            },
        trailingIcon = { },
        shape = RoundedCornerShape(8.dp)
    )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReturnButton(
    hoverButtonState: MutableState<Boolean>,
    clickButtonState: MutableState<Boolean>,
    painter: Painter,
    hoverColor: Color,
    color: Color,
    iconColor: Color,
    height: Dp,
    width: Dp,
    shape: Shape,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row() {
        IconButton(
            onClick = { clickButtonState.value = !clickButtonState.value },
            modifier = Modifier.height(height).width(width)
                .onPointerEvent(PointerEventType.Enter) { hoverButtonState.value = true }
                .onPointerEvent(PointerEventType.Exit) { hoverButtonState.value = false }
                .clickable(indication = null, interactionSource = interactionSource) {}
                .clip(shape),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = if (hoverButtonState.value) iconColor else MaterialTheme.colorScheme.background,
                containerColor = if (hoverButtonState.value) color else hoverColor
            )
        ) {
            Icon(painter, contentDescription = null)
        }

        if (hoverButtonState.value) {
            Popup(offset = IntOffset(x = 60, y = 50)) {
                Text(text = "Back to root")
            }
        }
    }
}

