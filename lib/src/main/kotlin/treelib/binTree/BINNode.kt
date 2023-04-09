package treelib.binTree

import treelib.abstractTree.Node

class BINNode<Pack : Comparable<Pack>>(
    override var value: Pack,
    override var left: BINNode<Pack>? = null,
    override var right: BINNode<Pack>? = null,
) : Node<Pack, BINNode<Pack>>
