import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import io.github.vinceglb.filekit.compose.rememberDirectoryPickerLauncher

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsWindow(isVisible: MutableState<Boolean>, choice: MutableState<Int>) {
   // var btnRemoveIsEnabled = mutableStateOf(false)
    var stendsAddedNum = mutableStateOf(0)
    var compAddedNum = mutableStateOf(0)
    var stendList = remember {mutableStateListOf<StendBoxModel>()}
    var bordersList = remember { mutableStateListOf<BorderStroke>() }
//    stendList.add(StendBoxModel("stend", 3))
//    stendList.add(StendBoxModel("comp", 0))
//    stendList.add(StendBoxModel("stend", 3))
    var directoryName = mutableStateOf("")
    val directoryPickerLauncher = rememberDirectoryPickerLauncher(
        title = "Выберите папку с экспонатами",
//                            initialDirectory = "/custom/initial/path"
    ) { directory ->
        println("directory = ${directory?.path}")
        directoryName.value = directory?.path!!
        val itemsMap = getItemsMap(directoryName.value) //todo разобраться, почему не работает
        println("itemsMap = $itemsMap")
    }
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
//        val windowSize = LocalWindowInfo.current.containerSize

//        val windowHeight = remember { mutableStateOf(window.height) }
//        val windowState = rememberWindowState(size = DpSize.Unspecified)
//        println("window height = ${windowState.size.height}")
        val dialogState = remember { mutableStateOf(false) }
        DialogWindow(onCloseRequest = { dialogState.value = false }, visible = dialogState.value,
            content = {
                Text("dialog content")
            }
        )
        Column(Modifier.fillMaxSize()){
            Row(//верхний ряд с управляющими элементами
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
//                        .padding(20.dp)
                    .background(Color(0xff1e63b2))
//                    .background(Color.Black)
                    .padding(5.dp)

            ) {
                ControlBar(stendsAddedNum, compAddedNum, stendList, bordersList)
//                println("window height = $")
            }
            Row(// ряд с кнопкой загрузки экспонатов
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
//                        .padding(20.dp)
                    .background(Color(0xff1e63b2))
//                    .background(Color.Black)
                    .padding(5.dp)

            ) {
                Button(
                    onClick = {
                        println("loading folder with items")
//                        GlobalScope.launch {
//                            val directory = FileKit.pickDirectory(
//                                title = "Pick a directory",
//                                initialDirectory = "/custom/initial/path"
//                            )
//                        }
                        directoryPickerLauncher.launch()
                    }
                ){
                    Text("Папка с экспонатами...")
                }
                Text(text = directoryName.value, color = Color.White)
            }

            LazyRow ( //центральный ряд с содержимым
                horizontalArrangement = Arrangement.spacedBy(5.dp,Alignment.CenterHorizontally),
                modifier = Modifier
                    .fillMaxSize()
//                    .ho
                    .border(BorderStroke(2.dp, Color(0xff1e63b2)))
//                    .border(BorderStroke(2.dp, Color(0xff00ff00)))
                    .padding(30.dp)
            ) {
                items(stendList) { model ->
                    StendBox(model = model, bordersList, dialogState)
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
    stendList: MutableList<StendBoxModel>,
    bordersList: SnapshotStateList<BorderStroke>,
) {
    Button(
        onClick = {
            //btnRemoveIsEnabled.value = true
            stendList.add(StendBoxModel("stend", 3, stendsAddedNum.value))
            stendsAddedNum.value++
            bordersList.add(BorderStroke(2.dp, Color.Black))
//            println("stendList = $stendList")
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