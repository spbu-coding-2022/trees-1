package treelib.avlTree

import treelib.abstractTree.Tree

import treelib.commonObjects.Container

class AVLTree<K : Comparable<K>, V> :
    Tree<K, V, AVLNode<Container<K, V?>>, AVLStateContainer<Container<K, V?>>, AVLVertex<Container<K, V?>>>() {

    override val treeStruct = AVLStruct<Container<K, V?>>()

    operator fun AVLTree<K, V>.get(key: K): V? = getItem(key)

    operator fun AVLTree<K, V>.set(key: K, value: V) = putItem(key to value)
}
