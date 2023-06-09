package treeApp.controller.viewPart.drawableBIN

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import treeApp.controller.viewPart.drawableTree.DrawableNode
import treeApp.ui.nodeDesign.BINNodeDesign

class BINDrawableNode<Pack>(
    override val value: Pack,
    override var leftChild: BINDrawableNode<Pack>? = null,
    override var rightChild: BINDrawableNode<Pack>? = null,
    xState: MutableState<Float>,
    yState: MutableState<Float>,

    ) : DrawableNode<Pack, BINDrawableNode<Pack>>(BINNodeDesign, BINNodeDesign.colorNode, xState, yState) {
    override val clickState = mutableStateOf(false)

}
