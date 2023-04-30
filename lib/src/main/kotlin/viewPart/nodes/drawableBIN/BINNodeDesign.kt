package viewPart.nodes.drawableBIN

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import viewPart.nodes.drawableTree.NodeDesign

object BINNodeDesign: NodeDesign {
    var colorNode = Color(208, 223, 252)
    override var lineColor = Color(34, 35, 41)
    override var nodeSize = 60f
    override var shape: Shape = CircleShape
    override var lineStrokeWidth = 10f
    @Composable
    override fun infoView(information: String, modifier: Modifier) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            //Text(
            //    text = information,
            //    fontSize = 30.sp,
            //    color = Color.White,
            //    fontWeight = FontWeight.Bold
            //)
        }
    }
}
