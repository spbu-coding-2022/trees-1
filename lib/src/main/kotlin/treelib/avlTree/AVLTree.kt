package treelib.avlTree

import treelib.abstractTree.Tree

import treelib.singleObjects.Container

class AVLTree<K : Comparable<K>, V> :
    Tree<K, V, AVLNode<Container<K, V>>>() {
    override val treeStruct: AVLStruct<Container<K, V>> = AVLStruct()
}