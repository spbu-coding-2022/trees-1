package treelib.rbTree

import treelib.abstractTree.Tree

import treelib.commonObjects.Container

class RBTree<K : Comparable<K>, V> :
    Tree<K, V, RBNode<Container<K, V?>>, RBStateContainer<Container<K, V?>>, RBVertex<Container<K, V?>>>() {

    override val treeStruct = RBStruct<Container<K, V?>>()

    operator fun set(key: K, value: V) = putItem(key to value)

    override operator fun get(key: K) = getItem(key)
}
