package treelib.rbTree

import treelib.abstractTree.Tree

import treelib.singleObjects.Container

class RBTree<Key : Comparable<Key>, Value> : Tree<Key, Value, RBNode<Container<Key, Value>>>() {
    override val treeStruct: RBStruct<Container<Key, Value>> = RBStruct()
}