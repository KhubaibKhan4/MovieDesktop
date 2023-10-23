import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import ui.MainScreen


fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Floating,
        isMinimized = true,
        position = WindowPosition(alignment = Alignment.Center),
        size = DpSize(width = 860.dp, height = 720.dp)
    )

    var isOpen  by remember {
        mutableStateOf(true)
    }
    var isClose by remember {
        mutableStateOf(false)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Movie Desktop",
        icon = painterResource("logo.png"),
        visible = true,
        state = state,
        undecorated = false,
        transparent = false,
        resizable = true,
        enabled = true,
    ) {
        MainScreen()
    }
}
