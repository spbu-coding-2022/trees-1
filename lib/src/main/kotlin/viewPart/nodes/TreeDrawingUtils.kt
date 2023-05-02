package viewPart.nodes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import treelib.commonObjects.Container
import viewPart.nodes.drawableAVL.AVLDrawableNode
import viewPart.nodes.drawableAVL.AVLDrawableTree
import viewPart.nodes.drawableAVL.AVLNodeDesign
import viewPart.nodes.drawableBIN.BINDrawableNode
import viewPart.nodes.drawableBIN.BINDrawableTree
import viewPart.nodes.drawableBIN.BINNodeDesign
import viewPart.nodes.drawableRB.RBDrawableNode
import viewPart.nodes.drawableRB.RBDrawableTree
import viewPart.nodes.drawableRB.RBNodeDesign
import viewPart.nodes.drawableTree.DrawTree
import viewPart.nodes.drawableTree.DrawableNode
import viewPart.nodes.drawableTree.NodeDesign
import java.util.*
import kotlin.math.roundToInt

@Composable
fun displayTree(tree: DrawTree){
    when(tree){
        is BINDrawableTree -> displayBIN(tree)
        is RBDrawableTree -> displayRB(tree)
        is AVLDrawableTree -> displayAVL(tree)
        else -> throw NullPointerException("Wrong DrawableTree type")
    }
}

@Composable
fun displayRB(tree: RBDrawableTree) {
    val root = tree.root
    root?.let {
        displayNode(it, RBNodeDesign)
    }
}

@Composable
fun displayAVL(tree: AVLDrawableTree) {
    val root = tree.root
    root?.let {
        displayNode(it, AVLNodeDesign)
    }
}

@Composable
fun displayBIN(tree: BINDrawableTree) {
    val root = tree.root
    root?.let {
        displayNode(it, BINNodeDesign)
    }
}


@Composable
fun <DNode : DrawableNode<Container<Int, String>, DNode>, NodeD : NodeDesign> displayNode(node: DNode, design: NodeD) {

    if (node.clickState.value) {
        Box(modifier = Modifier
            .height(if (node is BINDrawableNode<*>) 60.dp else 80.dp).width(100.dp)
            .offset {
                IntOffset(
                    node.xState.value.roundToInt() + 71,
                    node.yState.value.roundToInt()
                )
            }
            .background(color = Color(206, 211, 216), shape = AbsoluteRoundedCornerShape(5.dp))
            .border(2.dp, MaterialTheme.colorScheme.primary, AbsoluteRoundedCornerShape(5.dp))
            .zIndex(1f)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp, vertical = 2.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
                Text(text = "key: ${node.value.key} ", maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = "value: ${node.value.value}", maxLines = 2, overflow = TextOverflow.Ellipsis)
                when (node ) {
//            Column(modifier = Modifier.zIndex(1f)) {
//                Text(text = "key: ${node.value.key}")
//                Text(text = "value: ${node.value.value}")
//                when (node) {
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
