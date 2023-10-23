package component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberWindowState
import data.model.Result
import utils.handCursor
import utils.loadPicture
import java.awt.Cursor

@Composable
fun MovieList(result: List<Result>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(result) { result ->
            MovieItems(result)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieItems(result: Result) {
    val uriHandler = LocalUriHandler.current
    var isVisible by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .pointerHoverIcon(icon = PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
            .clickable {
                isVisible = !isVisible
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                loadPicture("https://image.tmdb.org/t/p/w500${result.posterPath ?: "/zbTaYrQzZaaEf1SZlv3RTZiUvZw.jpg"}"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(shape = RoundedCornerShape(8.dp)),
                filterQuality = FilterQuality.High
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
