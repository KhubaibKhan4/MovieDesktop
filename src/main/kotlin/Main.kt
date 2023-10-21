import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.MainScreen


fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Movie Desktop", icon = painterResource("logo.png")) {
        MainScreen()
    }
}
