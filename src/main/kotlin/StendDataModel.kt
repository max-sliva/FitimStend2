import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class StendBoxModel(var type: String = "stend", var shelvesNum: Int = 3)
//class StendViewModel : ViewModel() {
//}
@Composable
fun StendBox(model: StendBoxModel){
    if (model.type=="stend") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
               // .background(Color(0xffff0000))
                .border(BorderStroke(2.dp, Color.Black))
        ) {
            Text("stend")
            println("window size = $this.")
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
//                    .size()
                    .height(600.dp)
                    .width(400.dp)
                    //.fillMaxHeight()
//                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
                    //.background(Color.Red)
                    .border(BorderStroke(2.dp, Color.Red))
            ){
                println("stend height = ${this.maxHeight}")


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
                    .height(300.dp)
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
