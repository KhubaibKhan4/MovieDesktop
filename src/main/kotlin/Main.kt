import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import component.ExitDialog
import data.model.Result
import navigation.screens.Screen
import ui.DetailScreen
import ui.MainScreen


fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Floating,
        isMinimized = true,
        position = WindowPosition(alignment = Alignment.Center),
        size = DpSize(width = 860.dp, height = 720.dp),

    )

    val sampleResult = Result(
        adult = false,
        backdropPath = "/sample_backdrop_path.jpg",
        genreIds = listOf(28, 12, 16), // Replace with your sample genre ids
        id = 1,
        originalLanguage = "en",
        originalTitle = "Sample Original Title",
        overview = "This is a sample movie overview. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        popularity = 123.45,
        posterPath = "/sample_poster_path.jpg",
        releaseDate = "2023-10-24", // Replace with your sample release date
        title = "Sample Title",
        video = false,
        voteAverage = 4.5,
        voteCount = 100
    )
    var selectedResult by remember { mutableStateOf(sampleResult) }


    var isOpen by remember {
        mutableStateOf(false)
    }
    var currentScreen by remember {
        mutableStateOf(Screen.MAIN)
    }

    Window(
        onCloseRequest = { isOpen = true },
        title = "Movie Desktop",
        icon = painterResource("logo.png"),
        visible = true,
        state = state,
        undecorated = false,
        transparent = false,
        resizable = true,
        enabled = true,
    ) {
        when (currentScreen) {
            Screen.MAIN -> MainScreen(sampleResult) {result ->
                currentScreen = Screen.DETAIL
                selectedResult = result
            }

            Screen.DETAIL -> DetailScreen(selectedResult) {
                currentScreen = Screen.MAIN
            }
        }
        if (isOpen) {
            ExitDialog(true, onDialogClose = { isOpen = false })
        }
    }
}
