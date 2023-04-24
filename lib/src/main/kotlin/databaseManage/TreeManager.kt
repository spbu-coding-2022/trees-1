package databaseManage

import databaseSave.DrawableVertex
import treelib.commonObjects.Container

interface TreeManager<Pack: Container<Int, String>, DrawableVertexType : DrawableVertex<Pack>> {

    val currentTreeName: String

    fun initTree(treeName: String): List<DrawableVertexType>

    fun getVertexesForDrawFromTree(): List<DrawableVertexType>

    fun getVertexesForDrawFromDB(): List<DrawableVertexType>

    fun saveTree(preOrder: List<DrawableVertexType>, inOrder: List<DrawableVertexType>)

    fun deleteTree()

    fun getSavedTreesNames(): List<String>

    fun insert(item: Container<Int, String>)

    fun delete(item: Container<Int, String>)
}
