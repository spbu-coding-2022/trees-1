package viewPart.nodes.drawableTree

import databaseManage.TreeManager
import databaseSave.DrawableVertex
import treelib.abstractTree.Node
import treelib.abstractTree.StateContainer
import treelib.abstractTree.TreeStruct
import treelib.abstractTree.Vertex
import treelib.commonObjects.Container
import treelib.commonObjects.exceptions.ImpossibleCaseException


abstract class DrawableTree<
        DNodeType : DrawableNode<Container<Int, String>, DNodeType>,
        DVertexType : DrawableVertex<Container<Int, String>>,
        NodeType : Node<Container<Int, String>, NodeType>,
        State : StateContainer<Container<Int, String>, NodeType>,
        VertexType : Vertex<Container<Int, String>>,
        StructType : TreeStruct<Container<Int, String>, NodeType, State, VertexType>
        > {

    abstract val name: String
    internal abstract var root: DNodeType?
    protected abstract var drawablePreOrder: List<DNodeType>?
    protected abstract val treeManager: TreeManager<Container<Int, String>, DVertexType, NodeType, State, VertexType, StructType>
    protected abstract val treeStruct: StructType
    abstract val designNode: NodeDesign

    val yShiftBetweenNodes = 10f

    abstract fun initTree()
    abstract fun deleteTree()
    abstract fun saveTree()

    abstract fun updateTree()

    fun insert(item: Container<Int, String>) = treeStruct.insert(item)

    fun delete(item: Container<Int, String>) {
        if (treeStruct.find(item) != null) treeStruct.delete(item)
    }

    //fun find(item: Container<Int, String>): Container<Int, String>? = treeStruct.find(item)
    fun find(item: Int): Container<Int, String>? {
        var currentNode = root
        if (root == null)
            return null
        while(true) {
            if (item == currentNode?.value?.key) {
                currentNode.clickState.value = true
                return Container(Pair(item, currentNode.value.value))
            }
            currentNode?.let {
                if (item > it.value.key) currentNode = it.rightChild
                else currentNode = it.leftChild
            }
            if (currentNode == null) return null
        }

    }

    fun repositisonTree(xBase: Float = 0f, yBase: Float = 0f) {
        root?.let {
            createCordsState1(it, xBase, yBase)
            createCordsState2(it)
        }
    }

    private fun createCordsState1(node: DNodeType, x: Float, y: Float): Float {
        if (node.leftChild == null && node.rightChild == null) {
            node.xState.value = x
            node.yState.value = y
            return x + designNode.nodeSize
        }

        var xInfo = node.leftChild?.let {
            return@let createCordsState1(it, x, y + designNode.nodeSize + yShiftBetweenNodes)
        } ?: x

        node.xState.value = xInfo
        node.yState.value = y
        xInfo += designNode.nodeSize

        node.rightChild?.let {
            return@createCordsState1 createCordsState1(it, xInfo, y + designNode.nodeSize + yShiftBetweenNodes)
        }
        return xInfo
    }

    private fun createCordsState2(node: DNodeType): Float {
        if (node.leftChild == null && node.rightChild == null) return node.xState.value
        val xLeft = node.leftChild?.let { return@let createCordsState2(it) } ?: -1f
        val xRight = node.rightChild?.let { return@let createCordsState2(it) } ?: -1f

        if ((xLeft == -1f) || (xRight == -1f)) return node.xState.value

        val n = xLeft + (xRight - xLeft) / 2
        node.xState.value = n
        return n
    }

    protected fun preOrder() = sequence{
        var current: DNodeType
        val queue = ArrayDeque<DNodeType>()

        root?.let { root ->
            queue.add(root)
            while (queue.isNotEmpty()) {
                current = queue.removeLast()
                yield(current)
                if (current.rightChild != null)
                    current.rightChild?.let {
                        queue.add(it)
                    } ?: throw ImpossibleCaseException()

                if (current.leftChild != null)
                    current.leftChild?.let {
                        queue.add(it)
                    } ?: throw ImpossibleCaseException()
            }
        }
    }
}
