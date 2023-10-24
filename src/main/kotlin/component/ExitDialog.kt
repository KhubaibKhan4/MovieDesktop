package component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import utils.handCursor
import kotlin.system.exitProcess

@Composable
fun ExitDialog(isOpen: Boolean, onDialogClose: () -> Unit) {
    val dialogState = rememberDialogState(
        position = WindowPosition(alignment = Alignment.Center),
        width = 430.dp,
        height = 170.dp
    )
    if (isOpen) {
        Dialog(
            onCloseRequest = onDialogClose,
            state = dialogState,
            visible = true,
            title = "Do you want to Close?",
            icon = painterResource(resourcePath = "logo.png"),
            undecorated = false,
            transparent = false,
            resizable = true,
            enabled = true,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Do you really want to close the application?",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { onDialogClose()},
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .pointerHoverIcon(handCursor()),
                    ) {
                        Text(
                            "No",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        )
                    }

                    Button(
                        onClick = { exitProcess(status = 1) },
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .pointerHoverIcon(handCursor()),
                    ) {
                        Text(
                            "Yes",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        )
                    }
                }
            }
        }
    }
}
