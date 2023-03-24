package treeLib.BINtree

import treeLib.AbstractTree.Tree
import treeLib.Single_Objects.Container

class BINTree<K : Comparable<K>, V> : Tree<K, V, BINNode<Container<K, V>>>() {
    override val treeStruct: BINStruct<Container<K, V>> = BINStruct()
}