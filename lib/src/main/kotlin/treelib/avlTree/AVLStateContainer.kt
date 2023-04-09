package treelib.avlTree

import treelib.abstractTree.StateContainer

/*
DELETE: contentNode - parent of the MinRightLeaf (or MaxLeftLeaf)
                    - in case when has been deleted root - new root
INSERT: contentNode - parent of the inserted Node
                    - in case when has been inserted root - new root
FIND: contentNode   - found value
* */
class AVLStateContainer<V : Comparable<V>>(
    override val contentNode: AVLNode<V>?,
    val root: AVLNode<V>?,
) : StateContainer<V, AVLNode<V>>
