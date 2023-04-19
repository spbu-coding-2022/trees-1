package treelib

import treelib.rbTree.RBNode
import treelib.singleObjects.Markers

class TestModelRBT {
    fun <T : Comparable<T>> countBlackNodes(node: RBNode<T>): Int {
        var count = 0
        var currentNode: RBNode<T>? = node
        while (currentNode != null) {
            count = if (currentNode.color == Markers.BLACK) count + 1 else count
            currentNode = currentNode.parent
        }
        return count
    }

    fun <T : Comparable<T>> deleteNode(remoteNode: RBNode<T>, newNode: RBNode<T>): RBNode<T> {
        remoteNode.left?.parent = newNode
        remoteNode.right?.parent = newNode
        newNode.left = remoteNode.left
        newNode.right = remoteNode.right
        newNode.color = remoteNode.color
        when (newNode) {
            newNode.parent?.left -> newNode.parent?.left = null
            else -> newNode.parent?.right = null
        }

        if (remoteNode.parent != null) {
            val parent = remoteNode.parent!!
            when (remoteNode) {
                parent.left -> parent.left = newNode
                else -> parent.right = newNode
            }
            newNode.parent = parent
        } else {
            newNode.parent = null
        }

        return newNode
    }

    fun getFourthTree(): MutableList<RBNode<Int>?> {
        val nodes = getSecondTree()
        for (i in 24..27) {
            nodes[i]!!.right = null
            nodes[i]!!.left = null
        }
        nodes[25]!!.right = nodes[19]
        nodes[25]!!.left = nodes[18]
        nodes[25]!!.color = Markers.RED
        nodes[29]!!.color = Markers.BLACK
        nodes[26]!!.color = Markers.RED
        nodes[27]!!.color = Markers.RED

        return nodes
    }

    fun getThirdTree(): MutableList<RBNode<Int>?> {
        val nodes = getSecondTree()
        nodes[28]!!.color = Markers.BLACK
        nodes[24]!!.color = Markers.RED
        nodes[24]?.right = null
        nodes[24]?.left = null
        nodes[25]!!.color = Markers.RED
        nodes[25]?.right = null
        nodes[25]?.left = null
        return nodes

    }

    fun getSecondTree(): MutableList<RBNode<Int>?> {
        val nodes = getFirstTree()
        for (i in 16..23) {
            nodes[i]!!.color = Markers.RED
            nodes[i]!!.right = null
            nodes[i]!!.left = null
        }
        nodes[0]!!.color = Markers.RED
        nodes[16]!!.left = nodes[0]

        return nodes
    }


    fun getFirstTree(): MutableList<RBNode<Int>?> {
        val values = listOf(
            1, 7, 11, 14, 16, 20, 21, null, 29, 38, null, 45, 52, 70, null, null,
            4, 12, 18, 24, 37, 42, 63, 90,
            10, 19, 40, 71,
            15, 50,
            25
        )
        val markers = listOf(
            Markers.BLACK,
            Markers.BLACK,
            Markers.BLACK,
            Markers.BLACK,
            Markers.RED,
            Markers.RED,
            Markers.RED,
            null,
            Markers.RED,
            Markers.RED,
            null,
            Markers.RED,
            Markers.BLACK,
            Markers.BLACK,
            Markers.RED,
            Markers.RED,
            Markers.RED,
            Markers.RED,
            Markers.BLACK,
            Markers.BLACK,
            Markers.BLACK,
            Markers.BLACK,
            Markers.RED,
            Markers.BLACK,
            Markers.BLACK,
            Markers.BLACK,
            Markers.BLACK,
            Markers.BLACK,
            Markers.RED,
            Markers.RED,
            Markers.BLACK

        )
        val nodes = mutableListOf<RBNode<Int>?>()
        for (i in 1..31) {
            when (i) {
                in 1..16 -> {
                    if (values[i - 1] != null)
                        nodes.add(RBNode(values[i - 1]!!, null, null, null, markers[i - 1]!!))
                    else
                        nodes.add(null)

                }

                in 17..24 -> {
                    val delta = i - 17
                    if (values[i - 1] != null)
                        nodes.add(
                            RBNode(
                                values[i - 1]!!,
                                nodes[i - 17 + delta],
                                nodes[i - 17 + delta + 1],
                                null,
                                markers[i - 1]!!
                            )
                        )
                    else
                        nodes.add(null)
                    nodes[i - 17 + delta]?.parent = nodes[i - 1]
                    nodes[i - 17 + delta + 1]?.parent = nodes[i - 1]

                }

                in 25..28 -> {
                    val delta = i - 25
                    nodes.add(
                        RBNode(
                            values[i - 1]!!,
                            nodes[i - 9 + delta],
                            nodes[i - 9 + delta + 1],
                            null,
                            markers[i - 1]!!
                        )
                    )
                    nodes[i - 9 + delta]?.parent = nodes[i - 1]
                    nodes[i - 9 + delta + 1]?.parent = nodes[i - 1]
                }

                in 29..30 -> {
                    val delta = i - 29
                    nodes.add(
                        RBNode(
                            values[i - 1]!!,
                            nodes[i - 5 + delta],
                            nodes[i - 5 + delta + 1],
                            null,
                            markers[i - 1]!!
                        )
                    )
                    nodes[i - 5 + delta]?.parent = nodes[i - 1]
                    nodes[i - 5 + delta + 1]?.parent = nodes[i - 1]
                }

                else -> {
                    nodes.add(RBNode(values[i - 1]!!, nodes[28], nodes[29], null, markers[i - 1]!!))
                    nodes[28]?.parent = nodes[i - 1]
                    nodes[29]?.parent = nodes[i - 1]
                }
            }
        }
        return nodes
    }
}