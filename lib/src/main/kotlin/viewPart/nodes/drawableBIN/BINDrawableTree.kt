package viewPart.nodes.drawableBIN

import androidx.compose.runtime.mutableStateOf
import databaseManage.BINTreeManager
import databaseSave.jsonFormat.DrawableBINVertex
import treelib.binTree.BINNode
import treelib.binTree.BINStateContainer
import treelib.binTree.BINStruct
import treelib.binTree.BINVertex
import treelib.commonObjects.Container
import viewPart.nodes.drawableTree.DrawableTree

class BINDrawableTree(
    override var name: String,
    override val treeManager: BINTreeManager,
) :
    DrawableTree<
            BINDrawableNode<Container<Int, String>>,
            DrawableBINVertex<Container<Int, String>>,
            BINNode<Container<Int, String>>,
            BINStateContainer<Container<Int, String>>,
            BINVertex<Container<Int, String>>,
            BINStruct<Container<Int, String>>
            >() {

    override var root: BINDrawableNode<Container<Int, String>>? = null
    override var treeStruct = BINStruct<Container<Int, String>>()
    override var drawablePreOrder: List<BINDrawableNode<Container<Int, String>>>? = null
    override val designNode = BINNodeDesign

    override fun deleteTree() {
        root = null
        treeStruct = BINStruct()
    }

    override fun drawableVertexToNode(vertex: DrawableBINVertex<Container<Int, String>>) = BINDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(0f),
        yState = mutableStateOf(0f),
    )

    override fun vertexToNode(vertex: BINVertex<Container<Int, String>>) = BINDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(0f),
        yState = mutableStateOf(0f),
    )

    override fun nodeToDrawableVertex(node: BINDrawableNode<Container<Int, String>>) = DrawableBINVertex(
        value = node.value,
        x = node.xState.value.toDouble(),
        y = node.yState.value.toDouble(),
    )
}
