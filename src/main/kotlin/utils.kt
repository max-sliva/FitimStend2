
import java.io.File
import java.util.stream.Collectors
import java.util.stream.Stream

fun getItems(){

}

fun listFilesUsingJavaIO(dir: String?): Set<String> {
    return Stream.of(*File(dir).listFiles())
        .filter { file: File -> !file.isDirectory }
        .map { obj: File -> obj.name }
        .collect(Collectors.toSet())
}