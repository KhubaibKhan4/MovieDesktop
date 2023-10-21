package data

import data.model.Movie
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import utils.Constant.Companion.AUTHORIZATION
import utils.Constant.Companion.BEARER_TOKEN
import java.util.logging.Logger

object MovieApiClient {

    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(CIO) {

        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 100000
        }
        defaultRequest {
            header(
                AUTHORIZATION,
                BEARER_TOKEN
            )
        }


    }

    suspend fun getPopular(page: Int): Movie {
        val url = "https://api.themoviedb.org/3/movie/popular?language=en-US&page=${page}"
        return client.get(url).body()
    }

    suspend fun getTopRated(page: Int): Movie {
        val url = "https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=${page}"
        return client.get(url).body()
    }

}