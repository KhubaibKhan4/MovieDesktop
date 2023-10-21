package utils

import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.pointer.PointerIcon
import org.jetbrains.skia.Image
import org.jetbrains.skiko.Cursor
import java.net.URL

class Constant {
    companion object{
        const val AUTHORIZATION = "Authorization"
        const val BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjE2ODA5YWI0NzU5ZWMyMzBmYmM4MWQzNjZiZDM1MSIsInN1YiI6IjYyZDUzY2ZlNzJjMTNlMDYyZmVkYmU2NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.fsCUNzYIaSPkJ5Phuf1k1-iJyNb4_jIx88p_A4h4XM8"
    }
}

fun loadPicture(url: String) =
    Image.makeFromEncoded(URL(url).readBytes())
        .toComposeImageBitmap()

fun handCursor() = PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))