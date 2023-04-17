package utils

import treelib.avlTree.AVLNode
import kotlin.math.abs
import kotlin.math.max

class AVLAnalyzer<V: Comparable<V>>(override val assertMethod: (input: String) -> Unit) : Analyzer<V, AVLNode<V>>() {
    private var heightL = 0
    private var heightR = 0
    private var heightMax = 0
    private var mainRight: AVLNode<V>? = null

    override fun checkTree(root: AVLNode<V>) {
        mainRight = root.right
        descent(root, 0)
        heightR = heightMax
        if (abs(heightR - heightL) > 1) {
            wrappedAssertMethod("the invariant is not observed")
        }
    }

    private fun descent(node: AVLNode<V>, height: Int) {
        heightMax = max(heightMax, height)
        val left = node.left
        val right = node.right

        if (left != null) {
            descent(left, height + 1)
        }

        if (right == mainRight && heightR == 0) {
            heightL = heightMax
            heightMax = 0
        }

        if (right != null) {
            descent(right, height + 1)
        }
    }
}
