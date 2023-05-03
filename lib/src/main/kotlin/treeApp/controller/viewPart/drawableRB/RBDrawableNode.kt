package treeApp.controller.viewPart.drawableRB

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import treelib.rbTree.Markers
import treeApp.controller.viewPart.drawableTree.DrawableNode
import treeApp.ui.nodeDesign.RBNodeDesign

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
