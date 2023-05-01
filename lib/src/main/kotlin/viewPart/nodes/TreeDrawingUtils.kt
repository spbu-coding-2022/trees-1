package viewPart.nodes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import treelib.commonObjects.Container
import viewPart.nodes.drawableAVL.AVLDrawableNode
import viewPart.nodes.drawableBIN.BINDrawableNode
import viewPart.nodes.drawableRB.RBDrawableNode
import viewPart.nodes.drawableTree.DrawableNode
import viewPart.nodes.drawableTree.NodeDesign
import java.util.*
import kotlin.math.roundToInt
/*
@Composable
fun displayTree(tree: RBDrawableTree) {
    val root = tree.root
    root?.let {
        displayNode(it, RBNodeDesign)
    }
}

@Composable
fun displayTree(tree: AVLDrawableTree) {
    val root = tree.root
    root?.let {
        displayNode(it, AVLNodeDesign)
    }
}

@Composable
fun displayTree(tree: BINDrawableTree) {
    val root = tree.root
    root?.let {
        displayNode(it, BINNodeDesign)
    }
}
*/
@Composable
fun <DNode : DrawableNode<Container<Int, String>, DNode>, NodeD : NodeDesign> displayNode(node: DNode, design: NodeD) {

    if (node.clickState.value) {
        Box(modifier = Modifier
            .height(if (node is BINDrawableNode<*>) 60.dp else 90.dp).width(100.dp)
            .offset {
                IntOffset(
                    node.xState.value.roundToInt() + 71,
                    node.yState.value.roundToInt()
                )
            }
            .background(color = Color(206, 211, 216))
            .border(2.dp, MaterialTheme.colorScheme.primary, AbsoluteRoundedCornerShape(5.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .zIndex(1f)
        ) {
            Column(modifier = Modifier.zIndex(1f)) {
                Text(text = "key: ${node.value.key}")
                Text(text = "value: ${node.value.value}")
                when (node ) {
                    is AVLDrawableNode<*> -> Text(text = "height: ${node.height}")
                    is RBDrawableNode<*> -> Text(text = "color: ${node.color.toString().lowercase(Locale.getDefault())}")
                }

            }

        }
    }

    design.infoView(node.value.toString(), node.modifier)

    node.leftChild?.let {
        edgeView(node, it, design)
        displayNode(it, design)
    }

    node.rightChild?.let {
        edgeView(node, it, design)
        displayNode(it, design)
    }
}

@Composable
fun <Pack, DNode : DrawableNode<Pack, DNode>, NodeD : NodeDesign> edgeView(node: DNode, child: DNode, design: NodeD) {
    Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)) {
        drawLine(
            color = design.lineColor,
            start = Offset(
                node.xState.value + design.nodeSize / 2,
                node.yState.value + design.nodeSize / 2,
            ),
            end = Offset(
                child.xState.value + design.nodeSize / 2,
                child.yState.value + design.nodeSize / 2,
            ),
            strokeWidth = design.lineStrokeWidth,
        )
    }
}
