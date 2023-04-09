package treelib.binTree

import treelib.abstractTree.StateContainer

/*
DELETE: contentNode - deleted Node
INSERT: contentNode - inserted Node
FIND: contentNode   - found Node
* */
class BINStateContainer<V : Comparable<V>>(
    override val contentNode: BINNode<V>?,
) : StateContainer<V, BINNode<V>>
