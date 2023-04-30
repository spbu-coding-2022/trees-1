package viewPart.nodes.drawableRB

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import viewPart.nodes.drawableTree.NodeDesign

object RBNodeDesign: NodeDesign {
    val redMarker = Color.Red
    val blackMarker = Color.Black
    override var nodeSize = 100f
    override var shape: Shape = CircleShape
    override var lineStrokeWidth = 15f
    override var lineColor = Color.Black

    @Composable
    override fun infoView(information: String, modifier: Modifier) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = information,
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
