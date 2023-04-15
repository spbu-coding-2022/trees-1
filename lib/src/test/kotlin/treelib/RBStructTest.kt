package treelib

import org.junit.jupiter.api.*
import treelib.rbTree.RBNode
import treelib.rbTree.RBStateContainer
import treelib.rbTree.RBStruct
import treelib.singleObjects.Markers
import kotlin.test.assertEquals

@DisplayName("Test: Red-Black Tree Struct")
class RBStructTest {
    val treeW = TreeStructWrapper<Int, RBNode<Int>, RBStateContainer<Int>, RBStruct<Int>>()
    var classUnderTest = RBStruct<Int>()

    @BeforeEach
    fun reInitClassUnderTest() {
        classUnderTest = RBStruct()
    }

    @Test
    fun `base test on creation root`() {
        classUnderTest.insert(6)
        val root = treeW.getPrivateNode(classUnderTest)
        assertAll(
            { assertEquals(root?.value, 6) },
            { assertEquals(root?.color, Markers.BLACK) },
        )
    }

    @Test
    fun `base test on creation root with left`() {
        classUnderTest.insert(6)
        classUnderTest.insert(3)
        val root = treeW.getPrivateNode(classUnderTest)
        assertAll(
            { assertEquals(root?.left?.value, 3) },
            { assertEquals(root?.left?.color, Markers.RED) },
        )
    }

    @Test
    fun `base test on creation root with right`() {
        classUnderTest.insert(6)
        classUnderTest.insert(8)
        val root = treeW.getPrivateNode(classUnderTest)
        assertAll(
            { assertEquals(root?.right?.value, 8) },
            { assertEquals(root?.right?.color, Markers.RED) },
        )
    }

    @Test
    fun `base test on creation children`() {
        classUnderTest.insert(6)
        classUnderTest.insert(8)
        classUnderTest.insert(3)
        val root = treeW.getPrivateNode(classUnderTest)
        assertAll(
            { assertEquals(root?.right?.value, 8) },
            { assertEquals(root?.left?.value, 3) },
            { assertEquals(root?.right?.color, Markers.RED) },
            { assertEquals(root?.left?.color, Markers.RED) },
        )
    }

    @Test
    fun `base test delete root (left & right children)`() {
        classUnderTest.insert(6)
        classUnderTest.insert(8)
        classUnderTest.insert(3)
        classUnderTest.delete(6)
        val root = treeW.getPrivateNode(classUnderTest)
        assertAll(
            { assertEquals(root?.value, 8) },
            { assertEquals(root?.color, Markers.BLACK) },
        )
    }

    @Test
    fun `base test delete root (right child)`() {
        classUnderTest.insert(6)
        classUnderTest.insert(8)
        classUnderTest.delete(6)
        val root = treeW.getPrivateNode(classUnderTest)
        assertAll(
            { assertEquals(8, root?.value) },
            { assertEquals(Markers.BLACK, root?.color) },
        )
    }

    @Test
    fun `base test delete root (left child)`() {
        classUnderTest.insert(6)
        classUnderTest.insert(3)
        classUnderTest.delete(6)
        val root = treeW.getPrivateNode(classUnderTest)
        assertAll(
            { assertEquals(3, root?.value) },
            { assertEquals(Markers.BLACK, root?.color) },
        )
    }

}