package TreeLib.AVLtree

import TreeLib.AbstractTree.Tree

import TreeLib.Single_Objects.Container

class AVLTree<K : Comparable<K>, V> :
    Tree<K, V, AVLNode<Container<K, V>>>() {
    override val treeStruct: AVLStruct<Container<K, V>> = AVLStruct()
}