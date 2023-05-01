package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import controller.Controller
import viewPart.nodes.drawableTree.DrawTree

@Composable
fun controlFields(
    controller: Controller,
    activeTree: MutableState<Boolean>,
    deleteTreeState: MutableState<Boolean>,
    openTreeState: MutableState<Boolean>
) {

    val addFieldState = remember { mutableStateOf(false) }
    val findFieldState = remember { mutableStateOf(false) }
    val deleteFieldState = remember { mutableStateOf(false) }

    val value = remember { mutableStateOf("") }

    Column(modifier = Modifier.offset(0.dp, 0.dp).padding(horizontal = 10.dp)) {
        // add
        controlField("Add", addFieldState, value, activeTree)
        Spacer(modifier = Modifier.height(2.dp))

        // find
        controlField("Find", findFieldState, value, activeTree)
        Spacer(modifier = Modifier.height(2.dp))

        // delete
        controlField("Delete", deleteFieldState, value, activeTree)
    }

    var tree by remember { mutableStateOf<DrawTree?>(null) }

    if (!addFieldState.value && !findFieldState.value && !deleteFieldState.value) {
        if (deleteTreeState.value) {
            tree = controller.deleteTree()
            deleteTreeState.value = false
        }
        if (openTreeState.value) {
            tree = controller.tree  // ??
        }
        tree?.displayTree()
        addFieldState.value = false
    }

    if (addFieldState.value) {
        tree = controller.insert(value.value)
        addFieldState.value = false
    }
    if (findFieldState.value) {
        tree = controller.find(value.value)
        findFieldState.value = false
    }
    if (deleteFieldState.value) {
        tree = controller.delete(value.value)
        deleteFieldState.value = false
    }

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun controlField(
    buttonName: String,
    buttonState: MutableState<Boolean>,
    value: MutableState<String>,
    activeTree: MutableState<Boolean>
) {

    var text by remember { mutableStateOf("") }

    var containerColor by remember { mutableStateOf(Color(206, 211, 216)) }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        singleLine = true,
        label = { Text(buttonName) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = containerColor,
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
                containerColor = if (it.isFocused) {
                    Color(255, 255, 255)
                } else {
                    Color(206, 211, 216)
                }
            }.onPreviewKeyEvent {
                when {
                    (!it.isShiftPressed && it.key == Key.Enter && it.type == KeyEventType.KeyUp) -> {
                        value.value = text // change !!!
                        buttonState.value = true
                        text = ""
                        true
                    }

                    else -> {
                        false
                    }
                }

            },
        trailingIcon = {

        },
        shape = RoundedCornerShape(8.dp)
    )

}

