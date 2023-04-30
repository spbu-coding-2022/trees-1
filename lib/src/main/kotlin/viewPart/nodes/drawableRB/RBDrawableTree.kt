package viewPart.nodes.drawableRB

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

    override fun initTree() {
        TODO("Not yet implemented")
    }

    override fun deleteTree() {
        TODO("Not yet implemented")
    }

    override fun saveTree() {
        TODO("Not yet implemented")
    }

    override fun updateTree() {
        TODO("Not yet implemented")
    }
}
