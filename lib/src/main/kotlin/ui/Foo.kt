package ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

data class ScreenScale(
    val scale: Float
)

class State() {

    var screenScale by mutableStateOf(ScreenScale(1f))

    var screenDrag by mutableStateOf(Offset(0f, 0f))
        private set

    fun handleScreenDrag(dragAmount: Offset) {
        screenDrag += Offset(
            dragAmount.x / screenScale.scale,
            dragAmount.y / screenScale.scale
        )
    }
}