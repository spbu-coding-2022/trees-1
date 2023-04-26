package viewPart.nodes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import treelib.rbTree.Markers

class RBDrawableNode<Pack>(
    override val value: Pack,
    override var leftChild: RBDrawableNode<Pack>? = null,
    override var rightChild: RBDrawableNode<Pack>? = null,
    val color: Markers,
    override val xState: MutableState<Float>,
    override val yState: MutableState<Float>,
) : DrawableNode<Pack, RBDrawableNode<Pack>>() {

    @Composable
    override fun nodeView(
        information: String,
        offsetX: MutableState<Float>,
        offsetY: MutableState<Float>
    ) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun edgeView(child: RBDrawableNode<Pack>) {
        TODO("Not yet implemented")
    }
}
