
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import controller.Controller
import java.awt.Dimension
import javax.swing.JFileChooser

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "LESOK",
    ) {
        this.window.minimumSize = Dimension(800, 600)
        val treeNames = listOf("Red black tree", "AVL tree", "Binary tree")

        val dirPath = System.getProperty("user.dir") + "/saved-trees"
        val dirFiles = listOf("$dirPath/RB-trees", "$dirPath/AVL-trees", "$dirPath/BIN-trees")

        val controller = Controller()
        val showFiles = controller.showFiles()

        var numTree = 0

        MenuBar {
            Menu(text = "Open") {
                repeat(3) { indexTree ->
                    Menu(treeNames[indexTree]) {
                        // поле для самостоятельного ввода имени
                        val icon = Icons.Outlined.Search
                        Item("Search", rememberVectorPainter(icon)) {
                            val fd = JFileChooser(dirFiles[indexTree])
                            fd.isMultiSelectionEnabled = false
                            fd.fileSelectionMode = JFileChooser.FILES_ONLY
                            fd.isFileHidingEnabled = false
                            if (fd.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                numTree = indexTree // убедиться, что user сделал "корректный" выбор
                                val name = fd.selectedFile
                            }


                        }
                        repeat(showFiles[indexTree].size) { index ->
                            Item(
                                showFiles[indexTree][index],
                                onClick = {
                                    numTree = index
                                }) // контроллер создает новое дерево и начинает работу с ним
                        }
                    }
                }
            }
            Menu(text = " Create") {
                Item("Red Black tree", onClick = {
                    numTree = 0
                }) // контроллер создает новое дерево и начинает работу с ним
                Item("AVL tree", onClick = {
                    numTree = 1
                })
                Item("Binary tree", onClick = {
                    numTree = 2
                })
            }

            Menu(text = "Save & delete") {
                // надо как-то узнавать с каким деревом работаем
                Item("Save as") {
                    val fd = JFileChooser(dirFiles[numTree])
                    fd.fileSelectionMode = JFileChooser.FILES_ONLY

                    if (fd.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        val path = fd.selectedFile.name
                    }
                    // отправить name в балансер

                }
                Item("Delete", onClick = { /* тут запрос в балансер на удаление и предоставление нового дерева*/})
            }
        }

        Box(modifier = Modifier.background(Color(234, 231, 220)).fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(0.2f).fillMaxHeight(0.3f)
            ) {
                val options = listOf("INSERT", "DELETE", "FIND")
                repeat(3) { index ->
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        var consumedText by remember { mutableStateOf(0) }
                        var text by remember { mutableStateOf("") }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(216, 195, 165))
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = options[index],
                                color = Color(142, 141, 138),
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                        }
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .onPreviewKeyEvent {
                                    when {
                                        (!it.isShiftPressed && it.key == Key.Enter && it.type == KeyEventType.KeyUp) -> {
                                            consumedText -= text.length
                                            text = ""
                                            true
                                            // вот тут походу отправляем запрос в контроллер
                                        }

                                        else -> false
                                    }
                                }
                                .background(Color(216, 195, 165))
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(Modifier.height(5.dp))
                }

            }
        }
    }
}
// .wrapContentSize()