package treeApp.controller.viewPart.drawableBIN

import androidx.compose.runtime.mutableStateOf
import treeApp.controller.databaseManage.BINTreeManager
import treelib.databaseSave.jsonFormat.DrawableBINVertex
import treelib.binTree.BINNode
import treelib.binTree.BINStateContainer
import treelib.binTree.BINStruct
import treelib.binTree.BINVertex
import treelib.commonObjects.Container
import treeApp.controller.viewPart.drawableTree.DrawableTree
import treeApp.ui.nodeDesign.BINNodeDesign

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

    override fun saveTreeToDB() {
        if (root != null) {
            treeManager.saveTreeToDB(name, preOrder().map { nodeToDrawableVertex(it) }.toList(), listOf())
        } else {
            treeManager.saveTreeToDB(name, treeStruct)
        }
    }

    override fun drawableVertexToNode(vertex: DrawableBINVertex<Container<Int, String>>) = BINDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(vertex.x.toFloat()),
        yState = mutableStateOf(vertex.y.toFloat()),
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
