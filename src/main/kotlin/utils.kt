
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
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

fun listDirsUsingJavaIO(dir: String?): Set<String> {
    return Stream.of(*File(dir).listFiles())
        .filter { file: File -> file.isDirectory }
        .map { obj: File -> obj.name }
        .collect(Collectors.toSet())
}

@Throws(IOException::class)
fun listFilesUsingDirectoryStream(dir: String?): Set<String> {
    val fileSet: MutableSet<String> = HashSet()
    Files.newDirectoryStream(Paths.get(dir)).use { stream ->
        for (path in stream) {
            if (!Files.isDirectory(path)) {
                fileSet.add(
                    path.fileName
                        .toString()
                )
            }
        }
    }
    return fileSet
}

@Throws(IOException::class)
fun listDirsUsingDirectoryStream(dir: String?): Set<String> {
    val fileSet: MutableSet<String> = HashSet()
    Files.newDirectoryStream(Paths.get(dir)).use { stream ->
        for (path in stream) {
            if (Files.isDirectory(path)) {
                fileSet.add(
                    path.fileName
                        .toString()
                )
            }
        }
    }
    return fileSet
}