package treelib.avlTree

import treelib.abstractTree.StateContainer

/*Content node - the parent of the node on which the action was performed*/
class AVLStateContainer<V : Comparable<V>>(
    override val contentNode: AVLNode<V>,
    val root: AVLNode<V>,
) : StateContainer<V, AVLNode<V>>
