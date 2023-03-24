package treeLib.AVLtree

import treeLib.AbstractTree.Tree

import treeLib.Single_Objects.Container

class AVLTree<K : Comparable<K>, V> :
    Tree<K, V, AVLNode<Container<K, V>>>() {
    override val treeStruct: AVLStruct<Container<K, V>> = AVLStruct()
}