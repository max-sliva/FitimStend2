import androidx.compose.ui.text.substring

/*
Я недавно, как всегда, искал интересные вещи, которые можно использовать (листал ютуб), и наткнулся на такую вещь, как "Формула всего".
Вдохновлённый такой вещью, я решил попытаться сделать цикл всего, в котором можно будет найти любую комбинацию цифр.
Суть цикла такова: начиная с единицы, нужно добавлять каждое n-ое число к строке, но после каждого добавления увеличивать n на 1.
По итогу 1-5 цифры цикла будут "13610".
Я не уверен есть ли тут прям ЛЮБАЯ комбинация цифр, но пароль в этом цикле точно есть, где-то со 123 по 129 символ.
 */
fun main(){
    var s: String = ""
    var n = 1
    var b = 1
    for(i in 1..100000){
        if (i==b){
            s+=i
            println("s size = ${s.length} s= $s")
            n++
            b+=n
        }
        if (s.length>=129) {

           // println("s = 129")
            break
        }
    }
    val d = s.length-129
    val s2=s.substring(0..128)
    println("s2 size = ${s2.length} s2 = $s2")
    println(s2.substring(122..128))


}