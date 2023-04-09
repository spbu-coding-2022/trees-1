package treelib.rbTree

import treelib.abstractTree.StateContainer

/*
DELETE: contentNode - parent of the MinRightLeaf (or MaxLeftLeaf)
                    - in case when has been deleted root - new root
INSERT: contentNode - inserted Node
FIND: contentNode   - found value
* */
class RBStateContainer<V : Comparable<V>>(
    override val contentNode: RBNode<V>?,
) : StateContainer<V, RBNode<V>>
