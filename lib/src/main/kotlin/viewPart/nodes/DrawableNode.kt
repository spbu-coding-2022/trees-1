package viewPart.nodes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

abstract class DrawableNode<Pack, NodeType : DrawableNode<Pack, NodeType>> {
    abstract val value: Pack
    abstract var leftChild: NodeType?
    abstract var rightChild: NodeType?
    abstract val xState: MutableState<Float>
    abstract val yState: MutableState<Float>

    /* md should be added (for deleting)????
    * abstract val xDragState: MutableState<Float>
    * abstract val yDragState: MutableState<Float>
    * */

    @Composable
    fun display(width: Float? = null, height: Float? = null) {
        // TODO: Выпилить рекурсивный дисплей
        nodeView(
            information = value.toString(),
            remember { mutableStateOf(0f) },
            remember { mutableStateOf(0f) },
        )

        leftChild?.let {
            edgeView(it)
            it.display()
        }

        rightChild?.let {
            edgeView(it)
            it.display()
        }
    }

    @Composable
    protected abstract fun nodeView(
        information: String,
        offsetX: MutableState<Float>,
        offsetY: MutableState<Float>,
    )

    @Composable
    protected abstract fun edgeView(child: NodeType)
}

/* Usage example (put it inside block):

 val a = BINDrawableNode(
     1,
     null,
     null,
     remember { mutableStateOf(0f) },
     remember { mutableStateOf(0f) }
 )

 val b = BINDrawableNode(
     2,
     null,
     null,
     remember { mutableStateOf(0f) },
     remember { mutableStateOf(0f) }
 )

 val c = BINDrawableNode(
     3,
     null,
     null,
     remember { mutableStateOf(0f) },
     remember { mutableStateOf(0f) }
 )

 val d = BINDrawableNode(
     4,
     null,
     null,
     remember { mutableStateOf(0f) },
     remember { mutableStateOf(0f) }
 )

 a.leftChild = b
 a.rightChild = c
 c.leftChild = d

 a.display()
* */
