package treeApp.controller.viewPart.drawableAVL

import androidx.compose.runtime.mutableStateOf
import treeApp.controller.databaseManage.TreeManager
import treelib.databaseSave.sqlite.DrawableAVLVertex
import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStateContainer
import treelib.avlTree.AVLStruct
import treelib.avlTree.AVLVertex
import treelib.commonObjects.Container
import treeApp.controller.viewPart.drawableTree.DrawableTree
import treeApp.ui.nodeDesign.AVLNodeDesign

class AVLDrawableTree(
    override var name: String,
    override val treeManager: TreeManager<Container<Int, String>, DrawableAVLVertex<Container<Int, String>>, AVLNode<Container<Int, String>>, AVLStateContainer<Container<Int, String>>, AVLVertex<Container<Int, String>>, AVLStruct<Container<Int, String>>>,
) : DrawableTree<AVLDrawableNode<Container<Int, String>>, DrawableAVLVertex<Container<Int, String>>, AVLNode<Container<Int, String>>, AVLStateContainer<Container<Int, String>>, AVLVertex<Container<Int, String>>, AVLStruct<Container<Int, String>>>() {
    override var root: AVLDrawableNode<Container<Int, String>>? = null
    override var drawablePreOrder: List<AVLDrawableNode<Container<Int, String>>>? = null
    override var treeStruct = AVLStruct<Container<Int, String>>()
    override val designNode = AVLNodeDesign

    override fun deleteTree() {
        root = null
        treeStruct = AVLStruct()
    }

    override fun drawableVertexToNode(vertex: DrawableAVLVertex<Container<Int, String>>) = AVLDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(vertex.x.toFloat()),
        yState = mutableStateOf(vertex.y.toFloat()),
        height = vertex.height.toInt(),
    )

    override fun vertexToNode(vertex: AVLVertex<Container<Int, String>>) = AVLDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(0f),
        yState = mutableStateOf(0f),
        height = vertex.height.toInt(),
    )

    override fun nodeToDrawableVertex(node: AVLDrawableNode<Container<Int, String>>) = DrawableAVLVertex(
        value = node.value,
        x = node.xState.value.toDouble(),
        y = node.yState.value.toDouble(),
        height = node.height.toUInt(),
    )
}
