package treelib.avlTree

import treelib.abstractTree.StateContainer

class AVLStateContainer<V : Comparable<V>>(
    override val contentNode: AVLNode<V>?,
    val root: AVLNode<V>?,
) : StateContainer<V, AVLNode<V>>
