package component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import data.model.Result
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import java.awt.Cursor

@Composable
fun MovieList(result: List<Result>, onItemClick: (Result) -> Unit) {
    val state = rememberLazyGridState(0,2)
    val scrollbarState = rememberScrollbarAdapter(scrollState = state)
    Box(modifier = Modifier.fillMaxWidth()){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            state = state
        ) {
            items(result) { result ->
                MovieItems(result, onItemClick)
            }
        }
        VerticalScrollbar(adapter = scrollbarState,
            modifier = Modifier.align(Alignment.CenterEnd).wrapContentHeight())
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieItems(result: Result, onItemClick: (Result) -> Unit) {
    val uriHandler = LocalUriHandler.current
    var isVisible by remember {
        mutableStateOf(false)
    }
    val sampleResult = Result(
        adult = result.adult,
        backdropPath = result.backdropPath,
        genreIds = result.genreIds,
        id = result.id,
        originalLanguage = result.originalLanguage,
        originalTitle = result.originalTitle,
        overview = result.overview,
        popularity = result.popularity,
        posterPath = result.posterPath,
        releaseDate = result.releaseDate,
        title = result.title,
        video = result.video,
        voteAverage = result.voteAverage,
        voteCount = result.voteCount
    )
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandHorizontally()
    ) {


        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(24.dp))
                .pointerHoverIcon(icon = PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
                .clickable {
                    onItemClick(sampleResult)
                },
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val imageUrl =
                    asyncPainterResource(data = "https://image.tmdb.org/t/p/w500${result.posterPath ?: "/zbTaYrQzZaaEf1SZlv3RTZiUvZw.jpg"}")
                KamelImage(
                    resource = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    onLoading = {
                        CircularProgressIndicator()
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = "${result.title}",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "${result.overview}",
                        style = MaterialTheme.typography.body1,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.align(Alignment.End),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = if (index < result.voteAverage?.toInt()!!) Icons.Default.Star else Icons.Outlined.Star,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "(${result.voteCount} Reviews)",
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            softWrap = true
                        )
                    }
                }
            }
        }
        if (isVisible) {

            AlertDialog(
                onDismissRequest = { isVisible = !isVisible },
                title = { Text("${result.title}") },
                text = { Text("${result.overview}") },
                modifier = Modifier.fillMaxWidth(),
                confirmButton = {
                    TextButton({
                        uriHandler.openUri("https://image.tmdb.org/t/p/w500${result.posterPath}")

                    }) {
                        Text("More")
                    }
                },
                dismissButton = {
                    TextButton({
                        isVisible = !isVisible
                    }) {
                        Text("Dismiss")
                    }
                },
                shape = MaterialTheme.shapes.medium,
                backgroundColor = Color.White
            )
        }
    }
}
