package viewPart.nodes.drawableBIN

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import viewPart.nodes.drawableTree.DrawableNode

class BINDrawableNode<Pack>(
    override val value: Pack,
    override var leftChild: BINDrawableNode<Pack>? = null,
    override var rightChild: BINDrawableNode<Pack>? = null,
    override var xState: MutableState<Float>,
    override var yState: MutableState<Float>,

    ) : DrawableNode<Pack, BINDrawableNode<Pack>>(BINNodeDesign, BINNodeDesign.colorNode) {
    override val clickState = mutableStateOf(false)

}
