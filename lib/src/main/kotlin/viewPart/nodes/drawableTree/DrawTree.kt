package viewPart.nodes.drawableTree

import androidx.compose.runtime.Composable
import treelib.commonObjects.Container

interface DrawTree {
    var name: String
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
    fun find(item: Int)
    fun repositisonTree(xBase: Float, yBase: Float)
}
