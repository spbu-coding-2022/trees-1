package treelib.rbTree

import treelib.abstractTree.Tree

import treelib.singleObjects.Container

class RBTree<Key : Comparable<Key>, Value> :
    Tree<Key, Value, RBNode<Container<Key, Value?>>, RBStateContainer<Container<Key, Value?>>>() {

    override val treeStruct = RBStruct<Container<Key, Value?>>()

    operator fun RBTree<Key, Value>.get(key: Key): Value? = getItem(key)

    operator fun RBTree<Key, Value>.set(key: Key, value: Value) = putItem(key to value)
}
