
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import treeLib.RBtree.RBBalancer
import treeLib.RBtree.RBNode
import treeLib.RBtree.RBStateContainer
import treeLib.Single_Objects.Markers
class RBBalancerTest {

    val testModel = TestModelRBT()
    fun <T: Comparable<T>> countBlackNodes(node: RBNode<T>) = testModel.countBlackNodes(node)

    @DisplayName("Tests to check the operation of the balancer after removal")
    @Nested
    inner class RemovalTests {
        @Test
        fun `init test`() {
            val firstBalancer = RBBalancer<Int>(null)
            assertEquals(15, firstBalancer.balance(RBStateContainer(RBNode(15, null, null, null, Markers.BLACK))).value)
            val secondBalancer = RBBalancer<String>(null)
            assertEquals("Test", secondBalancer.balance(RBStateContainer(RBNode("Test", null, null, null, Markers.BLACK))).value)
        }

        @Test
        fun `remove red leaf`() {
            val nodes = testModel.getFirstTree()
            val root = nodes[30]
            val balancer = RBBalancer(root)
            nodes[18]!!.right = null
            assertEquals(root, balancer.balance(RBStateContainer(nodes[18]!!)))
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[4]!!)) },
                { assertEquals(3, countBlackNodes(nodes[5]!!)) },
                { assertEquals(3, countBlackNodes(nodes[18]!!)) },
                { assertEquals(3, countBlackNodes(nodes[19]!!)) },
            )
            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(19, root?.left?.right?.value) },
                { assertEquals(24, root?.left?.right?.right?.value) },
                { assertEquals(18, root?.left?.right?.left?.value) },
                { assertEquals(16, root?.left?.right?.left?.left?.value) },
                { assertEquals(null, root?.left?.right?.left?.right?.value) },
            )

        }

        @Test
        fun `remove black leaf (black parent)`() {
            val nodes = testModel.getFirstTree()
            val root = nodes[30]
            val balancer = RBBalancer(root)
            nodes[27]?.right = null
            assertEquals(root, balancer.balance(RBStateContainer(nodes[27]!!)))
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[12]!!)) },
                { assertEquals(3, countBlackNodes(nodes[13]!!)) },
                { assertEquals(3, countBlackNodes(nodes[27]!!)) },
                { assertEquals(2, countBlackNodes(nodes[22]!!)) },
                { assertEquals(3, countBlackNodes(nodes[11]!!)) },
            )

            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(50, root?.right?.value) },
                { assertEquals(63, root?.right?.right?.value) },
                { assertEquals(71, root?.right?.right?.right?.value) },
                { assertEquals(52, root?.right?.right?.left?.value) },
                { assertEquals(nodes[13]?.value, nodes[27]?.left?.value) },
                { assertEquals(null, nodes[27]?.right) },
            )
        }

        @Test
        fun `remove black leaf(red parent)`() {
            val nodes = testModel.getFirstTree()
            val root = nodes[30]
            val balancer = RBBalancer(root)
            nodes[17]?.left = null
            assertEquals(root, balancer.balance(RBStateContainer(nodes[17]!!)))
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[0]!!)) },
                { assertEquals(3, countBlackNodes(nodes[1]!!)) },
                { assertEquals(3, countBlackNodes(nodes[3]!!)) },
                { assertEquals(3, countBlackNodes(nodes[17]!!)) },
            )

            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(50, root?.right?.value) },
                { assertEquals(15, root?.left?.value) },
                { assertEquals(19, root?.left?.right?.value) },
                { assertEquals(10, root?.left?.left?.value) },
                { assertEquals(12, root?.left?.left?.right?.value) },
                { assertEquals(14, nodes[17]?.right?.value) },
                { assertEquals(null, nodes[17]?.left)}
            )
        }

        @Test
        fun `remove root (Case 1)`() {
            val nodes = testModel.getFirstTree()
            var root = nodes[30]
            val balancer = RBBalancer(root)
            root = testModel.deleteNode(nodes[30]!!, nodes[8]!!)

            root = balancer.balance(RBStateContainer(nodes[20]!!))
            assertAll(
                "Assertions of root",
                { assertEquals(nodes[8]?.value, root.value) },
                { assertEquals(Markers.BLACK, root.color) },
                { assertEquals(null, root.parent) },
                { assertEquals(nodes[28]?.value, root.left?.value) },
                { assertEquals( nodes[29]?.value, root.right?.value ) }
            )

            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[0]!!)) },
                { assertEquals(3, countBlackNodes(nodes[9]!!)) },
                { assertEquals(1, countBlackNodes(nodes[29]!!)) },
                { assertEquals(3, countBlackNodes(nodes[23]!!)) },
                { assertEquals(3, countBlackNodes(nodes[20]!!)) }
            )

            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(19, root.left?.right?.value) },
                { assertEquals(40, root.right?.left?.value) },
                { assertEquals(42, root.right?.left?.right?.value) },
                { assertEquals(37, root.right?.left?.left?.value) },
                { assertEquals(38, nodes[20]?.right?.value) },
                { assertEquals(null, nodes[20]?.left)}
            )
        }

        @Test
        fun `remove root (Case 2)`() {
            var root = RBNode(100, RBNode(29, null, null, null, Markers.RED), null, null, Markers.BLACK)
            root.left?.parent = root
            val balance = RBBalancer(root)
            root = testModel.deleteNode(root, root.left!!)

            root = balance.balance(RBStateContainer(root))

            assertAll(
                "Assertions of root",
                { assertEquals(Markers.BLACK, root.color) },
                { assertEquals(29, root.value) },
                { assertEquals(null, root.parent) },
                { assertEquals(null, root.left)},
                { assertEquals(null, root.right)}
            )
        }

        @Test
        fun `remove black leaf(red parent, black brother with two red child)`() {
            val nodes = testModel.getFirstTree()
            val root = nodes[30]!!
            nodes[13]?.value = 68
            val rightBrotherSon = RBNode(70, null, null, nodes[13], Markers.RED)
            val leftBrotherSon= RBNode(65, null, null, nodes[13], Markers.RED)
            nodes[13]?.right = rightBrotherSon
            nodes[13]?.left = leftBrotherSon
            val balancer = RBBalancer(nodes[30])
            nodes[22]?.left = null

            assertEquals(root, balancer.balance(RBStateContainer(nodes[22]!!)))
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(rightBrotherSon), "1") },
                { assertEquals(3, countBlackNodes(leftBrotherSon), "2") },
                { assertEquals(3, countBlackNodes(nodes[22]!!)) },
                { assertEquals(3, countBlackNodes(nodes[13]!!)) },
                { assertEquals(2, countBlackNodes(root.right?.right!!)) },
                { assertEquals(2, countBlackNodes(nodes[13]?.parent!!)) }
            )

            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(50, root.right?.value) },
                { assertEquals(71, root.right?.right?.value) },
                { assertEquals(68, root.right?.right?.left?.value) },
                { assertEquals(70, root.right?.right?.left?.right?.value) },
                { assertEquals(63, root.right?.right?.left?.left?.value) },
                { assertEquals(65, nodes[22]?.right?.value)},
                { assertEquals(null, nodes[22]?.left)},
            )


        }

        @Test
        fun `multiple deletions`() {
            val nodes = testModel.getFirstTree()
            val root = nodes[30]
            val balancer = RBBalancer(root)

            /** first delete **/
            testModel.deleteNode(nodes[24]!!, nodes[2]!!)

            assertEquals(root!!.value, balancer.balance(RBStateContainer(nodes[17]!!)).value)
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[3]!!)) },
                { assertEquals(3, countBlackNodes(nodes[17]!!)) },
                { assertEquals(2, countBlackNodes(nodes[26]!!)) },
                { assertEquals(2, countBlackNodes(nodes[24]!!)) }
            )
            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(12, nodes[17]?.value) },
                { assertEquals(14, nodes[3]?.value) },
                { assertEquals(null, nodes[17]?.left) }
            )

            /** second delete **/
            testModel.deleteNode(nodes[16]!!, nodes[1]!!)

            assertEquals(root, balancer.balance(RBStateContainer(nodes[1]!!)))
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[0]!!)) },
                { assertEquals(3, countBlackNodes(root.left?.left?.left!!)) },
                { assertEquals(2, countBlackNodes(nodes[24]!!), "2") },
                { assertEquals(1, countBlackNodes(nodes[28]!!)) },
                { assertEquals(1, countBlackNodes(nodes[30]!!)) },
                { assertEquals(3, countBlackNodes(nodes[17]!!), "1") }
            )

            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(15, root.left?.value) },
                { assertEquals(11, root.left?.left?.value) },
                { assertEquals(12, root.left?.left?.right?.value) },
                { assertEquals(7, root.left?.left?.left?.value) },
                { assertEquals(1, root.left?.left?.left?.left?.value) },
                { assertEquals(null, root.left?.left?.left?.right) },
            )

            /** third delete **/
            nodes[17]?.right = null
            assertEquals(root, balancer.balance(RBStateContainer(nodes[17]!!)))
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[0]!!), "1") },
                { assertEquals(3, countBlackNodes(nodes[1]!!), "2") },
                { assertEquals(3, countBlackNodes(nodes[17]!!), "3") },
                { assertEquals(2, countBlackNodes(nodes[2]!!)) },
                { assertEquals(1, countBlackNodes(nodes[28]!!)) },
            )

            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(15, root.left?.value) },
                { assertEquals(11, root.left?.left?.value) },
                { assertEquals(7, root.left?.left?.left?.value) },
                { assertEquals(12, nodes[17]?.value) },
                { assertEquals(null, nodes[17]?.right) },
                { assertEquals(null, nodes[17]?.left) },
            )

            /** fourth delete **/
            nodes[2]?.right = null

            assertEquals(root, balancer.balance(RBStateContainer(nodes[2]!!)))
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[0]!!)) },
                { assertEquals(2, countBlackNodes(nodes[1]!!)) },
                { assertEquals(3, countBlackNodes(nodes[2]!!)) },
                { assertEquals(1, countBlackNodes(nodes[28]!!)) },
                { assertEquals(1, countBlackNodes(nodes[30]!!)) },
                { assertEquals(2, countBlackNodes(nodes[25]!!)) }
            )

            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(15, root.left?.value) },
                { assertEquals(7, root.left?.left?.value) },
                { assertEquals(1, root.left?.left?.left?.value) },
                { assertEquals(11, root.left?.left?.right?.value) },
                { assertEquals(19, root.left?.right?.value) },
                { assertEquals(null, nodes[2]?.right) },
                { assertEquals(null, nodes[2]?.left) },
            )

            /** fourth delete **/
            nodes[2]?.right = null

            assertEquals(root, balancer.balance(RBStateContainer(nodes[2]!!)))
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[0]!!)) },
                { assertEquals(2, countBlackNodes(nodes[1]!!)) },
                { assertEquals(3, countBlackNodes(nodes[2]!!)) },
                { assertEquals(1, countBlackNodes(nodes[28]!!)) },
                { assertEquals(1, countBlackNodes(nodes[30]!!)) },
                { assertEquals(2, countBlackNodes(nodes[25]!!)) }
            )

            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(15, root.left?.value) },
                { assertEquals(7, root.left?.left?.value) },
                { assertEquals(1, root.left?.left?.left?.value) },
                { assertEquals(11, root.left?.left?.right?.value) },
                { assertEquals(19, root.left?.right?.value) },
                { assertEquals(null, nodes[2]?.right) },
                { assertEquals(null, nodes[2]?.left) },
            )

            /** fifth delete **/
            nodes[1]?.right = null

            assertEquals(root, balancer.balance(RBStateContainer(nodes[1]!!)))
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[0]!!)) },
                { assertEquals(3, countBlackNodes(nodes[1]!!)) },
                { assertEquals(2, countBlackNodes(nodes[28]!!)) },
                { assertEquals(2, countBlackNodes(nodes[25]!!)) },
                { assertEquals(3, countBlackNodes(nodes[18]!!)) },
                { assertEquals(3, countBlackNodes(nodes[19]!!)) }
            )

            assertAll(
                "Grouped Assertions of nodes values",
                { assertEquals(15, root.left?.value) },
                { assertEquals(7, root.left?.left?.value) },
                { assertEquals(1, root.left?.left?.left?.value) },
                { assertEquals(19, root.left?.right?.value) },
                { assertEquals(24, root.left?.right?.right?.value) },
                { assertEquals(null, root.left?.left?.right) },
            )
        }
    }

    @DisplayName("Tests to check the operation of the balancer after insertion")
    @Nested
    inner class InsertionTests {
        @Test
        fun `insertion root`() {
            val root = RBNode(18, null, null, null, Markers.RED)
            RBBalancer(root)
            assertAll(
                { assertEquals(Markers.BLACK, root.color) },
                { assertEquals(18, root.value) }
            )
        }

        @Test
        fun `insertion with black parent`() {
            var root = RBNode(18, null, null, null, Markers.BLACK)
            val balancer = RBBalancer(root)
            root.right = RBNode(39, null, null, root, Markers.RED)
            root = balancer.balance(RBStateContainer(root.right!!))
            assertAll(
                { assertEquals(18, root.value) },
                { assertEquals(Markers.BLACK, root.color) },
                { assertEquals(39, root.right?.value) },
                { assertEquals(null, root.left) },
                { assertEquals(null, root.right?.right) },
                { assertEquals(null, root.right?.left) }
            )
        }

        @Test
        fun `insertion with red uncle (Case 1)`() {
            val nodes = testModel.getSecondTree()
            var root = nodes[30]
            val balancer = RBBalancer(root)
            root = balancer.balance(RBStateContainer(nodes[0]!!))
            assertAll(
                "Assertions of root",
                { assertEquals(25, root.value) },
                { assertEquals(Markers.BLACK, root.color) }
            )
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(3, countBlackNodes(nodes[0]!!), "1") },
                { assertEquals(3, countBlackNodes(nodes[16]!!), "2") },
                { assertEquals(3, countBlackNodes(nodes[17]!!)) },
                { assertEquals(2, countBlackNodes(nodes[24]!!)) },
                { assertEquals(2, countBlackNodes(nodes[29]!!)) }
            )
            assertAll(
                "Grouped Assertions of values",
                { assertEquals(1, root.left?.left?.left?.left?.value) },
                { assertEquals(4, root.left?.left?.left?.value) },
                { assertEquals(12, root.left?.left?.right?.value) },
                { assertEquals(15, root.left?.value) },
                { assertEquals(null, root.left?.left?.left?.left?.left) },
                { assertEquals(null, root.left?.left?.right?.left) },
                { assertEquals(null, root.left?.left?.left?.right) },
            )
        }

        @Test
        fun `insertion with red uncle (Case 2)`() {
            val nodes = testModel.getThirdTree()
            var root = nodes[30]
            val balancer = RBBalancer(root)
            nodes[22]?.right = RBNode(65, null, null, nodes[22], Markers.RED)
            root = balancer.balance(RBStateContainer(nodes[22]!!.right!!))
            assertAll(
                "Assertions of root",
                { assertEquals(50, root.value) },
                { assertEquals(Markers.BLACK, root.color) }
            )
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(2, countBlackNodes(nodes[23]!!)) },
                { assertEquals(2, countBlackNodes(nodes[22]!!)) },
                { assertEquals(2, countBlackNodes(nodes[26]!!)) },
                { assertEquals(2, countBlackNodes(nodes[28]!!)) },
                { assertEquals(2, countBlackNodes(nodes[22]!!.right!!)) },
                { assertEquals(1, countBlackNodes(nodes[30]!!)) },
                { assertEquals(1, countBlackNodes(nodes[27]!!)) }
            )
            assertAll(
                "Grouped Assertions of values",
                { assertEquals(10, root.left?.left?.left?.value) },
                { assertEquals(19, root.left?.left?.right?.value) },
                { assertEquals(40, root.left?.right?.value) },
                { assertEquals(63, root.right?.left?.value) },
                { assertEquals(65, root.right?.left?.right?.value) },
                { assertEquals(90, root.right?.right?.value) },
                { assertEquals(71, root.right?.value) },
                { assertEquals(42, root.left?.right?.right?.value) },
                { assertEquals(null, root.right?.left?.left) },
            )

        }

        @Test
        fun `insertion with red uncle (Case 3)`() {
            val nodes = testModel.getFourthTree()
            var root = nodes[30]
            val balancer = RBBalancer(root)
            nodes[19]!!.left = RBNode(21, null, null, nodes[19], Markers.RED)
            root = balancer.balance(RBStateContainer(nodes[19]!!.left!!))

            assertAll(
                "Assertions of root",
                { assertEquals(19, root.value) },
                { assertEquals(Markers.BLACK, root.color) }
            )
            assertAll(
                "Grouped Assertions of black height",
                { assertEquals(2, countBlackNodes(nodes[24]!!)) },
                { assertEquals(1, countBlackNodes(nodes[28]!!)) },
                { assertEquals(2, countBlackNodes(nodes[18]!!)) },
                { assertEquals(2, countBlackNodes(nodes[19]!!)) },
                { assertEquals(2, countBlackNodes(nodes[19]!!.left!!)) },
                { assertEquals(2, countBlackNodes(nodes[27]!!)) },
                { assertEquals(2, countBlackNodes(nodes[26]!!)) }
            )
            assertAll(
                "Grouped Assertions of values",
                { assertEquals(10, root.left?.left?.value) },
                { assertEquals(15, root.left?.value) },
                { assertEquals(18, root.left?.right?.value) },
                { assertEquals(25, root.right?.value) },
                { assertEquals(50, root.right?.right?.value) },
                { assertEquals(24, root.right?.left?.value) },
                { assertEquals(71, root.right?.right?.right?.value) },
                { assertEquals(21, root.right?.left?.left?.value) },
                { assertEquals(null, root.right?.left?.right) },
                { assertEquals(null, root.left?.left?.right) },
                { assertEquals(null, root.left?.right?.left) },
            )
        }
    }

}
