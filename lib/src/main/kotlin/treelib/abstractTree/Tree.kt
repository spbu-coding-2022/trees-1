package treelib.abstractTree

import treelib.singleObjects.Container

abstract class Tree<
        Key : Comparable<Key>,
        Value,
        NodeType : Node<Container<Key, Value>, NodeType>,
        State : StateContainer<Container<Key, Value>, NodeType>
        > {

    protected abstract val treeStruct: TreeStruct<Container<Key, Value>, NodeType, State>

    fun putItem(item: Pair<Key, Value>) {
        TODO("Not yet implemented")
    }

    fun putItems(vararg items: Pair<Key, Value>) {
        TODO("Not yet implemented")
    }

    fun deleteItem(key: Key) {
        TODO("Not yet implemented")
    }

    fun getItem(key: Key) {
        TODO("Not yet implemented")
    }

    fun inOrder() {
        TODO("Not yet implemented")
    }

    fun preOrder() {
        TODO("Not yet implemented")
    }

    fun postOrder() {
        TODO("Not yet implemented")
    }
}
