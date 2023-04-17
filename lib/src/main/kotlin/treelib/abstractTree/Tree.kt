package treelib.abstractTree

import treelib.singleObjects.Container
import treelib.singleObjects.exceptions.NonExistentValueException

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
        if (getItem(key) == null) throw NonExistentValueException()
        treeStruct.delete(wrapForFind(key))
    }

    private fun createPoorList(info: List<NodeType>): List<NodeType> {
        val returnInfo = mutableListOf<NodeType>()
        for (element in info) returnInfo.add(element)
        return returnInfo
    }

    fun inOrder(): List<NodeType> = createPoorList(treeStruct.inOrder())

    fun preOrder(): List<NodeType> = createPoorList(treeStruct.preOrder())

    fun postOrder(): List<NodeType> = createPoorList(treeStruct.postOrder())
}
