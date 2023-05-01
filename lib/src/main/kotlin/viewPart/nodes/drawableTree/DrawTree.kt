package viewPart.nodes.drawableTree

import androidx.compose.runtime.Composable
import treelib.commonObjects.Container

interface DrawTree<DNodeType : DrawableNode<Container<Int, String>, DNodeType>> {
    /**How you should it use:
     *
     * <Work without DB (recommend, bd did not test)>
     *
     * OUTSIDE A COMPOSABLE CONTEXT {
     * * val manager = TreeManager()
     * * val tree = DrawableTree("name", manager)
     * * tree.insert
     * * tree.delete
     * * ...
     * * tree.update <- (means moving information: TreeStruct.root -> DrawableTree.root)
     * * tree.repositisonTree <- (set "beautiful" coordinates without display)
     *
     * }
     *
     * IN A COMPOSABLE CONTEXT {
     * * tree.displayTree()
     *
     * }
     * **/

    var name: String
    var root: DNodeType?
    val designNode: NodeDesign
    var yShiftBetweenNodes: Float

    @Composable
    fun displayTree()

    fun initTree()
    fun updateTree()
    fun deleteTree()
    fun deleteTreeFromBD()
    fun saveTreeToDB()
    fun insert(item: Container<Int, String>)
    fun delete(item: Container<Int, String>)
    fun find(item: Container<Int, String>): Container<Int, String>?
    fun repositisonTree(xBase: Float, yBase: Float)
}
