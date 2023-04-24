
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

// я передумал, будет три маленьких кнопки где-нибудь
@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "LESOK",
    ) {
        this.window.minimumSize = Dimension(800, 600)
        val treeNames = listOf("Red black tree", "AVL tree", "Binary tree")

        val dirPath = System.getProperty("user.dir")
        val dirFiles = listOf("$dirPath/neo4jDB/neo4jFormatFiles", dirPath, "$dirPath/jsonFormatFiles")

        val controller = Controller()
        val showFiles = controller.showFiles()

        MenuBar {
            Menu(text = "Open") {
                repeat(3) { indexTree ->
                    Menu(treeNames[indexTree]) {
                        // поле для самостоятельного ввода имени
                        Item("Search") {
                            val fd = JFileChooser(dirFiles[indexTree])
                            fd.isMultiSelectionEnabled = false
                            fd.fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
                            fd.isFileHidingEnabled = false
                            if (fd.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                val name = fd.selectedFile
                            }


                        }
                        repeat(showFiles[indexTree].size) { index ->
                            Item(
                                showFiles[indexTree][index],
                                onClick = { }) // контроллер создает новое дерево и начинает работу с ним
                        }
                    }
                }
            }
            Menu(text = " Create") {
                Item("Red Black tree", onClick = {}) // контроллер создает новое дерево и начинает работу с ним
                Item("AVL tree", onClick = {})
                Item("Binary tree", onClick = {})
            }

            Menu(text = "Save & delete") {
                Item("Save as", onClick = {})
                Item("Delete", onClick = {})
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

/*
val fd = FileDialog(this@Window.window, "Open", FileDialog.LOAD)
fd.isVisible = true
fd.file = dirFiles[indexTree] ?: dirPath
val fileName = fd.file ?: ""
if (fileName != "")
    controller.foo(fileName)
*/