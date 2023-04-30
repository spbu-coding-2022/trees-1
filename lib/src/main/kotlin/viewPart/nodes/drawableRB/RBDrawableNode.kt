package viewPart.nodes.drawableRB

import androidx.compose.runtime.MutableState
import treelib.rbTree.Markers
import viewPart.nodes.drawableTree.DrawableNode

class RBDrawableNode<Pack>(
    override val value: Pack,
    override var leftChild: RBDrawableNode<Pack>? = null,
    override var rightChild: RBDrawableNode<Pack>? = null,
    val color: Markers,
    override val xState: MutableState<Float>,
    override val yState: MutableState<Float>,
) : DrawableNode<Pack, RBDrawableNode<Pack>>()
