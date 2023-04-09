package treelib.binTree

import treelib.abstractTree.Node

class BINNode<Pack : Comparable<Pack>>(
    override var value: Pack,
    override var left: BINNode<Pack>?,
    override var right: BINNode<Pack>?
) : Node<Pack, BINNode<Pack>>