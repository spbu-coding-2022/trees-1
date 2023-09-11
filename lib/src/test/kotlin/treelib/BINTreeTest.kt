package treelib

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import treelib.binTree.*
import treelib.commonObjects.Container
import utils.TreeStructWrapper
import utils.TreeWrapper
import kotlin.test.assertEquals

class BINTreeTest {
    private val tree = BINTree<Int, Int>()
    private val treeW =
        TreeWrapper<Int, Int, BINNode<Container<Int, Int?>>, BINVertex<Container<Int, Int?>>, BINStateContainer<Container<Int, Int?>>, BINStruct<Container<Int, Int?>>, BINTree<Int, Int>>()
    private val treeSW =
        TreeStructWrapper<Container<Int, Int?>, BINNode<Container<Int, Int?>>, BINVertex<Container<Int, Int?>>, BINStateContainer<Container<Int, Int?>>, BINStruct<Container<Int, Int?>>>()


    // line - 0.6, branch - 0.5, methods = 0.9
    @ParameterizedTest
    @ValueSource(ints = [1, 2, 0, 6, 4])
    fun `test putItem`(str: Int) {
        tree.putItem((Pair(str, 1)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["1 2 3 4", "5 6 7 8 9"])
    fun `test putItems`(str: String) {
        val numbers = str.split(" ").map { it.toInt() }.toMutableList()
        val num = mutableListOf<Pair<Int, Int>>()
        for (i in numbers) {
            num.add(Pair(i, 1))
        }
        tree.putItem(num)
        assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key, actual = numbers[0])
        assertEquals(
            expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.right?.value?.key,
            actual = numbers[1]
        )
    }

    @Test
    fun `test getItem`() {
        val num = mutableListOf(Pair(1, 1), Pair(2, 1), Pair(5, 1), Pair(4, 1), Pair(3, 5))
        tree.putItem(num)
        val temp = tree.getItem(3)

        assertEquals(
            expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.right?.right?.left?.left?.value?.value,
            actual = temp
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["5 2 3", "5 6 7", "5 6 13", "5 6 1", "192 5 6"])
    fun `test putItems and delete`(str: String) {
        val numbers = str.split(" ").map { it.toInt() }.toMutableList()
        val num = mutableListOf<Pair<Int, Int>>()
        for (i in numbers) {
            num.add(Pair(i, 1))
        }
        tree.putItem(num)
        tree.deleteItem(numbers[2])

        assertEquals(expected = tree.getItem(numbers[2]), actual = null)
        assertEquals(expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key, actual = numbers[0])
    }

    @ParameterizedTest
    @ValueSource(strings = ["5 2 3 9", "5 6 7 1", "5 6 13 4", "5 6 1", "192 5 6 222"])
    fun `test putItems and delete root`(str: String) {
        val numbers = str.split(" ").map { it.toInt() }.toMutableList()
        val num = mutableListOf<Pair<Int, Int>>()
        for (i in numbers) {
            num.add(Pair(i, 1))
        }
        tree.putItem(num)
        val root = numbers[0]
        numbers.sort()
        val index = numbers.indexOf(root)
        tree.deleteItem(root)

        assertEquals(
            expected = treeSW.getPrivateNode(treeW.getPrivateNode(tree))?.value?.key,
            actual = numbers[index + 1]
        )
    }

    @Test
    fun `test get set methods`() {
        for (i in 1..3) {
            tree.putItem(Pair(i, i))
        }

        assertEquals(expected = tree[1], actual = 1)
        assertEquals(expected = tree[2], actual = 2)
        assertEquals(expected = tree[3], actual = 3)

    }
}