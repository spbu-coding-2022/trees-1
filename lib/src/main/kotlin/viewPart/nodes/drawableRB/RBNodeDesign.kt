package viewPart.nodes.drawableRB

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import viewPart.nodes.drawableTree.NodeDesign

object RBNodeDesign: NodeDesign {
    val redMarker = Color(233, 8, 28)
    val blackMarker = Color(51, 51, 51)
    override var colorNode: Color = Color(34, 35, 41)
    override var nodeSize = 60f
    override var shape: Shape = CircleShape
    override var lineStrokeWidth = 10f
    override var lineColor = Color(34, 35, 41)

    @Composable
    override fun infoView(information: String, modifier: Modifier) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
        }
    }
}
