package viewPart.nodes.drawableAVL

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import viewPart.nodes.drawableTree.NodeDesign

object AVLNodeDesign: NodeDesign {
    // TODO: Реализовать, как BINNodeDesign
    override var nodeSize: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var shape: Shape
        get() = TODO("Not yet implemented")
        set(value) {}
    override var lineStrokeWidth: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var lineColor: Color
        get() = TODO("Not yet implemented")
        set(value) {}

    @Composable
    override fun infoView(information: String, modifier: Modifier) {
        TODO("Not yet implemented")
    }
}
