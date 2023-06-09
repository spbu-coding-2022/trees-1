package treelib

import org.junit.jupiter.api.*
import treelib.binTree.BINNode
import treelib.binTree.BINStateContainer
import treelib.binTree.BINStruct
import treelib.binTree.BINVertex
import treelib.commonObjects.exceptions.VauleNotFound
import utils.BINAnalyzer
import utils.TreeStructWrapper
import kotlin.test.assertEquals

@DisplayName("Test: Binary Search Tree Struct")
class BINStructTest {
    private val treeW = TreeStructWrapper<Int, BINNode<Int>, BINVertex<Int>, BINStateContainer<Int>, BINStruct<Int>>()
    private var treeStruct = BINStruct<Int>()

    private fun testAssert(msg: String): Nothing = fail(msg)

    private val analyzer = BINAnalyzer<Int>(::testAssert)

    @BeforeEach
    fun reInitClassUnderTest() {
        treeStruct = BINStruct()
    }

    @Test
    fun `test delete root`() {
        val num = mutableListOf(5, 3, 7, 1, 9, -1, 4, 2, 0, 6)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeStruct.delete(5)
        val root = treeW.getPrivateNode(treeStruct)?.value

        assertEquals(expected = 6, actual = root)
    }

    @Test
    fun `test insert`() {
        val num = mutableListOf(1, 2, 3, 4, 5, 8)
        for (i in num) {
            treeStruct.insert(i)
        }

        val additionalNum = mutableListOf(1, 2, 3, 5, 7, 8, 11)
        for (i in additionalNum) {
            treeStruct.insert(i)
        }

        val root = treeW.getPrivateNode(treeStruct)?.value
        val node2 = treeW.getPrivateNode(treeStruct)?.right?.value
        val nodeNull = treeW.getPrivateNode(treeStruct)?.left?.value
        val node3 = treeW.getPrivateNode(treeStruct)?.right?.right?.value
        val nodeNull1 = treeW.getPrivateNode(treeStruct)?.right?.left?.value
        val node4 = treeW.getPrivateNode(treeStruct)?.right?.right?.right?.value

        assertEquals(expected = root, actual = 1)
        assertEquals(expected = nodeNull, actual = null)
        assertEquals(expected = node4, actual = 4)
        assertEquals(expected = node2, actual = 2)
        assertEquals(expected = node3, actual = 3)
        assertEquals(expected = nodeNull1, actual = null)
    }

    @Test
    fun `test find ordinary`() {
        val num = mutableListOf(2, 3, 1, 4, 5, 10)

        assertEquals(expected = treeStruct.find(2), actual = null)

        for (i in num) {
            treeStruct.insert(i)
        }

        assertEquals(expected = treeStruct.find(2), actual = 2)
    }

    @Test
    fun `test find null`() {
        val num = mutableListOf(1)
        treeStruct.insert(num[0])

        assertEquals(treeStruct.find(2), null)

    }

    @Test
    fun `test find root`() {
        val num = mutableListOf(1)
        treeStruct.insert(num[0])

        assertEquals(treeStruct.find(1), 1)
    }

    @Test
    fun `test insert and delete root`() {
        val num = mutableListOf(1, 2)
        for (i in num) {
            treeStruct.insert(i)
        }

        treeStruct.delete(1)

        val additionalNum = mutableListOf(1, 2, 11)
        for (i in additionalNum) {
            treeStruct.insert(i)
        }

        val root = treeW.getPrivateNode(treeStruct)?.value
        val node1 = treeW.getPrivateNode(treeStruct)?.left?.value
        val node11 = treeW.getPrivateNode(treeStruct)?.right?.value


        assertEquals(expected = root, actual = 2)
        assertEquals(expected = node1, actual = 1)
        assertEquals(expected = node11, actual = 11)
    }

    @Test
    fun `test delete nonexistent value right`() {
        val num = mutableListOf(5, 6)
        for (value in num) {
            treeStruct.insert(value)
        }
        treeStruct.delete(6)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.left?.value
        val nodeNullRight = treeW.getPrivateNode(treeStruct)?.right?.value

        assertEquals(expected = null, actual = nodeNullLeft)
        assertEquals(expected = null, actual = nodeNullRight)
        assertEquals(expected = 5, actual = root)
    }

    @Test
    fun `test delete nonexistent value left`() {
        val num = mutableListOf(6, 5)
        for (value in num) {
            treeStruct.insert(value)
        }
        treeStruct.delete(5)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.left?.value
        val nodeNullRight = treeW.getPrivateNode(treeStruct)?.right?.value

        assertEquals(expected = null, actual = nodeNullLeft)
        assertEquals(expected = null, actual = nodeNullRight)
        assertEquals(expected = 6, actual = root)
    }

    @Test
    fun `test delete no child root`() {
        val num = mutableListOf(3)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeStruct.delete(3)

        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.left?.value
        val nodeNullRight = treeW.getPrivateNode(treeStruct)?.right?.value
        val root = treeW.getPrivateNode(treeStruct)?.value

        assertEquals(expected = null, actual = nodeNullLeft)
        assertEquals(expected = null, actual = nodeNullRight)
        assertEquals(expected = null, actual = root)
    }

    @Test
    fun `test delete no child right`() {
        val num = mutableListOf(3, 10, 15)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeStruct.delete(15)

        val node10 = treeW.getPrivateNode(treeStruct)?.right?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.right?.left?.value
        val nodeNullRight = treeW.getPrivateNode(treeStruct)?.right?.right?.value
        val root = treeW.getPrivateNode(treeStruct)?.value


        assertEquals(expected = 10, actual = node10)
        assertEquals(expected = null, actual = nodeNullLeft)
        assertEquals(expected = null, actual = nodeNullRight)
        assertEquals(expected = 3, actual = root)
    }

    @Test
    fun `test delete no child left`() {
        val num = mutableListOf(15, 10, 3)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeStruct.delete(3)

        val node10 = treeW.getPrivateNode(treeStruct)?.left?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.left?.left?.value
        val nodeNullRight = treeW.getPrivateNode(treeStruct)?.left?.right?.value
        val root = treeW.getPrivateNode(treeStruct)?.value


        assertEquals(expected = 10, actual = node10)
        assertEquals(expected = null, actual = nodeNullLeft)
        assertEquals(expected = null, actual = nodeNullRight)
        assertEquals(expected = 15, actual = root)
    }

    @Test
    fun `test delete one child left`() {
        val num = mutableListOf(3, 2, 1, 5)
        for (value in num) {
            treeStruct.insert(value)
        }
        treeStruct.delete(2)

        val node1 = treeW.getPrivateNode(treeStruct)?.left?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.left?.left?.value
        val nodeNullRight = treeW.getPrivateNode(treeStruct)?.left?.right?.value
        val root = treeW.getPrivateNode(treeStruct)?.value

        assertEquals(expected = 1, actual = node1)
        assertEquals(expected = null, actual = nodeNullLeft)
        assertEquals(expected = null, actual = nodeNullRight)
        assertEquals(expected = 3, actual = root)
    }

    @Test
    fun `test delete one child right`() {
        val num = mutableListOf(3, 1, 5, 6)
        for (value in num) {
            treeStruct.insert(value)
        }
        treeStruct.delete(5)

        val node6 = treeW.getPrivateNode(treeStruct)?.right?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.right?.left?.value
        val nodeNullRight = treeW.getPrivateNode(treeStruct)?.right?.right?.value
        val root = treeW.getPrivateNode(treeStruct)?.value

        assertEquals(expected = 6, actual = node6)
        assertEquals(expected = null, actual = nodeNullLeft)
        assertEquals(expected = null, actual = nodeNullRight)
        assertEquals(expected = 3, actual = root)
    }

    @Test
    fun `test delete one child root`() {
        val num = mutableListOf(3, 6)
        for (value in num) {
            treeStruct.insert(value)
        }
        treeStruct.delete(3)

        val node6 = treeW.getPrivateNode(treeStruct)?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.left?.value
        val nodeNullRight = treeW.getPrivateNode(treeStruct)?.right?.value

        assertEquals(expected = 6, actual = node6)
        assertEquals(expected = null, actual = nodeNullLeft)
        assertEquals(expected = null, actual = nodeNullRight)
    }

    @Test
    fun `test delete one child with family`() {
        val num = mutableListOf(10, 7, 13, 6, 3, 1, 5, 2, 4, 15)
        for (value in num) {
            treeStruct.insert(value)
        }
        treeStruct.delete(7)
        val node6 = treeW.getPrivateNode(treeStruct)?.left?.value
        val node3 = treeW.getPrivateNode(treeStruct)?.left?.left?.value
        val nodeNullRight = treeW.getPrivateNode(treeStruct)?.left?.right?.value
        val node1 = treeW.getPrivateNode(treeStruct)?.left?.left?.left?.value
        val node2 = treeW.getPrivateNode(treeStruct)?.left?.left?.left?.right?.value
        val node5 = treeW.getPrivateNode(treeStruct)?.left?.left?.right?.value
        val node4 = treeW.getPrivateNode(treeStruct)?.left?.left?.right?.left?.value
        val root = treeW.getPrivateNode(treeStruct)?.value

        assertEquals(expected = 6, actual = node6)
        assertEquals(expected = 3, actual = node3)
        assertEquals(expected = null, actual = nodeNullRight)
        assertEquals(expected = 1, actual = node1)
        assertEquals(expected = 2, actual = node2)
        assertEquals(expected = 5, actual = node5)
        assertEquals(expected = 4, actual = node4)
        assertEquals(expected = 10, actual = root)
    }

    @Test
    fun `test delete two child only three element`() {
        val num = mutableListOf(2, 1, 3)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeStruct.delete(2)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val node1 = treeW.getPrivateNode(treeStruct)?.left?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.left?.left?.value
        val nodeNullRight1 = treeW.getPrivateNode(treeStruct)?.left?.right?.value
        val nodeNullRightRoot = treeW.getPrivateNode(treeStruct)?.right?.value

        assertEquals(expected = root, actual = 3)
        assertEquals(expected = node1, actual = 1)
        assertEquals(expected = nodeNullLeft, actual = null)
        assertEquals(expected = nodeNullRight1, actual = null)
        assertEquals(expected = nodeNullRightRoot, actual = null)
    }

    @Test
    fun `test delete two child without family`() {
        val num = mutableListOf(10, 7, 5, 4, 6)
        for (i in num) {
            treeStruct.insert(i)
        }
        treeStruct.delete(7)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val node5 = treeW.getPrivateNode(treeStruct)?.left?.value
        val node4 = treeW.getPrivateNode(treeStruct)?.left?.left?.value
        val node6 = treeW.getPrivateNode(treeStruct)?.left?.right?.value
        val nodeNullLeft4 = treeW.getPrivateNode(treeStruct)?.left?.left?.left?.value
        val nodeNullRight4 = treeW.getPrivateNode(treeStruct)?.left?.left?.right?.value
        val nodeNullLeft6 = treeW.getPrivateNode(treeStruct)?.left?.right?.left?.value
        val nodeNullRight6 = treeW.getPrivateNode(treeStruct)?.left?.right?.left?.value

        assertEquals(expected = root, actual = 10)
        assertEquals(expected = node5, actual = 5)
        assertEquals(expected = node4, actual = 4)
        assertEquals(expected = node6, actual = 6)
        assertEquals(expected = nodeNullLeft4, actual = null)
        assertEquals(expected = nodeNullRight4, actual = null)
        assertEquals(expected = nodeNullLeft6, actual = null)
        assertEquals(expected = nodeNullRight6, actual = null)
    }

    @Test
    fun `test two child double delete and delete root`() {
        val num = mutableListOf(6, 8, 10, 7)
        for (i in num) {
            treeStruct.insert(i)
        }

        treeStruct.delete(6)
        treeStruct.delete(7)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.left?.value
        val node10 = treeW.getPrivateNode(treeStruct)?.right?.value
        val nodeNullRight10 = treeW.getPrivateNode(treeStruct)?.right?.right?.value
        val nodeNullLeft10 = treeW.getPrivateNode(treeStruct)?.right?.left?.value

        assertEquals(expected = root, actual = 8)
        assertEquals(expected = node10, actual = 10)
        assertEquals(expected = nodeNullLeft10, actual = null)
        assertEquals(expected = nodeNullRight10, actual = null)
        assertEquals(expected = nodeNullLeft, actual = null)
    }

    @Test
    fun `test two child delete min element in right tree`() {
        val num = mutableListOf(6, 8, 10, 7, 12, 9)
        for (i in num) {
            treeStruct.insert(i)
        }

        treeStruct.delete(8)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val nodeNullLeft = treeW.getPrivateNode(treeStruct)?.left?.value
        val node9 = treeW.getPrivateNode(treeStruct)?.right?.value
        val node7 = treeW.getPrivateNode(treeStruct)?.right?.left?.value
        val node10 = treeW.getPrivateNode(treeStruct)?.right?.right?.value
        val node12 = treeW.getPrivateNode(treeStruct)?.right?.right?.right?.value
        val nodeNull = treeW.getPrivateNode(treeStruct)?.right?.right?.left?.value

        assertEquals(expected = root, actual = 6)
        assertEquals(expected = node9, actual = 9)
        assertEquals(expected = nodeNull, actual = null)
        assertEquals(expected = nodeNullLeft, actual = null)
        assertEquals(expected = node7, actual = 7)
        assertEquals(expected = node10, actual = 10)
        assertEquals(expected = node12, actual = 12)
    }

    @Test
    fun `test two child delete min element in right tree for root`() {
        val num = mutableListOf(8, 10, 7, 12, 9)
        for (i in num) {
            treeStruct.insert(i)
        }

        treeStruct.delete(8)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val node7 = treeW.getPrivateNode(treeStruct)?.left?.value
        val node10 = treeW.getPrivateNode(treeStruct)?.right?.value
        val node12 = treeW.getPrivateNode(treeStruct)?.right?.right?.value
        val nodeNull = treeW.getPrivateNode(treeStruct)?.right?.left?.value

        assertEquals(expected = root, actual = 9)
        assertEquals(expected = nodeNull, actual = null)
        assertEquals(expected = node7, actual = 7)
        assertEquals(expected = node10, actual = 10)
        assertEquals(expected = node12, actual = 12)
    }

    @Test
    fun `test two child delete min element in right tree for rightmost element`() {
        val num = mutableListOf(8, 10, 7, 12, 13, 14)
        for (i in num) {
            treeStruct.insert(i)
        }

        treeStruct.delete(8)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val node7 = treeW.getPrivateNode(treeStruct)?.left?.value
        val node12 = treeW.getPrivateNode(treeStruct)?.right?.value
        val node13 = treeW.getPrivateNode(treeStruct)?.right?.right?.value
        val node14 = treeW.getPrivateNode(treeStruct)?.right?.right?.right?.value
        val nodeNull = treeW.getPrivateNode(treeStruct)?.right?.left?.value

        assertEquals(expected = root, actual = 10)
        assertEquals(expected = nodeNull, actual = null)
        assertEquals(expected = node7, actual = 7)
        assertEquals(expected = node13, actual = 13)
        assertEquals(expected = node14, actual = 14)
        assertEquals(expected = node12, actual = 12)
    }

    @Test
    fun `test two child delete min element in right tree but in Tree`() {
        val num = mutableListOf(8, 12, 15, 13, 10, 11, 9)
        for (i in num) {
            treeStruct.insert(i)
        }

        treeStruct.delete(12)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val node13 = treeW.getPrivateNode(treeStruct)?.right?.value
        val node15 = treeW.getPrivateNode(treeStruct)?.right?.right?.value
        val node10 = treeW.getPrivateNode(treeStruct)?.right?.left?.value
        val node11 = treeW.getPrivateNode(treeStruct)?.right?.left?.right?.value
        val node9 = treeW.getPrivateNode(treeStruct)?.right?.left?.left?.value
        val nodeNull = treeW.getPrivateNode(treeStruct)?.right?.right?.left?.value

        assertEquals(expected = root, actual = 8)
        assertEquals(expected = node10, actual = 10)
        assertEquals(expected = node11, actual = 11)
        assertEquals(expected = node13, actual = 13)
        assertEquals(expected = node9, actual = 9)
        assertEquals(expected = node15, actual = 15)
        assertEquals(expected = nodeNull, actual = null)
    }

    @Test
    fun `test two child delete min element in right tree for leftmost element`() {
        val num = mutableListOf(8, 10, 7, 6)
        for (i in num) {
            treeStruct.insert(i)
        }

        treeStruct.delete(8)

        val root = treeW.getPrivateNode(treeStruct)?.value
        val node7 = treeW.getPrivateNode(treeStruct)?.left?.value
        val node6 = treeW.getPrivateNode(treeStruct)?.left?.left?.value
        val nodeNull = treeW.getPrivateNode(treeStruct)?.right?.value

        assertEquals(expected = root, actual = 10)
        assertEquals(expected = nodeNull, actual = null)
        assertEquals(expected = node7, actual = 7)
        assertEquals(expected = node6, actual = 6)
    }

    @Test
    fun `test analyzer`() {
        val num = mutableListOf(6, 8, 10, 7, 12, 9)
        for (i in num) {
            treeStruct.insert(i)
        }

        treeStruct.delete(8)

        val root = treeW.getPrivateNode(treeStruct)
        root?.let { analyzer.checkTree(root) } ?: Exception("CHzh")
    }

    @Test
    fun `test delete a node that is not in tree`() {
        Assertions.assertThrows(VauleNotFound::class.java) {
            for (i in (0..10000).shuffled()) {
                treeStruct.insert(i)
            }
            treeStruct.delete(100001)
        }
    }

    @Test
    fun `test add and delete 10000 arg`() {
        for (i in (0..10000).shuffled()) {
            treeStruct.insert(i)
        }
        for (i in (0..10000).shuffled()) {
            treeStruct.delete(i)
        }

        val root = treeW.getPrivateNode(treeStruct)?.value
        assertEquals(expected = root, actual = null)
    }

    @Test
    fun `test delete a node that is not in tree with message`() {
        Assertions.assertThrows(VauleNotFound(". Repeat again.")::class.java) {
            for (i in (0..10000).shuffled()) {
                treeStruct.insert(i)
            }
            treeStruct.delete(100001)
        }
    }
}
