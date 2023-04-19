package utils

import treelib.abstractTree.*
import treelib.singleObjects.Container


class TreeWrapper<
        V : Comparable<V>,
        Value,
        NodeType : Node<Container<V, Value?>, NodeType>,
        VertexType : Vertex<Container<V, Value?>>,
        State : StateContainer<Container<V, Value?>, NodeType>,
        TStruct : TreeStruct<Container<V, Value?>, NodeType, State, VertexType>,
        Wood : Tree<V, Value, NodeType, State, VertexType>> {
    fun getPrivateNode(tree: Wood, name: String = "treeStruct"): TStruct {
        val treeS = tree.javaClass.getDeclaredField(name)
        treeS.isAccessible = true
        val treeStruct = treeS.get(tree)

        return treeStruct as TStruct
    }
}