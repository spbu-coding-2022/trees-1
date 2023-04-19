package treeTests

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import treelib.rbTree.RBTree

@DisplayName("Test: Red-Black Tree")
class RBTreeTest {
    private var rbTree = RBTree<Int, Int>()

    @Test
    fun `insert two elements`(){
        rbTree.putItem(Pair(25, 1))
        rbTree.putItem(Pair(15, 1))
    }
}