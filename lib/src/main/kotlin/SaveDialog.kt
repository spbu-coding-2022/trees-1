
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun saveDialog(
    buttonState: List<MutableState<Boolean>>,
    clickButtonState: MutableState<Boolean>,
    fileName: MutableState<String>
) {

    var validateInputState by remember { mutableStateOf(true) }
    var successDialogState by remember { mutableStateOf(false) }
    var warningDialogState by remember { mutableStateOf(false) }
    val saveButtonState = remember { mutableStateOf(false) }



    if (clickButtonState.value) {
        Dialog(
            onCloseRequest = { clickButtonState.value = false }, undecorated = true,
            state = rememberDialogState(
                position = WindowPosition(Alignment.Center), // поменять ??
                size = DpSize(300.dp, 200.dp)
            ),
            resizable = false
        )
        {
            Card(
                modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f)
                    .shadow(elevation = 10.dp, ambientColor = Color.Gray),
                shape = RectangleShape,
                border = BorderStroke(0.5.dp, Color.LightGray),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {

                Column(modifier = Modifier.fillMaxSize()) {

                    Column(modifier = Modifier.height(50.dp)) {
                        tittleAndButton("Save tree", clickButtonState)
                    }

                    Divider(modifier = Modifier.height(1.dp).fillMaxWidth())

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextField(
                            value = fileName.value,
                            singleLine = true,
                            shape = RoundedCornerShape(4.dp),
                            onValueChange = { fileName.value = it },
                            modifier = Modifier
                                .onPreviewKeyEvent {
                                    when {
                                        (!it.isShiftPressed && it.key == Key.Enter && it.type == KeyEventType.KeyUp) -> {
                                            validateInputState = validate(fileName.value)
                                            if (validateInputState) {
                                                successDialogState = true
                                            } else {
                                                warningDialogState = true
                                            }
                                            true
                                        }
                                        else -> {
                                            warningDialogState = false
                                            successDialogState = false
                                            saveButtonState.value = false
                                            false
                                        }
                                    }
                                }
                                .align(Alignment.CenterHorizontally).border(2.dp, Color(47, 105, 215)),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = if (!warningDialogState) MaterialTheme.colorScheme.background
                                else Color(232, 169, 169)
                            ),
                            isError = !validateInputState,
                            placeholder = { Text(text = "Enter a filename", color = Color.Gray) },
                        )
                        Text(
                            text = when {
                                successDialogState -> "Tree successfully saved to ${fileName.value}"
                                warningDialogState -> "Invalid tree name for saving"
                                else -> ""
                            },
                            color = if (successDialogState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )

                        Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
                            BottomButtons(
                                clickButtonState,
                                saveButtonState,
                                "Save",
                                "Cancel"
                            )
                        }

                        if (saveButtonState.value) {
                            validateInputState = validate(fileName.value)
                            if (validateInputState) {
                                successDialogState = true
                            } else {
                                warningDialogState = true
                            }
                        }

                    }

                }

            }
        }
    }

    topAppIconButton(
        painterResource("/drawable/save.png"),
        buttonState,
        1,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.background,
        clickButtonState
    )
}
