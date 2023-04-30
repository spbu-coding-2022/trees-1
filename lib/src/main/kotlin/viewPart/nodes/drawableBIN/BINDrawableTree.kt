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
    override val name: String,
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
    override val treeStruct = BINStruct<Container<Int, String>>()
    override var drawablePreOrder: List<BINDrawableNode<Container<Int, String>>>? = null
    override val designNode = BINNodeDesign

    /* DB methods */
    override fun initTree() {
        val binVertexes = treeManager.initTree(name, treeStruct)
        drawablePreOrder = binVertexes.map { drawableVertexToNode(it) }

        drawablePreOrder?.let {
            if (it.isNotEmpty()) {
                restoreTree(it)
            }
        }
    }

    override fun updateTree() {
        for (el in vertexesToNodes(treeStruct.preOrder())) {
            restoreInsert(el)
        }
    }

    private fun vertexesToNodes(preOrder: List<BINVertex<Container<Int, String>>>) = sequence {
        for (el in preOrder) {
            yield(
                vertexToNode(el)
            )
        }
    }

    private fun vertexToNode(vertex: BINVertex<Container<Int, String>>): BINDrawableNode<Container<Int, String>> {
        return BINDrawableNode(
            value = vertex.value,
            xState = mutableStateOf(0f),
            yState = mutableStateOf(0f),
        )
    }

    private fun drawableVertexToNode(vertex: DrawableBINVertex<Container<Int, String>>) = BINDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(vertex.x.toFloat()),
        yState = mutableStateOf(vertex.y.toFloat()),
    )

    private fun nodeToDrawableVertex(node: BINDrawableNode<Container<Int, String>>) = DrawableBINVertex<Container<Int, String>>(
        value = node.value,
        x = node.xState.value.toDouble(),
        y = node.yState.value.toDouble(),
    )

    private fun restoreTree(preOrder: List<BINDrawableNode<Container<Int, String>>>) {
        root = null
        for (el in preOrder) {
            restoreInsert(el)
        }
    }

    private fun restoreInsert(preOrderNode: BINDrawableNode<Container<Int, String>>) {
        if (root == null) {
            root = preOrderNode
            return
        }
        var currentParent = root
        while (currentParent != null) {
            currentParent?.let {
                when {
                    it.value < preOrderNode.value -> {
                        if (it.rightChild == null) {
                            it.rightChild = preOrderNode
                            return@restoreInsert
                        }
                        else currentParent = it.rightChild
                    }

                    it.value > preOrderNode.value -> {
                        if (it.leftChild == null) {
                            it.leftChild = preOrderNode
                            return@restoreInsert
                        }
                        else currentParent = it.leftChild
                    }

                    else -> {
                        println(currentParent!!.value)
                        println(preOrderNode.value)
                        throw InternalError("Can't restore tree from preOrder :(")
                    }
                }
            }
        }
    }

    override fun deleteTree() = treeManager.deleteTreeFromDB(name)

    override fun saveTree() {
        if (root != null) {
            treeManager.saveTreeToDB(name, preOrder().map { nodeToDrawableVertex(it) }.toList(), listOf())
        } else {
            treeManager.saveTreeToDB(name, treeStruct)
        }
    }
}
