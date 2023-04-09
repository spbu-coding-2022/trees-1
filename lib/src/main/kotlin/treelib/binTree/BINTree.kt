package treelib.binTree

import treelib.abstractTree.Tree
import treelib.singleObjects.Container

class BINTree<K : Comparable<K>, V> : Tree<K, V, BINNode<Container<K, V>>>() {
    override val treeStruct: BINStruct<Container<K, V>> = BINStruct()
}