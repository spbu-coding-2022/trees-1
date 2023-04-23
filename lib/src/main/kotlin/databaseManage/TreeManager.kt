package databaseManage

import databaseSave.DrawableVertex
import treelib.commonObjects.Container

interface TreeManager<Pack : Container<Int, String>, DrawableVertexType : DrawableVertex<Pack>> {

    fun getTree(treeName: String): Pair<List<DrawableVertexType>, List<DrawableVertexType>>

    fun saveTree(preOrder: Array<DrawableVertexType>, inOrder: Array<DrawableVertexType>, treeName: String)

    fun deleteTree(treeName: String)

    fun getSavedTreesNames(): List<String>

    fun isTreeExist(): Boolean
}