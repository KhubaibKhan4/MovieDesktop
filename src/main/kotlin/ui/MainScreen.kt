package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import component.MovieList
import data.MovieApiClient
import data.model.Movie
import data.model.Result
import io.ktor.client.plugins.*
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {

    var data by remember {
        mutableStateOf<Movie?>(null)
    }
    var resultData by remember {
        mutableStateOf(emptyList<Result>())
    }
    var isLoading by remember {
        mutableStateOf(true)
    }


    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
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

    MaterialTheme {
        if (isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
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
                data?.results?.let { MovieList(it) }
            }
        }

    }
}
