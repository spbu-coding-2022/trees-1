
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import controller.Controller
import ui.*
import java.awt.Dimension

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun main() = application {


    val deleteButton: MutableState<Boolean> = remember { mutableStateOf(false) }
    val saveButton: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openButton: MutableState<Boolean> = remember { mutableStateOf(false) }
    val createButton: MutableState<Boolean> = remember { mutableStateOf(false) }
    val minimizeButton: MutableState<Boolean> = remember { mutableStateOf(false) }
    val maximizeButton: MutableState<Boolean> = remember { mutableStateOf(false) }
    val closeButton: MutableState<Boolean> = remember { mutableStateOf(false) }

    val controller = Controller()
    val activeTree = remember { mutableStateOf(false) }
    val windowState = rememberWindowState(placement = WindowPlacement.Maximized)
    val deleteTreeState = remember { mutableStateOf(false) }
    val openTreeState = remember { mutableStateOf(false) }
    val createTreeState = remember { mutableStateOf(false) }

    val dragState = remember { mutableStateOf(false) }

    val xCenter = remember { mutableStateOf(0f) }
    val yCenter = remember { mutableStateOf(0f) }

    if (!closeButton.value) {
        Window(
            onCloseRequest = ::exitApplication,
            undecorated = true,
            state = windowState,
            icon = AppIcon()
        ) {

            this.window.minimumSize = Dimension(800, 600)
            Scaffold(
                topBar = {
                    WindowDraggableArea {
                        MyTopAppBar(
                            clickDeleteButton = deleteButton,
                            clickSaveButton = saveButton,
                            clickOpenButton = openButton,
                            clickCreateButton = createButton,
                            clickMinimizeButton = minimizeButton,
                            clickMaximizeButton = maximizeButton,
                            clickCloseButton = closeButton,
                            windowState = windowState,
                            controller = controller,
                            activeTree = activeTree,
                            deleteTreeState = deleteTreeState,
                            openTreeState = openTreeState,
                            createTreeState = createTreeState
                        )
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
                                .onDrag {
                                    dragState.value = true
                                }

                        ) {
                            ControlFields(
                                controller = controller,
                                activeTree = activeTree,
                                deleteTreeState = deleteTreeState,
                                openTreeState = openTreeState,
                                createTreeState = createTreeState,
                                dragState = dragState,
                                windowState = windowState
                            )
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
