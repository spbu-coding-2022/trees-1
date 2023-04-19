package treelib

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import treelib.rbTree.*
import treelib.singleObjects.Container
import treelib.singleObjects.Markers
import utils.TreeStructWrapper
import utils.TreeWrapper
import kotlin.test.assertEquals

class RBTreeTest {
    private val tree = RBTree<Int, Int>()
    private val treeW =
        TreeWrapper<Int, Int, RBNode<Container<Int, Int?>>, RBVertex<Container<Int, Int?>>, RBStateContainer<Container<Int, Int?>>, RBStruct<Container<Int, Int?>>, RBTree<Int, Int>>()
    private val treeSW =
        TreeStructWrapper<Container<Int, Int?>, RBNode<Container<Int, Int?>>, RBVertex<Container<Int, Int?>>, RBStateContainer<Container<Int, Int?>>, RBStruct<Container<Int, Int?>>>()


    @ParameterizedTest
    @ValueSource(strings = ["1 2 3 4 6 5", "5 3 8 6 9 11 13", "10 11 15 12 17 18"])
    fun `test change color delete root`(str: String) {
        val numbers = str.split(" ").map { it.toInt() }.toMutableList()
        val num = mutableListOf<Pair<Int, Int>>()
        for (i in numbers) {
            num.add(Pair(i, i))
        }

        tree.putItems(num)

        val rootR = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.right?.color
        assertEquals(expected = rootR, actual = Markers.RED)
        val root = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key
        if (root != null) {
            treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.let { tree.deleteItem(it.key) }
        }
        assertEquals(expected = rootR, actual = Markers.RED)
    }

    @ParameterizedTest
    @ValueSource(strings = ["1 2 5", "1 11 13", "10 11 18"])
    fun `test check color`(str: String) {
        val numbers = str.split(" ").map { it.toInt() }.toMutableList()
        val num = mutableListOf<Pair<Int, Int>>()
        for (i in numbers) {
            num.add(Pair(i, i))
        }

        tree.putItems(num)

        assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.right?.color, actual = Markers.RED)
        assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.left?.color, actual = Markers.RED)
        assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.color, actual = Markers.BLACK)
    }
}