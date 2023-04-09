package treelib.abstractTree

import treelib.singleObjects.Container

abstract class Tree<K : Comparable<K>, V, NodeType: Node<Container<K, V>, NodeType>> {
    protected abstract val treeStruct: TreeStruct<Container<K, V>, NodeType>
    var size: ULong = 0u

    fun putItem(item: Pair<K, V>){
        treeStruct.insert(Container(item))
        TODO("change size (if the added element is not a duplicate)")
    }

    fun putItems(vararg items: Pair<K, V>) {
        for (item in items) {
            putItem(item)
        }
    }

    fun deleteItem(key: K){
        treeStruct.delete( Container(key to null) )
        TODO("change size (if the deleted item is not a duplicate)")
    }

    fun getItem(key: K) {
        treeStruct.find( Container(key to null) )
    }

    fun inOrder(): List<K> {
        // https://stackoverflow.com/questions/46869353/kotlin-generics-arrayt-results-in-cannot-use-t-as-a-reified-type-parameter-u
        return treeStruct.inOrder().map { it.key }
    }

    fun preOrder(): List<K> {
        // Хорошо бы подумать над тем, чтобы array возвращать, хотя list вроде расширяет iterable, так что обход
        // все равно O(n) будет
        return treeStruct.preOrder().map { it.key }
    }

    fun postOrder(): List<K> {
        return treeStruct.postOrder().map { it.key }
    }
}