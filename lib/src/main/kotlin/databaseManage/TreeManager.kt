package databaseManage

import databaseSave.DrawableVertex
import treelib.commonObjects.Container

interface TreeManager<Pack: Container<Int, String>, DrawableVertexType : DrawableVertex<Pack>> {

    val currentTreeName: String

    fun initTree(treeName: String): MutableList<DrawableVertexType>

    fun getVertexesForDrawFromTree(): MutableList<DrawableVertexType>

    fun getVertexesForDrawFromDB(): MutableList<DrawableVertexType>

    fun saveTree(vertexes: MutableList<DrawableVertexType>, inOrder: MutableList<DrawableVertexType>)

    fun deleteTree()

    fun getSavedTreesNames(): List<String>

    fun insert(item: Container<Int, String>)

    fun delete(item: Container<Int, String>)
}
