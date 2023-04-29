
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun deleteDialog(
    treeName: String,
    buttonState: List<MutableState<Boolean>>,
    clickButtonsState: MutableState<Boolean>
) {

    val deleteDialogState = remember { mutableStateOf(true) }

    if (clickButtonsState.value) {
        AlertDialog(
            backgroundColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { clickButtonsState.value = false},
            text = { Text(text = " Are you sure you want to delete $treeName ?", color = MaterialTheme.colorScheme.primary) },
            buttons = {

                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
                    BottomButtons(deleteDialogState, clickButtonsState,"Cancel", "Delete")
                }
            }
        )
    }

    if (!deleteDialogState.value) {
        // отправляем запрос в контроллер на удаление
        deleteDialogState.value = true
        clickButtonsState.value = false
    }

    topAppIconButton(
        rememberVectorPainter(Icons.Outlined.Delete),
        buttonState,
        0,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.background,
        clickButtonsState
    )

}
