package treelib.binTree

import treelib.abstractTree.Tree
import treelib.singleObjects.Container

class BINTree<K : Comparable<K>, V>
    : Tree<K, V, BINNode<Container<K, V?>>, BINStateContainer<Container<K, V?>>, BINVertex<Container<K, V?>>>() {

    override val treeStruct = BINStruct<Container<K, V?>>()

    operator fun BINTree<K, V>.get(key: K): V? = getItem(key)

    operator fun BINTree<K, V>.set(key: K, value: V) = putItem(key to value)
}
