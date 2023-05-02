package treeApp.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SearchIcon() = Icon(
    imageVector = Icons.Outlined.Search,
    contentDescription = null
)

@Composable
fun ArrowIcon() = Icon(
    imageVector = Icons.Outlined.KeyboardArrowRight,
    contentDescription = null,
    modifier = Modifier.size(16.dp)
)

@Composable
fun MenuItemBackgroundColor(state: MutableState<Boolean>): Color {
    return if (state.value) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.background
}

@Composable
fun AppIcon(): Painter {
    return painterResource("appIcon.png")
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopAppIconButton(
    painter: Painter,
    hoverButtonState: MutableState<Boolean>,
    color: Color,
    disabledColor: Color,
    iconColor: Color,
    clickButtonState: MutableState<Boolean>,
) {

    IconButton(onClick = {
        clickButtonState.value = !clickButtonState.value
    }, modifier = Modifier.fillMaxHeight().width(50.dp).clip(RectangleShape)
        .onPointerEvent(PointerEventType.Enter) { hoverButtonState.value = true }
        .onPointerEvent(PointerEventType.Exit) { hoverButtonState.value = false },
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (hoverButtonState.value) MaterialTheme.colorScheme.background else iconColor,
            containerColor = if (hoverButtonState.value) color else disabledColor
        )
    ) {
        Icon(painter, contentDescription = null)
    }

}
