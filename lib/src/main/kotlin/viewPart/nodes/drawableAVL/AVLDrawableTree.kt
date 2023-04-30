package viewPart.nodes.drawableAVL

import androidx.compose.runtime.mutableStateOf
import databaseManage.TreeManager
import databaseSave.sqlite.DrawableAVLVertex
import treelib.avlTree.AVLNode
import treelib.avlTree.AVLStateContainer
import treelib.avlTree.AVLStruct
import treelib.avlTree.AVLVertex
import treelib.commonObjects.Container
import viewPart.nodes.drawableTree.DrawableTree

class AVLDrawableTree(
    override val name: String,
    override val treeManager: TreeManager<Container<Int, String>, DrawableAVLVertex<Container<Int, String>>, AVLNode<Container<Int, String>>, AVLStateContainer<Container<Int, String>>, AVLVertex<Container<Int, String>>, AVLStruct<Container<Int, String>>>,
) : DrawableTree<AVLDrawableNode<Container<Int, String>>, DrawableAVLVertex<Container<Int, String>>, AVLNode<Container<Int, String>>, AVLStateContainer<Container<Int, String>>, AVLVertex<Container<Int, String>>, AVLStruct<Container<Int, String>>>() {
    override var root: AVLDrawableNode<Container<Int, String>>? = null
    override var drawablePreOrder: List<AVLDrawableNode<Container<Int, String>>>? = null
    override val treeStruct: AVLStruct<Container<Int, String>> = AVLStruct()
    override val designNode = AVLNodeDesign

    override fun drawableVertexToNode(vertex: DrawableAVLVertex<Container<Int, String>>) = AVLDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(0f),
        yState = mutableStateOf(0f),
        height = vertex.height.toInt()
    )

    override fun vertexToNode(vertex: AVLVertex<Container<Int, String>>) = AVLDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(0f),
        yState = mutableStateOf(0f),
        height = vertex.height.toInt()
    )

    override fun nodeToDrawableVertex(node: AVLDrawableNode<Container<Int, String>>) = DrawableAVLVertex(
        value = node.value,
        x = node.xState.value.toDouble(),
        y = node.yState.value.toDouble(),
        height = node.height.toUInt(),
    )
}
