package treeApp.controller.viewPart.drawableRB

import androidx.compose.runtime.mutableStateOf
import treeApp.controller.databaseManage.TreeManager
import treeApp.controller.databaseManage.databaseSave.neo4j.DrawableRBVertex
import treelib.commonObjects.Container
import treelib.rbTree.RBNode
import treelib.rbTree.RBStateContainer
import treelib.rbTree.RBStruct
import treelib.rbTree.RBVertex
import treeApp.controller.viewPart.drawableTree.DrawableTree
import treeApp.ui.nodeDesign.RBNodeDesign

class RBDrawableTree(
    override var name: String,
    override val treeManager: TreeManager<Container<Int, String>, DrawableRBVertex<Container<Int, String>>, RBNode<Container<Int, String>>, RBStateContainer<Container<Int, String>>, RBVertex<Container<Int, String>>, RBStruct<Container<Int, String>>>,
) : DrawableTree<
        RBDrawableNode<Container<Int, String>>,
        DrawableRBVertex<Container<Int, String>>,
        RBNode<Container<Int, String>>,
        RBStateContainer<Container<Int, String>>,
        RBVertex<Container<Int, String>>,
        RBStruct<Container<Int, String>>
        >() {
    override var root: RBDrawableNode<Container<Int, String>>? = null
    override var drawablePreOrder: List<RBDrawableNode<Container<Int, String>>>? =  null
    override var treeStruct = RBStruct<Container<Int, String>>()
    override val designNode = RBNodeDesign

    override fun deleteTree() {
        this.deleteTreeFromBD()
        root = null
        treeStruct = RBStruct()
    }

    override fun saveTreeToDB() {
        if (root != null) {
            treeManager.saveTreeToDB(
                name,
                preOrder().map { nodeToDrawableVertex(it) }.toList(),
                inOrder().map { nodeToDrawableVertex(it) }.toList()
            )
        } else {
            treeManager.saveTreeToDB(name, treeStruct)
        }
    }

    override fun drawableVertexToNode(vertex: DrawableRBVertex<Container<Int, String>>) = RBDrawableNode(
        value = vertex.value,
        xState = mutableStateOf(vertex.x.toFloat()),
        yState = mutableStateOf(vertex.y.toFloat()),
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
