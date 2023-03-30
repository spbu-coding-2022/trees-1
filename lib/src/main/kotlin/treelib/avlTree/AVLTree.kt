package treelib.avlTree

import treelib.abstractTree.Tree

import treelib.singleObjects.Container

class AVLTree<Key : Comparable<Key>, Value> :
    Tree<Key, Value, AVLNode<Container<Key, Value>>, AVLStateContainer<Container<Key, Value>>>() {

    override val treeStruct = AVLStruct<Container<Key, Value>>()
}
