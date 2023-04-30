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
import databaseManage.BINTreeManager
import viewPart.nodes.displayTree
import viewPart.nodes.drawableBIN.BINDrawableTree

@Composable
fun controlFields(controller: Controller) {

    val addFieldState = remember { mutableStateOf(false) }
    val findFieldState = remember { mutableStateOf(false) }
    val deleteFieldState = remember { mutableStateOf(false) }

    // скорее всего использовать by remember

    val value = remember { mutableStateOf("") }

    Column(modifier = Modifier.offset(0.dp, 0.dp).padding(horizontal = 10.dp)) {
        // add
        controlField("Add", addFieldState, value)
        Spacer(modifier = Modifier.height(2.dp))

        // find
        controlField("Find", findFieldState, value)
        Spacer(modifier = Modifier.height(2.dp))

        // delete
        controlField("Delete", deleteFieldState, value)

    }

    var tree by remember { mutableStateOf(BINDrawableTree("a", BINTreeManager())) }

    if (!addFieldState.value && !findFieldState.value && !deleteFieldState.value) {
        displayTree(tree)
        addFieldState.value = false
    }

    // тут мы наверное можем быть уверены, что тип дерева не поменялся
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
fun controlField(buttonName: String, buttonState: MutableState<Boolean>, value: MutableState<String>) {

    var text by remember { mutableStateOf("") }

    var containerColor by remember { mutableStateOf(Color(237, 232, 232)) }

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
        modifier = Modifier
            .padding(start = 3.dp)
            .width(150.dp)
            .onFocusChanged {
                containerColor = if (it.isFocused) {
                    Color(255, 255, 255)
                } else {
                    Color(237, 232, 232)
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