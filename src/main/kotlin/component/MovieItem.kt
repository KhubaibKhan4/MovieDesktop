package component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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

@Composable
fun MovieItems(result: Result) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .pointerHoverIcon(icon = PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
            .clickable {
                uriHandler.openUri("https://image.tmdb.org/t/p/w500${result.posterPath}")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                loadPicture("https://image.tmdb.org/t/p/w500${result.posterPath}") ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = result.title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = result.overview,
                    style = MaterialTheme.typography.body1,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(modifier = Modifier.align(Alignment.End)) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < result.voteAverage!!.toInt()) Icons.Default.Star else Icons.Outlined.Star,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(${result.voteCount} Reviews)",
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}