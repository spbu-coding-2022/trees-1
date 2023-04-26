package treelib.avlTree

import treelib.abstractTree.Tree

import treelib.singleObjects.Container

class AVLTree<K : Comparable<K>, V> :
    Tree<K, V, AVLNode<Container<K, V?>>, AVLStateContainer<Container<K, V?>>, AVLVertex<Container<K, V?>>>() {

    override val treeStruct = AVLStruct<Container<K, V?>>()

    operator fun set(key: K, value: V) = putItem(key to value)

    operator fun get(key: K) = getItem(key)
}
