package treelib.binTree

import treelib.abstractTree.StateContainer

/*Content node - the node on which the action was performed*/
class BINStateContainer<V : Comparable<V>>(
    override val contentNode: BINNode<V>?,
) : StateContainer<V, BINNode<V>>
