
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import controller.Controller
import java.awt.Dimension

@OptIn(ExperimentalMaterial3Api::class)
fun main() = application {

    Window(
        onCloseRequest = ::exitApplication
    ) {
        this.window.minimumSize = Dimension(800, 600)
        Scaffold(
            topBar = {
                myTopAppBar()
            },
            content = {

            }
        )

    }
}

val lightColors = lightColorScheme(
    background = Color(255, 255, 255),
    primary = Color(34, 35, 41),
    secondary = Color(47, 105, 215),
    onSecondary = Color(148, 150, 166), // 208, 223, 252
    tertiary = Color(208, 223, 252)

)

private val myTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 96.sp
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myTopAppBar() {

    val controller = Controller()
    val showFiles = controller.showFiles()

    MaterialTheme(
        colorScheme = lightColors,
        typography = myTypography
    ) {
        val expanded = remember { mutableStateOf(false) }
        val expandedCreateNested = remember { mutableStateOf(false) }
        val expandedOpenNested = remember { mutableStateOf(false) }
        var mainMenuWidth by remember { mutableStateOf(0.dp) }
        TopAppBar(
            title = { Text("") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.background
            ),
            navigationIcon = {
                IconButton(
                    onClick = {
                        expanded.value = !expanded.value
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                    enabled = true,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description",
                    )
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    focusable = false,
                ) {
                    DropdownMenuItem(
                        text = { Text("Create") },
                        trailingIcon = { arrowIcon() },
                        onClick = {
                            expandedCreateNested.value = !expandedCreateNested.value // !!!
                            expandedOpenNested.value = false
                            // main.value = true
                        },
                        modifier = Modifier.onGloballyPositioned {
                            mainMenuWidth = it.size.width.dp - 40.dp
                        }
                    )
                    createNestedMenu(expandedCreateNested, expanded, mainMenuWidth)

                    DropdownMenuItem(
                        text = { Text("Open") },
                        trailingIcon = { arrowIcon() },
                        onClick = {
                            expandedOpenNested.value = !expandedOpenNested.value
                            //showFiles = controller.showFiles()
                            // main.value
                        }
                    )
                    openNestedMenu(expandedOpenNested, expanded, mainMenuWidth, showFiles)

                    DropdownMenuItem(text = { Text("Save") }, onClick = {})

                    DropdownMenuItem(leadingIcon = { deleteIcon() }, text = { Text("Delete") }, onClick = {})
                }
            }
        )
    }

}

@Composable
fun deleteIcon() = Icon(
    imageVector = Icons.Outlined.Delete,
    contentDescription = null
)

@Composable
fun arrowIcon() = Icon(
    imageVector = Icons.Outlined.KeyboardArrowRight,
    contentDescription = null
)

@Composable
fun createNestedMenu(
    expandedNested: MutableState<Boolean>,
    expandedMainMenu: MutableState<Boolean>,
    mainMenuWidth: Dp,
) {
    val treesNames = listOf("Red black tree", "AVL tree", "Binary tree")
    AnimatedVisibility(
        visible = expandedNested.value
    ) {
        DropdownMenu(
            expanded = expandedNested.value,
            offset = DpOffset(mainMenuWidth, 0.dp),
            onDismissRequest = {
                expandedNested.value = false
                expandedMainMenu.value = false
            }
        ) {

            repeat(3) { index ->
                DropdownMenuItem(
                    text = { Text(treesNames[index]) },
                    onClick = {
                        expandedNested.value = false
                        expandedMainMenu.value = false
                    }
                )
            }

        }

    }
}

@Composable
fun openNestedMenu(
    expandedNested: MutableState<Boolean>,
    expandedMainMenu: MutableState<Boolean>,
    mainMenuWidth: Dp,
    showFiles: List<List<String>>,
) {
    val treesNames = listOf("Red black tree", "AVL tree", "Binary tree")
    val a = remember { mutableStateOf(true) } // поменять на лист из 3??
    val expandedTreesNames = listOf(remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) })
    var offsetTreesNames by remember { mutableStateOf(0.dp) }

    AnimatedVisibility(
        visible = expandedNested.value
    ) {
        DropdownMenu(
            expanded = expandedNested.value,
            offset = DpOffset(mainMenuWidth, 0.dp),
            onDismissRequest = {
                expandedNested.value = false
            },
            modifier = Modifier.onGloballyPositioned {
                offsetTreesNames = it.size.width.dp - 40.dp
            }
        ) {
            repeat(3) { index ->
                DropdownMenuItem(
                    text = { Text(treesNames[index]) },
                    onClick = {
                        expandedTreesNames[index].value = a.value
                        a.value = true
                    },
                    trailingIcon = { arrowIcon() }
                )
                treesNames(
                    expandedTreesNames[index],
                    expandedNested,
                    showFiles[index],
                    offsetTreesNames, a, index  // remove a !!!!
                )
                expandedMainMenu.value = expandedNested.value
            }

        }

    }

}

@Composable
fun treesNames(
    expandedNested: MutableState<Boolean>,
    expandedOpenNested: MutableState<Boolean>,
    showFiles: List<String>,
    offset: Dp,
    a: MutableState<Boolean>,
    index: Int
) {

    val dirPath = System.getProperty("user.dir") + "/saved-trees"
    val dirFiles = listOf("$dirPath/RB-trees", "$dirPath/AVL-trees", "$dirPath/BIN-trees")

    val chooseSearch = remember { mutableStateOf(false) }

    val selectedTree = remember { mutableStateOf("null") }

    AnimatedVisibility(
        visible = expandedNested.value
    ) {
        DropdownMenu(
            expanded = expandedNested.value,
            offset = DpOffset(offset, 0.dp),
            onDismissRequest = {
                a.value = !expandedNested.value
                expandedNested.value = false
            }
        ) {
            searchItem(dirFiles[index], expandedOpenNested, selectedTree)
            repeat(showFiles.size) { index ->
                DropdownMenuItem(
                    text = { Text(showFiles[index]) },
                    onClick = {
                        expandedOpenNested.value = false
                    }
                )
            }

        }

    }

}

@Composable
fun searchIcon() = Icon(
    imageVector = Icons.Outlined.Search,
    contentDescription = null
)
