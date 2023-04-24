package controller

import databaseManage.AVLTreeManager
import databaseManage.BINTreeManager
import databaseManage.RBTreeManager
import java.io.IOException

class Controller {
    private val avlManager = AVLTreeManager()
    private val rbManager = RBTreeManager()
    private val binManager = BINTreeManager()

    fun showFiles(): List<List<String>> {
        avlManager.initDatabase("DBname")
        return listOf(rbManager.getSavedTreesNames(), avlManager.getSavedTreesNames(), binManager.getSavedTreesNames())
    }

    fun foo(fileName: String) {
        if (fileName == "myTree.json")
            throw IOException()
    }

}

/*
<Pack : Container<String, Int>, NodeType : Node<Pack, NodeType>, State : StateContainer<Pack, NodeType>,
        VertexType : Vertex<Pack>, TreeType : TreeStruct<Pack, NodeType, State, VertexType>>
 */

/*
       val RBtree = RBStruct<Container<Int, String>>()
       RBtree.restoreStruct(orders.first, orders.second)
       neo4jDB.close()
*/

/*
val BINtree = BINStruct<Container<Int, String>>()
        BINtree.restoreStruct(preOrder.toList())
 */