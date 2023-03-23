package TreeLib.RBtree

import TreeLib.AbstractTree.Tree

import TreeLib.Single_Objects.Container

class RBTree<Key : Comparable<Key>, Value> : Tree<Key, Value, RBNode<Container<Key, Value>>>() {
    override val treeStruct: RBStruct<Container<Key, Value>> = RBStruct()
}