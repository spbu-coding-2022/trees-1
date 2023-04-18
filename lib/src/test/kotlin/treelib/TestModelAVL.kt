import treelib.avlTree.AVLNode

class TestModelAVL {
    val firstTreeValues =
        listOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                   null,      null,        null,       null,       null,       null,       null,       null,
                          1,                      null,                  null,                  null,
                                    14,                                            null,
                                                          23)
    val secondTreeValues =
        listOf(null, null, 45, null, null, null, null, null, null, null, null, null, null, null, null, null,
                   1,        47,          63,        90,         163,        208,        315,        999,
                        44,                    72,                     205,                     507,
                                  57,                                                230,
                                                             101)
    val thirdTreeValues =
        listOf(null, 33, null, null, 69, 72, null, null, null, null, 174, null, null, null, 293, 370,
                   10,        63,      70,       93,        139,       180,         240,       351,
                         59,                 74,                  163,                    285,
                                  67,                                          192,
                                                      104)
    val thirdTreeValuesCase2 =
        listOf(null, 33, null, null, 69, 72, null, null, null, null, 174, null, null, 293, null, null,
                    10,       63,      70,       93,         139,      180,         285,       370,
                         59,                74,                    163,                   351,
                                   67,                                          192,
                                                        104)
    val thirdTreeValuesCase3 =
        listOf(null, 33, null, null, 69, 72, null, null, null, null, 174, null, null, null, null, null,
                    10,       63,      70,       93,         139,      180,         285,       351,
                         59,                74,                    163,                   293,
                                  67,                                          192,
                                                      104)
    val thirdTreeValuesCase4 =
        listOf(null, 33, null, null, 69, 72, null, null, null, null, null, null, null, null, null, null,
                    10,       63,      70,       93,         163,      180,         285,       351,
                         59,                74,                    174,                   293,
                                  67,                                          192,
                                                       104)

    fun <T: Comparable<T>>getTree(values: List<T?>): AVLNode<T> {
        val nodes = mutableListOf<AVLNode<T>?>()
        for (i in 0..15) {
            if (values[i] != null) {
                nodes.add(AVLNode(values[i]!!, null, null))
            }
            else
                nodes.add(null)
        }
        for (i in 16..29) {
            if (values[i] != null) {
                nodes.add(AVLNode(values[i]!!, nodes[2*(i-16)], nodes[2*(i-16)+1]))
                updateHeight(nodes[i])
            }
            else
                nodes.add(null)
        }
        nodes.add(AVLNode(values[30]!!, nodes[28], nodes[29]))

        return nodes[30]!!
    }
    private fun <T: Comparable<T>>updateHeight(currentNode: AVLNode<T>?) {
        if (currentNode != null)
            currentNode.height = ( maxOf(getHeight(currentNode.left), getHeight(currentNode.right)) + 1u)

    }
    private fun <T: Comparable<T>>getHeight(currentNode: AVLNode<T>?): UInt {
        return currentNode?.height ?: 0u
    }


}