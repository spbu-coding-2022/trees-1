package treelib.rbTree

import treelib.abstractTree.Tree

import treelib.singleObjects.Container

class RBTree<K : Comparable<K>, V> :
    Tree<K, V, RBNode<Container<K, V?>>, RBStateContainer<Container<K, V?>>, RBVertex<Container<K, V?>>>() {

    override val treeStruct = RBStruct<Container<K, V?>>()

    operator fun RBTree<K, V>.get(key: K): V? = getItem(key)

    operator fun RBTree<K, V>.set(key: K, value: V) = putItem(key to value)
}
