package treelib.abstractTree.balanced

import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer
import treelib.abstractTree.TreeStruct

abstract class BalancedTreeStruct<
        Pack : Comparable<Pack>,
        NodeType : Node<Pack, NodeType>,
        State : StateContainer<Pack, NodeType>,
        BalancerType : Balancer<Pack, NodeType, State>,
        > : TreeStruct<Pack, NodeType, State>() {

    protected abstract val balancer: BalancerType

    override fun insert(item: Pack): Pack? {
        val currenNode = insertItem(item)
        if (currenNode != null) {
            root = balancer.balance(currenNode)
        }
        return item
    }

    // если удалил рут, то не запускаем баланс
    override fun delete(item: Pack): Pack? {
        // TODO передаю родителя + что делать если: root null, root заменил я (а не Артем внутри)
        // TODO если вернулся рут, то нифига не запускать
        val currenNode = deleteItem(item)
        if (currenNode != null) {
            root = balancer.balance(currenNode)
            return item
        } else return null
    }
}
