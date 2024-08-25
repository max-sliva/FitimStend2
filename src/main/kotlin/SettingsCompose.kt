import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

@Composable
fun SettingsWindow(isVisible: MutableState<Boolean>, choice: MutableState<Int>) {
    Window(
        onCloseRequest = {
            isVisible.value = false
            choice.value = 0
        },
        visible = isVisible.value,
        undecorated = false,
        alwaysOnTop = false,
        state = WindowState(WindowPlacement.Fullscreen)
//        state = WindowState(WindowPlacement.Fullscreen)
    ) {
        Column(Modifier.fillMaxSize()){
            Row(//верхний ряд с управляющими элементами
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
//                        .padding(20.dp)
                    .background(Color(0xff1e63b2))
            ) {

            }
            Row(//центральный ряд с содержимым
                modifier = Modifier
                    .fillMaxSize()
                    .border(BorderStroke(2.dp, Color(0xff1e63b2)))
                    .padding(30.dp)
            ) {

            }
        }

    }
//    DialogWindow(visible = isVisible.value, onCloseRequest = { isVisible.value = false }) {
//        Text("dialog content")
//    }
}