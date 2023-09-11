package treeApp.controller.databaseManage

import treelib.databaseSave.DrawableVertex
import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer
import treelib.abstractTree.TreeStruct
import treelib.abstractTree.Vertex
import treelib.commonObjects.Container

interface TreeManager<
        Pack: Container<Int, String>,
        DVertexType : DrawableVertex<Pack>,
        NodeType : Node<Pack, NodeType>,
        State : StateContainer<Pack, NodeType>,
        VertexType : Vertex<Pack>,
        StructType: TreeStruct<Pack, NodeType, State, VertexType>
        > {

    fun initTree(name: String, tree: StructType): List<DVertexType>

    fun getVertexesForDrawFromTree(tree: StructType): List<DVertexType>

    fun getVertexesForDrawFromDB(name: String): List<DVertexType>

    fun saveTreeToDB(name: String, preOrder: List<DVertexType>, inOrder: List<DVertexType>)

    fun saveTreeToDB(name: String, tree: StructType)

    fun deleteTreeFromDB(name: String)

    fun getSavedTreesNames(): List<String>

}
