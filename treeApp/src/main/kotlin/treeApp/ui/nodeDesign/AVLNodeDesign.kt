package treeApp.ui.nodeDesign

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import treeApp.ui.nodeDesign.NodeDesign

object AVLNodeDesign : NodeDesign {
    override var colorNode = Color(208, 223, 252)
    override var lineColor = Color(34, 35, 41)
    override var nodeSize = 60f
    override var shape: Shape = CircleShape
    override var lineStrokeWidth = 10f

    @Composable
    override fun infoView(modifier: Modifier, information: String) = Box(modifier = modifier, contentAlignment = Alignment.Center,) {
        Text(
            textAlign = TextAlign.Center,
            text = if (information.length <=3) information else "..."
        )
    }
}
