import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import component.ExitDialog
import data.model.Result
import navigation.screens.Screen
import ui.DetailScreen
import ui.MainScreen


fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Floating,
        isMinimized = false,
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

    var isVisible by remember { mutableStateOf(true) }
    var isActive by remember { mutableStateOf(false) }
    val dialogState = rememberDialogState()

    var isDarkTheme by remember {
        mutableStateOf(false)
    }
    var isAbout by remember {
        mutableStateOf(false)
    }

    var isUpdate by remember {
        mutableStateOf(false)
    }
    var isRefresh by remember {
        mutableStateOf(false)
    }
    var isSearchActive by remember {
        mutableStateOf(false)
    }
    var action by remember {
        mutableStateOf("Last Action: None")
    }

    val refreshIcon = rememberVectorPainter(image = Icons.Default.Refresh)
    val searchIcon = rememberVectorPainter(image = Icons.Default.Search)
    val exitIcon = rememberVectorPainter(image = Icons.Default.ExitToApp)
    val darkIcon = rememberVectorPainter(image = Icons.Default.DarkMode)
    val lightIcon = rememberVectorPainter(image = Icons.Default.LightMode)
    val aboutIcon = rememberVectorPainter(image = Icons.Default.Accessibility)
    val updateIcon = rememberVectorPainter(image = Icons.Default.Update)

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
        MenuBar {
            Menu(text = "File", mnemonic = 'F') {
                Item(
                    "Refresh",
                    onClick = {
                        isRefresh = !isRefresh
                    },
                    shortcut = KeyShortcut(key = Key.R, ctrl = true),
                    icon = refreshIcon
                )
                Item(
                    "Search",
                    onClick = {
                        isSearchActive = !isSearchActive
                    },
                    shortcut = KeyShortcut(key = Key.S, ctrl = true),
                    icon = searchIcon
                )
                Item(
                    "Exit",
                    onClick = { exitApplication() },
                    shortcut = KeyShortcut(key = Key.Escape, alt = true),
                    mnemonic = 'E',
                    icon = exitIcon
                )

            }

            Menu("Setting", mnemonic = 'S') {
                Menu(
                    "Themes", enabled = true,
                    mnemonic = 'T',
                ) {
                    Item(
                        "Dark Theme",
                        enabled = if (isDarkTheme) false else true,
                        mnemonic = 'D',
                        shortcut = KeyShortcut(key = Key.D, ctrl = true),
                        onClick = {
                            isDarkTheme = !isDarkTheme
                        },
                        icon = darkIcon
                    )
                    Item(
                        "Light Theme",
                        enabled = isDarkTheme,
                        mnemonic = 'L',
                        shortcut = KeyShortcut(key = Key.L, ctrl = true),
                        onClick = {
                            isDarkTheme = false
                        },
                        icon = lightIcon
                    )
                }
            }

            Menu("View", mnemonic = 'V') {
                Item(
                    "About",
                    icon = aboutIcon,
                    onClick = {
                        isAbout = !isAbout
                    },
                    shortcut = KeyShortcut(key = Key.A, ctrl = true)
                )

                Item(
                    "Checks for Updates",
                    icon = updateIcon,
                    onClick = {
                        isUpdate = !isUpdate
                    },
                    shortcut = KeyShortcut(key = Key.U, ctrl = true)
                )

            }

        }

        when (currentScreen) {
            Screen.MAIN -> MainScreen(sampleResult, isRefresh, isSearchActive, isDarkTheme) { result ->
                currentScreen = Screen.DETAIL
                selectedResult = result
            }

            Screen.DETAIL -> DetailScreen(selectedResult, isDarkTheme) {
                currentScreen = Screen.MAIN
            }
        }
        if (isOpen) {
            ExitDialog(true, onDialogClose = { isOpen = false })
        }
    }
    if (isAbout) {
        DialogWindow(
            onCloseRequest = {
                isAbout = !isAbout
            },
            state = dialogState,
            visible = true,
            title = "About Us",
            icon = aboutIcon,
            undecorated = false,
            transparent = false,
            resizable = false,
            enabled = true,
            focusable = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "About Us",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Welcome to the Wallpaper Desktop Application, a project crafted with passion and dedication by Muhammad Khubaib Imtiaz. This application is designed to provide you with a stunning collection of wallpapers to beautify your desktop and bring a fresh aesthetic to your workspace. Explore a diverse range of high-quality images carefully curated to cater to your unique preferences and style.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
        }
    }
    if (isUpdate) {
        DialogWindow(
            onCloseRequest = {
                isUpdate = !isUpdate
            },
            state = dialogState,
            visible = true,
            title = "Update Available",
            icon = updateIcon,
            undecorated = false,
            transparent = false,
            resizable = false,
            enabled = true,
            focusable = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Update Available",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Text(
                    text = "A new version of the Wallpaper Desktop Application is available. We recommend updating to the latest version to enjoy new features, enhancements, and bug fixes.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = {
                            isUpdate = !isUpdate
                        },
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text("Update Now")
                    }
                    OutlinedButton(
                        onClick = {
                            isUpdate = !isUpdate
                        },
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text("Remind Me Later")
                    }
                }
            }
        }
    }
}
