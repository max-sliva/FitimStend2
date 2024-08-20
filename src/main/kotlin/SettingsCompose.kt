import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.*

@Composable
fun SettingsWindow(isVisible: MutableState<Boolean>, choice: MutableState<Int>) {
    Window(
        onCloseRequest = {
            isVisible.value = false
            choice.value = 0
        },
        visible = isVisible.value,
        undecorated = false, //если в файле mode.txt первое число 0, то без оконных кнопок, иначе они будут, для отладки
        alwaysOnTop = false,
        state = WindowState(WindowPlacement.Fullscreen)
//        state = WindowState(WindowPlacement.Fullscreen)
    ) {
        //
    }
//    DialogWindow(visible = isVisible.value, onCloseRequest = { isVisible.value = false }) {
//        Text("dialog content")
//    }
}