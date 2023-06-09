package treelib

import org.junit.jupiter.api.*
import treelib.rbTree.RBNode
import treelib.rbTree.RBStateContainer
import treelib.rbTree.RBStruct
import treelib.rbTree.RBVertex
import treelib.rbTree.Markers
import utils.RBAnalyzer
import utils.TreeStructWrapper
import utils.fuzzers.RBStructFuzzer
import kotlin.test.assertEquals

@DisplayName("Test: Red-Black Tree Struct")
class RBStructTest {
    private val treeW = TreeStructWrapper<Int, RBNode<Int>, RBVertex<Int>, RBStateContainer<Int>, RBStruct<Int>>()
    private var treeStruct = RBStruct<Int>()

    private fun testAssert(msg: String): Nothing = fail(msg)

    private val analyzer = RBAnalyzer<Int>(::testAssert)

    @BeforeEach
    fun reInitClassUnderTest() {
        treeStruct = RBStruct()
    }

    @Test
    fun `base test on creation root`() {
        treeStruct.insert(6)
        val root = treeW.getPrivateNode(treeStruct)
        assertAll(
                { assertEquals(root?.value, 6) },
                { assertEquals(root?.color, Markers.BLACK) },
                { analyzer.checkTree(root!!) }
        )
    }

    @Test
    fun `base test on creation root with left`() {
        treeStruct.insert(6)
        treeStruct.insert(3)
        val root = treeW.getPrivateNode(treeStruct)
        assertAll(
                { assertEquals(root?.left?.value, 3) },
                { assertEquals(root?.left?.color, Markers.RED) },
                { analyzer.checkTree(root!!) }
        )
    }

    @Test
    fun `base test on creation root with right`() {
        treeStruct.insert(6)
        treeStruct.insert(8)
        val root = treeW.getPrivateNode(treeStruct)
        assertAll(
                { assertEquals(root?.right?.value, 8) },
                { assertEquals(root?.right?.color, Markers.RED) },
                { analyzer.checkTree(root!!) }
        )
    }

    @Test
    fun `base test on creation children`() {
        treeStruct.insert(6)
        treeStruct.insert(8)
        treeStruct.insert(3)
        val root = treeW.getPrivateNode(treeStruct)
        assertAll(
                { assertEquals(root?.right?.value, 8) },
                { assertEquals(root?.left?.value, 3) },
                { assertEquals(root?.right?.color, Markers.RED) },
                { assertEquals(root?.left?.color, Markers.RED) },
                { analyzer.checkTree(root!!) }
        )
    }

    @Test
    fun `base test delete root (left & right children)`() {
        treeStruct.insert(6)
        treeStruct.insert(8)
        treeStruct.insert(3)
        treeStruct.delete(6)
        val root = treeW.getPrivateNode(treeStruct)
        assertAll(
                { assertEquals(root?.value, 8) },
                { assertEquals(root?.color, Markers.BLACK) },
                { analyzer.checkTree(root!!) }
        )
    }

    @Test
    fun `base test delete root (right child)`() {
        treeStruct.insert(6)
        treeStruct.insert(8)
        treeStruct.delete(6)
        val root = treeW.getPrivateNode(treeStruct)
        assertAll(
                { assertEquals(8, root?.value) },
                { assertEquals(Markers.BLACK, root?.color) },
                { analyzer.checkTree(root!!) }
        )
    }

    @Test
    fun `base test delete root (left child)`() {
        treeStruct.insert(6)
        treeStruct.insert(3)
        treeStruct.delete(6)
        val root = treeW.getPrivateNode(treeStruct)
        assertAll(
                { assertEquals(3, root?.value) },
                { assertEquals(Markers.BLACK, root?.color) },
                { analyzer.checkTree(root!!) }
        )
    }

    @Test
    fun `fuzzer test`() {
        val fuzzer = RBStructFuzzer(
                arrayOf(
                        1,
                        2,
                        3,
                        4,
                        5,
                        6,
                        7,
                        8,
                        9,
                        20,
                        100,
                        123,
                        234,
                        556,
                        345677,
                        88765,
                        43,
                        364,
                        23456,
                        2754
                ), ::testAssert
        )
        fuzzer.saveNextTestSets("TEST_TEST")

        assertAll(
                {
                    fuzzer.fuzzInvariantInsert(15, 10)
                }
        )
    }
}
