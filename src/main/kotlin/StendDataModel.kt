import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class StendBoxModel(
    var type: String = "stend",
    var shelvesNum: Int = 3,
    var borderNumber: Int = 0
)
//class StendViewModel : ViewModel() {
//}
@Composable
fun StendBox(model: StendBoxModel, bordersList: SnapshotStateList<BorderStroke>){
//    var borderForStend = remember{ mutableStateOf(BorderStroke(2.dp, Color.Black))}
    var borderForStend = bordersList[model.borderNumber]
    if (model.type=="stend") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
               // .background(Color(0xffff0000))
                .border(borderForStend)
//                .border(if (model.clicked) BorderStroke(2.dp, Color.Black) else BorderStroke(15.dp, Color.Yellow))
        ) {
            Text("stend")
//            println("window size = ${this.}")
//            BoxWithConstraints(
            Box(
                modifier = Modifier
                    .fillMaxSize()
//                    .size()
                    .height(600.dp) //todo вычислять размер стенда по ширине окна минус ширину компа
                    .width(800.dp)
                    //.fillMaxHeight()
//                    .fillMaxWidth()
                   // .clip(RoundedCornerShape(5.dp))
                    //.background(Color.Red)
                    .border(BorderStroke(2.dp, Color.Red))
                    .clickable {
                        println("stend clicked")
                        bordersList[model.borderNumber] = BorderStroke(15.dp, Color.Yellow)
                        for (i in bordersList.indices){
                            if (i!=model.borderNumber) bordersList[i] = BorderStroke(2.dp, Color.Black)
                        }
                    }
//                    clickable {
//                    onItemClick.invoke(HobbyRowItems)
//                    Log.d("Hobby", "Pressed Box")

//                }
            ){
//                println("stend height = ${this.maxHeight}")


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
