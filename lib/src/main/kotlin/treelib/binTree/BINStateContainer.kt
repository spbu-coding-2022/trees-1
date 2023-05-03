package treelib.binTree

import treelib.abstractTree.StateContainer

class BINStateContainer<V : Comparable<V>>(
    override val contentNode: BINNode<V>?,
) : StateContainer<V, BINNode<V>>
