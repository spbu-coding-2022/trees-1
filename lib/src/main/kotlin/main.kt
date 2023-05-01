
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import controller.Controller
import ui.*
import java.awt.Dimension

@OptIn(ExperimentalMaterial3Api::class)
fun main() = application {

    val clickButtonsState = List(7) { remember { mutableStateOf(false) } }

    val windowState = rememberWindowState(placement = WindowPlacement.Maximized)

    val controller = Controller()

    val activeTree = remember { mutableStateOf(false) }

    val deleteTreeState = remember { mutableStateOf(false) }

    val openTreeState = remember { mutableStateOf(false) }

    if (!clickButtonsState[6].value) {
        Window(
            onCloseRequest = ::exitApplication,
            undecorated = true,
            state = windowState,
            icon = appIcon()
        ) {

            this.window.minimumSize = Dimension(800, 600)
            Scaffold(
                topBar = {
                    WindowDraggableArea {
                        myTopAppBar(clickButtonsState, windowState, controller, activeTree, deleteTreeState, openTreeState)
                    }
                },
                content = {
                    MaterialTheme(
                        colorScheme = lightColors,
                        typography = myTypography
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                                .offset(0.dp, 50.dp)

                        ) {
                            controlFields(controller, activeTree, deleteTreeState, openTreeState)
                        }

                    }

                }
            )


        }
    }

}

val lightColors = lightColorScheme(
    background = Color(255, 255, 255),
    primary = Color(34, 35, 41),
    secondary = Color(47, 105, 215),
    onSecondary = Color(148, 150, 166),
    tertiary = Color(208, 223, 252),
    onTertiary = Color(51, 51, 51),
    onPrimary = Color(53, 55, 63)

)

val myTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 96.sp
    )
)


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun topAppIconButton(
    painter: Painter,
    buttonsState: List<MutableState<Boolean>>,
    index: Int,
    color: Color,
    disabledColor: Color,
    iconColor: Color,
    clickButtonState: MutableState<Boolean>,
) {

    IconButton(onClick = {
        clickButtonState.value = !clickButtonState.value
    }, modifier = Modifier.fillMaxHeight().width(50.dp).clip(RectangleShape)
        .onPointerEvent(PointerEventType.Enter) { buttonsState[index].value = true }
        .onPointerEvent(PointerEventType.Exit) { buttonsState[index].value = false },
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (buttonsState[index].value) MaterialTheme.colorScheme.background else iconColor,
            containerColor = if (buttonsState[index].value) color else disabledColor
        )
    ) {
        Icon(painter, contentDescription = null)
    }

}

@Composable
fun searchIcon() = Icon(
    imageVector = Icons.Outlined.Search,
    contentDescription = null
)

@Composable
fun arrowIcon() = Icon(
    imageVector = Icons.Outlined.KeyboardArrowRight,
    contentDescription = null,
    modifier = Modifier.size(16.dp)
)

@Composable
fun menuItemBackgroundColor(state: MutableState<Boolean>): Color {
    return if (state.value) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.background
}

@Composable
fun appIcon(): Painter {
    return painterResource("appIcon.png")
}
