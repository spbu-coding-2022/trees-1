package utils

import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer
import treelib.abstractTree.Tree
import treelib.abstractTree.TreeStruct
import treelib.singleObjects.Container


class TreeWrapper <
        V : Comparable<V>,
        Value,
        NodeType: Node<Container<V, Value?>, NodeType>,
        State: StateContainer<Container<V, Value?>, NodeType>,
        TStruct: TreeStruct<Container<V, Value?>, NodeType, State>,
        Wood : Tree<V, Value,  NodeType, State>> {
    fun getPrivateNode(tree: Wood, name: String = "treeStruct"): TStruct {
        val treeS = tree.javaClass.getDeclaredField(name)
        treeS.isAccessible = true
        val treeStruct = treeS.get(tree)

        return treeStruct as TStruct
    }
}