package viewPart.nodes.drawableAVL

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
) : DrawableTree<
        AVLDrawableNode<Container<Int, String>>,
        DrawableAVLVertex<Container<Int, String>>,
        AVLNode<Container<Int, String>>,
        AVLStateContainer<Container<Int, String>>,
        AVLVertex<Container<Int, String>>,
        AVLStruct<Container<Int, String>>
        >() {
    override var root: AVLDrawableNode<Container<Int, String>>? = null
    override var drawablePreOrder: List<AVLDrawableNode<Container<Int, String>>>? = null
    override val treeStruct: AVLStruct<Container<Int, String>> = AVLStruct()
    override val designNode = AVLNodeDesign

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
