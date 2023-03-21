package BINtree

import AbstractTree.Tree
import Single_Objects.Container

class BINTree<Key : Comparable<Key>, Value> : Tree<Key, Value, BINNode<Container<Key, Value>>>() {
    override val treeStruct: BINStruct<Container<Key, Value>> = BINStruct()
}