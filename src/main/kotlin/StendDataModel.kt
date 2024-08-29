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
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
               // .background(Color(0xffff0000))
            //    .border(BorderStroke(2.dp, Color(0xffff0000)))
        ) {
            Text("stend")
            Box(
                modifier = Modifier
                    .size(500.dp)
                    .fillMaxHeight()
//                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    //.background(Color.Red)
                    .border(BorderStroke(2.dp, Color.Red))
            )
//            val hexagon = remember {
//                RoundedPolygon(
//                    6,
//                    rounding = CornerRounding(0.2f)
//                )
//            }
//            val clip = remember(hexagon) {
//                RoundedPolygonShape(polygon = hexagon)
//            }
//            Box(
//                modifier = Modifier
//                    .clip(clip)
//                    //.background(MaterialTheme.colorScheme.secondary)
//                    .size(200.dp)
//            ) {
//                Text(
//                    "Hello Compose",
//                    //color = MaterialTheme.colorScheme.onSecondary,
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
        }
    } else if (model.type=="comp") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(Color(0xff00ff00))
                .border(BorderStroke(2.dp, Color(0xff00ff00)))
        ) {
            Text("comp")
        }
    }
}
