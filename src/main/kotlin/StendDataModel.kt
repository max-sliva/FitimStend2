import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                .background(Color(0xffff0000))
                .border(BorderStroke(2.dp, Color(0xffff0000)))
        ) {
            Text("stend")
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
