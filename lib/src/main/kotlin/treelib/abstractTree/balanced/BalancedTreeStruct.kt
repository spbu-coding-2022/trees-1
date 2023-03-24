package treelib.abstractTree.balanced

import treelib.abstractTree.Node
import treelib.abstractTree.TreeStruct

abstract class BalancedTreeStruct<Pack : Comparable<Pack>, NodeType : Node<Pack, NodeType>, BalancerType : Balancer<Pack, NodeType>> :
    TreeStruct<Pack, NodeType>() {

    protected abstract val balancer: BalancerType

    protected fun getLeafForInsert(item: Pack): NodeType? {
        var currentNode = root
        while (true) {
            if (currentNode != null) {
                if (item > currentNode.value) {
                    if (currentNode.right != null) currentNode = currentNode.right
                    else return currentNode
                } else {
                    if (currentNode.left != null) currentNode = currentNode.left
                    else return currentNode
                }
            } else return null
        }
    }

    override fun insert(item: Pack): Pack? {
        val currenNode = insertItem(item)
        if (currenNode != null) {
            root = balancer.balance(currenNode)
            return item
        } else return null
    }
// если удалил рут, то не запускаем баланс
    override fun delete(item: Pack): Pack? {
        val currenNode = deleteItem(item)
        if (currenNode != null) {
            root = balancer.balance(currenNode)
            return item
        } else return null
    }
}