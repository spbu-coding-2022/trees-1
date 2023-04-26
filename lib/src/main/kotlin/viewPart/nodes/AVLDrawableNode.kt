package viewPart.nodes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

class AVLDrawableNode<Pack>(
    override val value: Pack,
    override var leftChild: AVLDrawableNode<Pack>? = null,
    override var rightChild: AVLDrawableNode<Pack>? = null,
    val height: Int,
    override val xState: MutableState<Float>,
    override val yState: MutableState<Float>,
) : DrawableNode<Pack, AVLDrawableNode<Pack>>() {

    @Composable
    override fun nodeView(
        information: String,
        offsetX: MutableState<Float>,
        offsetY: MutableState<Float>
    ) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun edgeView(child: AVLDrawableNode<Pack>) {
        TODO("Not yet implemented")
    }


}
