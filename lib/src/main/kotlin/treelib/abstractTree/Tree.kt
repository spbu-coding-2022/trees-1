package treelib.abstractTree

import treelib.commonObjects.Container
import treelib.commonObjects.exceptions.VauleNotFound

abstract class Tree<
        K : Comparable<K>,
        V,
        NodeType : Node<Container<K, V?>, NodeType>,
        State : StateContainer<Container<K, V?>, NodeType>,
        VertexType : Vertex<Container<K, V?>>
        > {

    protected abstract val treeStruct: TreeStruct<Container<K, V?>, NodeType, State, VertexType>

    private fun wrapForFind(key: K) = Container<K, V?>(key to null)

    fun putItem(item: Pair<K, V?>) {
        treeStruct.insert(Container(item))
    }

    fun putItem(vararg items: Pair<K, V?>) {
        for (element in items) putItem(element)
    }

    fun putItem(items: Iterable<Pair<K, V?>>) {
        for (element in items) putItem(element)
    }

    fun getItem(key: K): V? = treeStruct.find(wrapForFind(key))?.value

    open operator fun get(key: K): V? = treeStruct.find(wrapForFind(key))?.value

    fun deleteItem(key: K) {
        if (treeStruct.find(wrapForFind(key)) == null) throw VauleNotFound()
        treeStruct.delete(wrapForFind(key))
    }

    private fun createPoorList(info: List<VertexType>): List<Pair<K, V?>> {
        val returnInfo = mutableListOf<Pair<K, V?>>()
        for (element in info) {
            returnInfo.add(element.value.pair)
        }
        return returnInfo
    }

    fun inOrder(): List<Pair<K, V?>> = createPoorList(treeStruct.inOrder())

    fun preOrder(): List<Pair<K, V?>> = createPoorList(treeStruct.preOrder())

    fun postOrder(): List<Pair<K, V?>> = createPoorList(treeStruct.postOrder())
}
