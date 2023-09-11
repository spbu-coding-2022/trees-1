package treelib.rbTree

import treelib.abstractTree.StateContainer

class RBStateContainer<V : Comparable<V>>(
    override val contentNode: RBNode<V>?,
) : StateContainer<V, RBNode<V>>
