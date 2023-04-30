package viewPart.nodes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.zIndex
import viewPart.nodes.drawableAVL.AVLDrawableTree
import viewPart.nodes.drawableAVL.AVLNodeDesign
import viewPart.nodes.drawableBIN.BINDrawableTree
import viewPart.nodes.drawableBIN.BINNodeDesign
import viewPart.nodes.drawableRB.RBDrawableTree
import viewPart.nodes.drawableRB.RBNodeDesign
import viewPart.nodes.drawableTree.DrawableNode
import viewPart.nodes.drawableTree.NodeDesign

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

@Composable
fun <Pack, DNode : DrawableNode<Pack, DNode>, NodeD : NodeDesign> displayNode(node: DNode, design: NodeD) {
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
