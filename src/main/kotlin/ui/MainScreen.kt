package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import component.MovieList
import data.MovieApiClient
import data.model.Movie
import data.model.Result
import io.ktor.client.plugins.*
import kotlinx.coroutines.launch
import utils.handCursor
import java.awt.Cursor

@Composable
fun MainScreen(
    sampleResult: Result,
    isRefresh: Boolean,
    isSearchActive: Boolean,
    isDarkTheme: Boolean,
    onItemClick: (Result) -> Unit
) {

    var data by remember { mutableStateOf<Movie?>(null) }
    var resultData by remember { mutableStateOf(emptyList<Result>()) }
    var isLoading by remember { mutableStateOf(true) }
    var isVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var refresh by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf(false) }


    val scope = rememberCoroutineScope()

    LaunchedEffect(isRefresh) {
        isLoading = true
        scope.launch {
            try {
                val movieData = MovieApiClient.getPopular(1)
                data = movieData
                resultData = movieData.results
                isLoading = false
                println("$movieData")
            } catch (e: ClientRequestException) {
                isLoading = false
                e.printStackTrace()
            }
        }
    }

    if (isSearchActive) {
        isLoading = true
        scope.launch {
            try {
                val searchedData = MovieApiClient.getSearched(searchText)
                data = searchedData
                resultData = searchedData.results
                isLoading = false
                println("$searchedData")
            } catch (e: ClientRequestException) {
                isLoading = false
                e.printStackTrace()
            } finally {
                search = false // Reset the search variable after the search operation is completed
            }
        }
    }


    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(color = if (isDarkTheme) Color.DarkGray else Color.White)
        ) {
            if (isLoading) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = if (isDarkTheme) Color.White else MaterialTheme.colors.primary
                    )
                }
            } else {
                if (data == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedVisibility(isSearchActive) {
                            OutlinedTextField(
                                value = searchText,
                                onValueChange = { text ->
                                    searchText = text
                                },
                                modifier = Modifier.fillMaxWidth(0.70f).align(Alignment.CenterHorizontally)
                                    .pointerHoverIcon(
                                        icon = PointerIcon(
                                            Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)
                                        )
                                    ),
                                enabled = isSearchActive,
                                label = {
                                    Text(text = "Search Movie")
                                },
                                placeholder = {
                                    Text("Search Movie")
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                search = true
                                                isLoading = true
                                                data = MovieApiClient.getSearched(searchText)
                                            }
                                        },
                                        modifier = Modifier.pointerHoverIcon(handCursor())
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Search, contentDescription = null,
                                            tint = MaterialTheme.colors.primary,
                                        )
                                    }
                                },
                                singleLine = true,
                                maxLines = 1,
                                shape = RoundedCornerShape(24.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    backgroundColor = Color.White,
                                    cursorColor = MaterialTheme.colors.primary,
                                    focusedBorderColor = MaterialTheme.colors.primary,
                                    trailingIconColor = MaterialTheme.colors.primary,
                                    unfocusedBorderColor = Color.Black,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Characters,
                                    autoCorrect = true,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(onSearch = {
                                    scope.launch {
                                        search = true
                                        isLoading = true
                                        data = MovieApiClient.getSearched(searchText)
                                    }
                                })
                            )
                        }
                        data?.results?.let { results ->
                            MovieList(results) { result ->
                                onItemClick(result)
                            }
                        }

                    }
                }
            }
        }
    }

}

