package utils

import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer
import treelib.abstractTree.TreeStruct
import treelib.abstractTree.Vertex

class TreeStructWrapper<V : Comparable<V>, NodeType : Node<V, NodeType>, VertexType: Vertex<V>, State: StateContainer<V, NodeType>, TStruct : TreeStruct<V, NodeType, State, VertexType>> {

    fun getPrivateNode(tree: TStruct, name: String = "root"): NodeType? {
        val field = tree.javaClass.getDeclaredField(name)
        field.isAccessible = true
        val root = field.get(tree)
        return if (root == null) null
        else root as? NodeType
    }

    fun executePrivateMethod(tree: TStruct, name: String, parameterValues: Array<Any>?=null, vararg parameterTypes: Class<*>):Any? {
        val method = tree.javaClass.getDeclaredMethod(name, *parameterTypes)
        method.isAccessible = true
        return if (parameterValues != null) method.invoke(tree, *parameterValues)
        else method.invoke(tree)
    }
}