package viewPart.nodes.drawableTree

import treelib.commonObjects.Container

interface DrawTree<DNodeType : DrawableNode<Container<Int, String>, DNodeType>> {
    val name: String
    var root: DNodeType?
    val designNode: NodeDesign
    var yShiftBetweenNodes: Float

    fun initTree()
    fun updateTree()
    fun deleteTree()
    fun saveTree()
    fun insert(item: Container<Int, String>)
    fun delete(item: Container<Int, String>)
    fun find(item: Container<Int, String>): Container<Int, String>?
    fun repositisonTree(xBase: Float, yBase: Float)
}
