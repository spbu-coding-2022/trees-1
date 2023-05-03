package treeApp.controller.viewPart.drawableTree

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import treeApp.ui.nodeDesign.NodeDesign
import kotlin.math.roundToInt

abstract class DrawableNode<Pack, NodeType : DrawableNode<Pack, NodeType>>(
    design : NodeDesign,
    color : Color,
    val xState: MutableState<Float>,
    val yState: MutableState<Float>,
) {
    abstract val value: Pack
    abstract var leftChild: NodeType?
    abstract var rightChild: NodeType?
    abstract val clickState: MutableState<Boolean>
    @OptIn(ExperimentalFoundationApi::class)
    var modifier = Modifier
            .offset {
                IntOffset(
                    x = xState.value.roundToInt(),
                    y = yState.value.roundToInt()
                )
            }
            .pointerInput(xState, yState) {
                detectDragGestures { _, dragAmount ->
                    xState.value += dragAmount.x
                    yState.value += dragAmount.y
                }
            }
            .size(design.nodeSize.dp)
            .clip(design.shape)
            .background(color)
            .onClick { clickState.value = !clickState.value }

}
