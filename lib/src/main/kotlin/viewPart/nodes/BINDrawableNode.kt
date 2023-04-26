package viewPart.nodes

import viewPart.nodes.design.BINNodeDesign
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

class BINDrawableNode<Pack>(
    override val value: Pack,
    override var leftChild: BINDrawableNode<Pack>? = null,
    override var rightChild: BINDrawableNode<Pack>? = null,
    override val xState: MutableState<Float>,
    override val yState: MutableState<Float>,
) : DrawableNode<Pack, BINDrawableNode<Pack>>() {

    @Composable
    override fun nodeView(
        information: String,
        offsetX: MutableState<Float>,
        offsetY: MutableState<Float>
    ) {
        val modifier = Modifier
            .offset {
                IntOffset(
                    x = offsetX.value.roundToInt(),
                    y = offsetY.value.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                }
            }
            .size(BINNodeDesign.nodeSize.dp)
            .clip(BINNodeDesign.shape)
            .background(BINNodeDesign.colorNode)
            .onGloballyPositioned { layoutCoordinates ->
                val rect = layoutCoordinates.boundsInParent()
                xState.value = rect.center.x
                yState.value = rect.center.y
            }
        BINNodeDesign.infoView(information, modifier)
    }

    @Composable
    override fun edgeView(child: BINDrawableNode<Pack>) {
        Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)) {
            drawLine(
                color = BINNodeDesign.lineColor,
                start = Offset(xState.value, yState.value),
                end = Offset(child.xState.value, child.yState.value),
                strokeWidth = BINNodeDesign.lineStrokeWidth, //TODO вынести в конфиг ноды
            )
        }
    }
}
