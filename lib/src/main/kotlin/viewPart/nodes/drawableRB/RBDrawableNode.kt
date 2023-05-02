package viewPart.nodes.drawableRB

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import treelib.rbTree.Markers
import viewPart.nodes.drawableTree.DrawableNode

class RBDrawableNode<Pack>(
    override val value: Pack,
    override var leftChild: RBDrawableNode<Pack>? = null,
    override var rightChild: RBDrawableNode<Pack>? = null,
    val color: Markers,
    xState: MutableState<Float>,
    yState: MutableState<Float>,
) : DrawableNode<Pack, RBDrawableNode<Pack>>(RBNodeDesign, if (color == Markers.BLACK) RBNodeDesign.blackMarker else RBNodeDesign.redMarker, xState, yState) {
    override val clickState = mutableStateOf(false)
}
