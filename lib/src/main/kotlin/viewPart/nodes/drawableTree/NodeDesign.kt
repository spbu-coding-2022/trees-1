package viewPart.nodes.drawableTree

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

interface NodeDesign {
    var nodeSize: Float
    var shape: Shape
    var lineStrokeWidth: Float
    var lineColor: Color

    @Composable
    fun infoView(information: String, modifier: Modifier)
}