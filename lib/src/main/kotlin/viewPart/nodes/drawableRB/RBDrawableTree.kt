package viewPart.nodes.drawableRB

import androidx.compose.runtime.mutableStateOf
import databaseManage.TreeManager
import databaseSave.neo4j.DrawableRBVertex
import treelib.commonObjects.Container
import treelib.rbTree.RBNode
import treelib.rbTree.RBStateContainer
import treelib.rbTree.RBStruct
import treelib.rbTree.RBVertex
import viewPart.nodes.drawableTree.DrawableTree

class RBDrawableTree(
    override val name: String,
    override val treeManager: TreeManager<Container<Int, String>, DrawableRBVertex<Container<Int, String>>, RBNode<Container<Int, String>>, RBStateContainer<Container<Int, String>>, RBVertex<Container<Int, String>>, RBStruct<Container<Int, String>>>,
) : DrawableTree<
        RBDrawableNode<Container<Int, String>>,
        DrawableRBVertex<Container<Int, String>>,
        RBNode<Container<Int, String>>,
        RBStateContainer<Container<Int, String>>,
        RBVertex<Container<Int, String>>,
        RBStruct<Container<Int, String>>
        >() {
    override var root:  RBDrawableNode<Container<Int, String>>? = null
    override var drawablePreOrder: List<RBDrawableNode<Container<Int, String>>>? = null
    override val treeStruct: RBStruct<Container<Int, String>> = RBStruct()
    override val designNode = RBNodeDesign

    override fun drawableVertexToNode(vertex: DrawableRBVertex<Container<Int, String>>) = RBDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(0f),
        yState = mutableStateOf(0f),
        color = vertex.color,
    )

    override fun vertexToNode(vertex: RBVertex<Container<Int, String>>) = RBDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(0f),
        yState = mutableStateOf(0f),
        color = vertex.color,
    )

    override fun nodeToDrawableVertex(node: RBDrawableNode<Container<Int, String>>) = DrawableRBVertex(
        value = node.value,
        x = node.xState.value.toDouble(),
        y = node.yState.value.toDouble(),
        color = node.color,
    )
}
