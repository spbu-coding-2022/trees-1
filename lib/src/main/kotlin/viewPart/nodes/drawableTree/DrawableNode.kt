package viewPart.nodes.drawableTree

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import viewPart.nodes.drawableBIN.BINNodeDesign
import kotlin.math.roundToInt

abstract class DrawableNode<Pack, NodeType : DrawableNode<Pack, NodeType>> {
    abstract val value: Pack
    abstract var leftChild: NodeType?
    abstract var rightChild: NodeType?
    abstract val xState: MutableState<Float>
    abstract val yState: MutableState<Float>

    var modifier = Modifier
            .offset {
                IntOffset(
                    x = xState.value.roundToInt(),
                    y = yState.value.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    xState.value += dragAmount.x
                    yState.value += dragAmount.y
                }
            }
            .size(BINNodeDesign.nodeSize.dp)
            .clip(BINNodeDesign.shape)
            .background(BINNodeDesign.colorNode)
}
