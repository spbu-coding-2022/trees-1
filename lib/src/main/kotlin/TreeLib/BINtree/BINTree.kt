package TreeLib.BINtree

import TreeLib.AbstractTree.Tree
import TreeLib.Single_Objects.Container

class BINTree<K : Comparable<K>, V> : Tree<K, V, BINNode<Container<K, V>>>() {
    override val treeStruct: BINStruct<Container<K, V>> = BINStruct()
}