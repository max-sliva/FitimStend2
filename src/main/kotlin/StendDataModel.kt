import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

data class StendBoxModel(
    var type: String = "stend",
    var shelvesNum: Int = 4,
    var borderNumber: Int = 0
)
//class StendViewModel : ViewModel() {
//}
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun StendBox(
    model: StendBoxModel,
    stendBordersList: SnapshotStateList<BorderStroke>,
    dialogState: MutableState<Boolean>,
    barForStendVisibility: MutableState<Boolean>,
    rowValue: MutableState<String>,
    itemsInStend: MutableState<ItemsInStend?> //todo проверять, есть ли там массив для стенда с нужным номером
){
//    var borderForStend = remember{ mutableStateOf(BorderStroke(2.dp, Color.Black))}
    val windowSize = LocalWindowInfo.current.containerSize
    println("window size = ${windowSize.width}")
    var borderForStend = stendBordersList[model.borderNumber]
    if (model.type=="stend") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
               // .background(Color(0xffff0000))
                .border(borderForStend)
                .clickable{
                    barForStendVisibility.value = true
                    rowValue.value = model.shelvesNum.toString()
                    println("stend left clicked")
                    stendBordersList[model.borderNumber] = BorderStroke(15.dp, Color.Yellow)
                    for (i in stendBordersList.indices){
                        if (i!=model.borderNumber) stendBordersList[i] = BorderStroke(2.dp, Color.Black)
                    }
                }
                .onClick( matcher = PointerMatcher.mouse(PointerButton.Secondary),
                    onClick = {
                        //todo проверить на тачскрине работу правой кнопки
                        println("right button click")
                        dialogState.value = true
                    }
                )
//                .border(if (model.clicked) BorderStroke(2.dp, Color.Black) else BorderStroke(15.dp, Color.Yellow))
        ) {
            Text("stend")
            for (i in 0..< model.shelvesNum) {
                Row(
                    modifier = Modifier
                        .border(BorderStroke(2.dp, Color.Blue))
                         //  .fillMaxSize()
                        .height(200.dp)
                        .width((windowSize.width / 2 - 350).dp)
                        .clickable{
                            println("shelve clicked")
//                            stendBordersList[model.borderNumber] = BorderStroke(15.dp, Color.Yellow)
                        }
                ) {

                }
            }
        }
    } else if (model.type=="comp") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight()
//                .wrapContentHeight()
                .fillMaxWidth()
//                .background(Color(0xff00ff00))
//                .border(BorderStroke(2.dp, Color.Yellow))
        ) {
            Text("comp")
            BoxWithConstraints(
                modifier = Modifier

//                    .fillMaxSize()
//                    .size()
                    .height(600.dp)
                    .width(200.dp)
                    //.fillMaxHeight()
//                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
                    //.background(Color.Red)
                    .border(BorderStroke(2.dp, Color.Green))
            ){
                println("comp height = ${this.maxHeight}")


            }
        }
    }
}
