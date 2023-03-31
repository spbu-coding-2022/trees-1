
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import treeLib.AVLtree.AVLBalancer
import treeLib.AVLtree.AVLNode
import treeLib.AVLtree.AVLStateContainer

class AVLBalancerTest {

    val testModel = TestModelAVL()

    @DisplayName("Tests to check the operation of the balancer after removal")
    @Nested
    inner class RemovalTests {

        @Test
        fun `first remove (RightHeight-LeftHeight=2)`() {
            val balancer = AVLBalancer<Int>(null)
            var root = testModel.getTree(testModel.thirdTreeValues)
            root.right?.right?.left = null
            root = balancer.balance(AVLStateContainer(root.right?.right!!, root))
            assertAll(
                "Grouped Assertions of height",
                { assertEquals(3, root.left?.left?.height?.toInt()) },
                { assertEquals(5, root.height.toInt()) },
                { assertEquals(3, root.right?.right?.height?.toInt()) },
                { assertEquals(1, root.right?.right?.right?.height?.toInt()) },
                { assertEquals(3, root.right?.left?.height?.toInt()) }
            )

            assertAll(
                "Grouped Assertions of values",
                { assertEquals(104, root.value) },
                { assertEquals(74, root.left?.right?.value) },
                { assertEquals(163, root.right?.left?.value) },
                { assertEquals(370, root.right?.right?.right?.value) },
                { assertEquals(293, root.right?.right?.left?.right?.value) },
                { assertEquals(null, root.right?.right?.left?.left?.value) }
            )
        }

        @Test
        fun `second remove`() {
            val balancer = AVLBalancer<Int>(null)
            var root = testModel.getTree(testModel.thirdTreeValuesCase2)
            root.right?.right?.right = null
            root = balancer.balance(AVLStateContainer(root.right?.right!!, root))
            assertAll(
                "Grouped Assertions of height",
                { assertEquals(3, root.left?.left?.height?.toInt()) },
                { assertEquals(5, root.height.toInt()) },
                { assertEquals(2, root.right?.right?.height?.toInt()) },
                { assertEquals(1, root.right?.right?.right?.height?.toInt()) },
                { assertEquals(1, root.right?.right?.left?.height?.toInt()) },
                { assertEquals(3, root.right?.left?.height?.toInt()) }
            )

            assertAll(
                "Grouped Assertions of values",
                { assertEquals(104, root.value) },
                { assertEquals(74, root.left?.right?.value) },
                { assertEquals(163, root.right?.left?.value) },
                { assertEquals(351, root.right?.right?.right?.value) },
                { assertEquals(293, root.right?.right?.value) },
                { assertEquals(174, root.right?.left?.right?.left?.value) },
                { assertEquals(null, root.right?.right?.left?.left?.value) }
            )
        }

        @Test
        fun `third remove`() {
            val balancer = AVLBalancer<Int>(null)
            var root = testModel.getTree(testModel.thirdTreeValuesCase3)
            root.right?.left?.left = null
            root = balancer.balance(AVLStateContainer(root.right?.left!!, root))
            assertAll(
                "Grouped Assertions of height",
                { assertEquals(3, root.left?.left?.height?.toInt()) },
                { assertEquals(5, root.height.toInt()) },
                { assertEquals(2, root.right?.right?.height?.toInt(), "2") },
                { assertEquals(1, root.right?.left?.left?.height?.toInt()) },
                { assertEquals(2, root.right?.left?.height?.toInt()) }
            )

            assertAll(
                "Grouped Assertions of values",
                { assertEquals(104, root.value) },
                { assertEquals(74, root.left?.right?.value) },
                { assertEquals(174, root.right?.left?.value) },
                { assertEquals(180, root.right?.left?.right?.value) },
                { assertEquals(163, root.right?.left?.left?.value) },
                { assertEquals(293, root.right?.right?.value) },
                { assertEquals(null, root.right?.left?.left?.left?.value) },
                { assertEquals(null, root.right?.left?.right?.left?.value) }
            )
        }

        @Test
        fun `fourth remove (without the need for balancing)`() {
            val balancer = AVLBalancer<Int>(null)
            var root = testModel.getTree(testModel.thirdTreeValuesCase4)
            root.right?.right?.right = null
            root = balancer.balance(AVLStateContainer(root.right?.right!!, root))
            assertAll(
                "Grouped Assertions of height",
                { assertEquals(3, root.left?.left?.height?.toInt()) },
                { assertEquals(5, root.height.toInt()) },
                { assertEquals(2, root.right?.right?.height?.toInt()) },
                { assertEquals(1, root.right?.left?.right?.height?.toInt()) },
                { assertEquals(1, root.right?.left?.left?.height?.toInt()) },
                { assertEquals(2, root.right?.left?.height?.toInt()) }
            )

            assertAll(
                "Grouped Assertions of values",
                { assertEquals(104, root.value) },
                { assertEquals(74, root.left?.right?.value) },
                { assertEquals(174, root.right?.left?.value) },
                { assertEquals(180, root.right?.left?.right?.value) },
                { assertEquals(163, root.right?.left?.left?.value) },
                { assertEquals(293, root.right?.right?.value) },
                { assertEquals(285, root.right?.right?.left?.value) },
                { assertEquals(null, root.right?.right?.right?.value) }
            )
        }



    }

    @DisplayName("Tests to check the operation of the balancer after insertion")
    @Nested
    inner class InsertionTests {
        @Test
        fun `insert case 1(LeftHeight - RightHeight = 2, delta(leftSon)=1)`() {
            val balancer = AVLBalancer<Int>(null)
            var root = testModel.getTree(testModel.firstTreeValues)
            root = balancer.balance(AVLStateContainer(root.left!!, root))
            assertAll(
                { assertEquals(14, root.value, "Value of the root") },
                { assertEquals(1, root.left?.value, "Value of the left son of the root") },
                { assertEquals(23, root.right?.value, "Value of the right son of the root") }
            )
        }

        @Test
        fun `insert case 2(LeftHeight- RightHeight =-2, delta(rightSon)=-1`() {
            val balancer = AVLBalancer<Int>(null)
            var root = testModel.getTree(testModel.secondTreeValues)
            root.left?.left?.right?.right = AVLNode(53, AVLNode(49, null, null), null)
            root = balancer.balance(AVLStateContainer(root.left?.left?.right?.right!!, root))
            assertAll(
                { assertEquals(101, root.value) },
                { assertEquals(230, root.right?.value) },
                { assertEquals(47, root.left?.left?.value) },
                { assertEquals(44, root.left?.left?.left?.value) },
                { assertEquals(1, root.left?.left?.left?.left?.value) },
                { assertEquals(53, root.left?.left?.right?.value) },
                { assertEquals(49, root.left?.left?.right?.left?.value) }
            )
        }

        @Test
        fun `multiple insertions`() {
            val balancer = AVLBalancer<Int>(null)
            var root = testModel.getTree(testModel.secondTreeValues)
            /** First insert **/
            root.left?.left?.right?.left?.right = AVLNode(46, null, null)
            root = balancer.balance(AVLStateContainer(root.left?.left?.right?.left!!, root))
            assertAll(
                "Grouped Assertions of height",
                { assertEquals(5, root.height.toInt()) },
                { assertEquals(2, root.right?.left?.height?.toInt()) },
                { assertEquals(3, root.left?.left?.height?.toInt()) },
                { assertEquals(2, root.left?.left?.right?.height?.toInt()) },
                { assertEquals(1, root.left?.left?.right?.right?.height?.toInt())},
                { assertEquals(1, root.left?.left?.left?.height?.toInt())}
            )
            assertAll(
                "Grouped Assertions of values",
                { assertEquals(101, root.value) },
                { assertEquals(205, root.right?.left?.value) },
                { assertEquals(44, root.left?.left?.value) },
                { assertEquals(46, root.left?.left?.right?.value) },
                { assertEquals(47, root.left?.left?.right?.right?.value)},
                { assertEquals(null, root.left?.left?.right?.left?.left?.value)}
            )

            /** Second insert **/
            root.left?.left?.right?.right?.right = AVLNode(50, null, null)
            root = balancer.balance(AVLStateContainer(root.left?.left?.right?.right!!, root))
            assertAll(
                "Grouped Assertions of height",
                { assertEquals(1, root.left?.left?.right?.right?.height?.toInt()) },
                { assertEquals(1, root.left?.left?.left?.left?.height?.toInt()) },
                { assertEquals(2, root.right?.right?.height?.toInt()) },
                { assertEquals(2, root.left?.left?.right?.height?.toInt()) },
                { assertEquals(3, root.left?.left?.height?.toInt()) }
            )
            assertAll(
                "Grouped Assertions of values",
                { assertEquals(101, root.value) },
                { assertEquals(205, root.right?.left?.value) },
                { assertEquals(46, root.left?.left?.value) },
                { assertEquals(1, root.left?.left?.left?.left?.value) },
                { assertEquals(null, root.left?.left?.right?.left?.value) },
                { assertEquals(null, root.left?.left?.right?.right?.left?.value)}
            )

            /** Third insert **/
            root.left?.left?.right?.right?.left = AVLNode(49, null, null)
            root = balancer.balance(AVLStateContainer(root.left?.left?.right?.right!!, root))
            assertAll(
                "Grouped Assertions of height",
                { assertEquals(1, root.left?.left?.right?.right?.height?.toInt()) },
                { assertEquals(1, root.left?.left?.left?.left?.height?.toInt()) },
                { assertEquals(1, root.left?.left?.right?.left?.height?.toInt()) },
                { assertEquals(2, root.right?.right?.height?.toInt()) },
                { assertEquals(2, root.left?.left?.right?.height?.toInt()) },
                { assertEquals(3, root.left?.left?.height?.toInt()) }
            )
            assertAll(
                "Grouped Assertions of values",
                { assertEquals(101, root.value) },
                { assertEquals(205, root.right?.left?.value) },
                { assertEquals(46, root.left?.left?.value) },
                { assertEquals(49, root.left?.left?.right?.value)},
                { assertEquals(1, root.left?.left?.left?.left?.value) },
                { assertEquals(47, root.left?.left?.right?.left?.value) },
                { assertEquals(null, root.left?.left?.right?.right?.left?.value)},
                { assertEquals(null, root.left?.left?.right?.right?.right?.value)}
            )
        }
    }

}
