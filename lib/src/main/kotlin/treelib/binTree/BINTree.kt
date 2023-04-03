package treelib.binTree

import treelib.abstractTree.Tree
import treelib.singleObjects.Container

class BINTree<Key : Comparable<Key>, Value>
    : Tree<Key, Value, BINNode<Container<Key, Value?>>, BINStateContainer<Container<Key, Value?>>>() {

    override val treeStruct = BINStruct<Container<Key, Value?>>()

    operator fun BINTree<Key, Value>.get(key: Key): Value? = getItem(key)

    operator fun BINTree<Key, Value>.set(key: Key, value: Value) = putItem(key to value)
}
