package viewPart.nodes.drawableAVL

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import viewPart.nodes.drawableTree.DrawableNode

class AVLDrawableNode<Pack>(
    override val value: Pack,
    override var leftChild: AVLDrawableNode<Pack>? = null,
    override var rightChild: AVLDrawableNode<Pack>? = null,
    val height: Int,
    override val xState: MutableState<Float>,
    override val yState: MutableState<Float>,
) : DrawableNode<Pack, AVLDrawableNode<Pack>>(AVLNodeDesign, AVLNodeDesign.colorNode) {
    override val clickState = mutableStateOf(false)
}
