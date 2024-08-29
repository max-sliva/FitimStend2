import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

@Composable
fun SettingsWindow(isVisible: MutableState<Boolean>, choice: MutableState<Int>) {
   // var btnRemoveIsEnabled = mutableStateOf(false)
    var stendsAddedNum = mutableStateOf(0)
    var compAddedNum = mutableStateOf(0)
    var stendList = remember {mutableStateListOf<StendBoxModel>()}
//    stendList.add(StendBoxModel("stend", 3))
//    stendList.add(StendBoxModel("comp", 0))
//    stendList.add(StendBoxModel("stend", 3))
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
//        val windowState = rememberWindowState(size = DpSize.Unspecified)
//        println("window height = ${windowState.size.height}")
        Column(Modifier.fillMaxSize()){
            println("window height = ${ window.height}")
//            window.height

            Row(//верхний ряд с управляющими элементами
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
//                        .padding(20.dp)
                    .background(Color(0xff1e63b2))
                    .padding(5.dp)

            ) {
                ControlBar(stendsAddedNum, compAddedNum, stendList)
                println("window height = $")
            }
            LazyRow ( //центральный ряд с содержимым
                modifier = Modifier
                    .fillMaxSize()
                    .border(BorderStroke(2.dp, Color(0xff1e63b2)))
//                    .border(BorderStroke(2.dp, Color(0xff00ff00)))
                    .padding(30.dp)
            ) {
                items(stendList) { model ->
                    StendBox(model = model)
                }
            }
        }

    }
//    DialogWindow(visible = isVisible.value, onCloseRequest = { isVisible.value = false }) {
//        Text("dialog content")
//    }
}

@Composable
private fun ControlBar(
    stendsAddedNum: MutableState<Int>,
    compAddedNum: MutableState<Int>,
    stendList: MutableList<StendBoxModel>
) {
    Button(
        onClick = {
            //btnRemoveIsEnabled.value = true
            stendsAddedNum.value++
            stendList.add(StendBoxModel("stend", 3))
            println("stendList = $stendList")
        },
        modifier = Modifier
            .padding(5.dp)
    ) {
        Text("Добавить стенд")
    }
    Button(
        onClick = {
            //btnRemoveIsEnabled.value = false
            stendsAddedNum.value--
            stendList.reverse()
            for (element in stendList) {
                if (element.type == "stend") {
                    stendList.remove(element)
                    break
                }
            }
            stendList.reverse()
        },
        enabled = stendsAddedNum.value > 0,
        modifier = Modifier
            .padding(5.dp)
    ) {
        Text("Убрать стенд")
    }
    Button(
        onClick = {
            compAddedNum.value++
            stendList.add(StendBoxModel("comp", 0))
        },
        modifier = Modifier
            .padding(5.dp)
    ) {
        Text("Добавить комп")
    }
    Button(
        onClick = {
            compAddedNum.value--
            stendList.reverse()
            for (element in stendList) {
                if (element.type == "comp") {
                    stendList.remove(element)
                    break
                }
            }
            stendList.reverse()
        },
        enabled = compAddedNum.value > 0,
        modifier = Modifier
            .padding(5.dp)
    ) {
        Text("Убрать комп")
    }
}