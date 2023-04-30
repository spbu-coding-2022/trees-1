package viewPart.nodes.drawableBIN

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.MutableState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import viewPart.nodes.drawableTree.DrawableNode
import kotlin.math.roundToInt

class BINDrawableNode<Pack>(
    override val value: Pack,
    override var leftChild: BINDrawableNode<Pack>? = null,
    override var rightChild: BINDrawableNode<Pack>? = null,
    override var xState: MutableState<Float>,
    override var yState: MutableState<Float>,
) : DrawableNode<Pack, BINDrawableNode<Pack>>()
