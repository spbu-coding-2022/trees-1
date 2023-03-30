package treelib.rbTree

import treelib.abstractTree.StateContainer

/*
Insert: contentNode - the node on which the action was performed
Delete: contentNode - the parent of the node on which the action was performed
* */
class RBStateContainer<V : Comparable<V>>(
    override val contentNode: RBNode<V>,
) : StateContainer<V, RBNode<V>>
