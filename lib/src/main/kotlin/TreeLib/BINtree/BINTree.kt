package TreeLib.BINtree

import TreeLib.AbstractTree.Tree
import TreeLib.Single_Objects.Container

class BINTree<Key : Comparable<Key>, Value> : Tree<Key, Value, BINNode<Container<Key, Value>>>() {
    override val treeStruct: BINStruct<Container<Key, Value>> = BINStruct()
}