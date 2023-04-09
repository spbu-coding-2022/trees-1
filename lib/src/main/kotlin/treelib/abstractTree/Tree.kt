package treelib.abstractTree

import treelib.singleObjects.Container

abstract class Tree<
        Key : Comparable<Key>,
        Value,
        NodeType : Node<Container<Key, Value?>, NodeType>,
        State : StateContainer<Container<Key, Value?>, NodeType>
        > {

    protected abstract val treeStruct: TreeStruct<Container<Key, Value?>, NodeType, State>

    private fun wrapForFind(key: Key) = Container<Key, Value?>(key to null)

    fun putItem(item: Pair<Key, Value?>) {
        treeStruct.insert(Container(item))
    }

    fun putItems(vararg items: Pair<Key, Value?>) {
        for (element in items) putItem(element)
    }

    fun putItems(items: Iterable<Pair<Key, Value?>>) {
        for (element in items) putItem(element)
    }

    fun getItem(key: Key): Value? = treeStruct.find(wrapForFind(key))?.value

    fun deleteItem(key: Key) {
        if (getItem(key) == null) throw Exception("Attempt to remove a non-existent element")
        treeStruct.delete(wrapForFind(key))
    }

    private fun createPoorList(info: List<Container<Key, Value?>>): List<Pair<Key, Value?>> {
        val returnInfo = mutableListOf<Pair<Key, Value?>>()
        for (element in info) returnInfo.add(element.pair)
        return returnInfo
    }

    fun inOrder(): List<Pair<Key, Value?>> = createPoorList(treeStruct.inOrder())

    fun preOrder(): List<Pair<Key, Value?>> = createPoorList(treeStruct.preOrder())

    fun postOrder(): List<Pair<Key, Value?>> = createPoorList(treeStruct.postOrder())
}
