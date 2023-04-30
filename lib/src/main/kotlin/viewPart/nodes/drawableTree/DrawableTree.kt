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
        >: DrawTree<DNodeType> {

    protected abstract var drawablePreOrder: List<DNodeType>?
    protected abstract val treeManager: TreeManager<Container<Int, String>, DVertexType, NodeType, State, VertexType, StructType>
    protected abstract val treeStruct: StructType

    override var yShiftBetweenNodes = 10f

    override fun initTree() {
        val binVertexes = treeManager.initTree(name, treeStruct)
        drawablePreOrder = binVertexes.map { drawableVertexToNode(it) }

        drawablePreOrder?.let {
            if (it.isNotEmpty()) {
                restoreTree(it)
            }
        }
    }

    override fun updateTree() {
        root = null
        val ded = treeStruct.preOrder()
        for (el in vertexesToNodes(ded)) {
            restoreInsert(el)
        }
    }

    override fun deleteTree() = treeManager.deleteTreeFromDB(name)

    override fun saveTree() {
        if (root != null) {
            treeManager.saveTreeToDB(name, preOrder().map { nodeToDrawableVertex(it) }.toList(), listOf())
        } else {
            treeManager.saveTreeToDB(name, treeStruct)
        }
    }

    override fun insert(item: Container<Int, String>) = treeStruct.insert(item)

    override fun delete(item: Container<Int, String>) {
        if (treeStruct.find(item) != null) treeStruct.delete(item)
    }

    override fun find(item: Container<Int, String>): Container<Int, String>? = treeStruct.find(item)

    override fun repositisonTree(xBase: Float, yBase: Float) {
        /*xBase: Float = 0f, yBase: Float = 0f*/
        root?.let {
            createCordsState1(it, xBase, yBase)
            createCordsState2(it)
        }
    }

    protected abstract fun vertexToNode(vertex: VertexType): DNodeType

    protected abstract fun nodeToDrawableVertex(node: DNodeType): DVertexType

    protected abstract fun drawableVertexToNode(vertex: DVertexType): DNodeType

    private fun restoreTree(preOrder: List<DNodeType>) {
        root = null
        for (el in preOrder) {
            restoreInsert(el)
        }
    }

    private fun restoreInsert(preOrderNode: DNodeType) {
        if (root == null) {
            root = preOrderNode
            return
        }
        var currentParent = root
        while (currentParent != null) {
            currentParent?.let {
                when {
                    it.value < preOrderNode.value -> {
                        if (it.rightChild == null) {
                            it.rightChild = preOrderNode
                            return@restoreInsert
                        }
                        else currentParent = it.rightChild
                    }

                    it.value > preOrderNode.value -> {
                        if (it.leftChild == null) {
                            it.leftChild = preOrderNode
                            return@restoreInsert
                        }
                        else currentParent = it.leftChild
                    }

                    else -> {
                        println(currentParent!!.value)
                        println(preOrderNode.value)
                        throw InternalError("Can't restore tree from preOrder :(")
                    }
                }
            }
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

    private fun preOrder() = sequence{
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

    private fun vertexesToNodes(preOrder: List<VertexType>) = sequence {
        for (el in preOrder) {
            yield(
                vertexToNode(el)
            )
        }
    }
}
