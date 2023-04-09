package treelib.avlTree

import treelib.abstractTree.Tree

import treelib.singleObjects.Container

class AVLTree<Key : Comparable<Key>, Value> :
    Tree<Key, Value, AVLNode<Container<Key, Value?>>, AVLStateContainer<Container<Key, Value?>>>() {

    override val treeStruct = AVLStruct<Container<Key, Value?>>()

    operator fun AVLTree<Key, Value>.get(key: Key): Value? = getItem(key)

    operator fun AVLTree<Key, Value>.set(key: Key, value: Value) = putItem(key to value)
}
