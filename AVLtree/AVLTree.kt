package AVLtree

import AbstractTree.Tree

import Single_Objects.Container

class AVLTree<Key : Comparable<Key>, Value> :
    Tree<Key, Value, AVLNode<Container<Key, Value>>>() {
    override val treeStruct: AVLStruct<Container<Key, Value>> = AVLStruct()
}