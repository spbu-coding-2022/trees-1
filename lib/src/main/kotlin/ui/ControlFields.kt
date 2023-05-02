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
fun ControlFields(
    controller: Controller,
    activeTree: MutableState<Boolean>,
    deleteTreeState: MutableState<Boolean>,
    openTreeState: MutableState<Boolean>,
    createTreeState: MutableState<Boolean>
) {

    val addFieldState = remember { mutableStateOf(false) }
    val findFieldState = remember { mutableStateOf(false) }
    val deleteFieldState = remember { mutableStateOf(false) }

    val value = remember { mutableStateOf("") }

    Column(modifier = Modifier.offset(0.dp, 0.dp).padding(horizontal = 10.dp)) {
        // add
        ControlField("Add", addFieldState, value, activeTree)
        Spacer(modifier = Modifier.height(2.dp))

        // find
        ControlField("Find", findFieldState, value, activeTree)
        Spacer(modifier = Modifier.height(2.dp))

        // delete
        ControlField("Delete", deleteFieldState, value, activeTree)
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

