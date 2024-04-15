
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import java.util.prefs.Preferences
import javax.swing.JFrame


class SettingsWork: JFrame() {
    private lateinit var prefs: Preferences

    init {
        createUI("Настройки")
//        println("filesSet = $filesSet")
    }

    private fun createUI(title: String) {
        setTitle(title)
//        defaultCloseOperation = EXIT_ON_CLOSE
//        setUpperBar()
//        setCentralPart()
        setSize(800, 600)
        setLocationRelativeTo(null)
        setPreference()
    }

    fun setPreference() {
        // This will define a node in which the preferences can be stored
        prefs = Preferences.userRoot().node(this.javaClass.name)
        val ID1 = "Test1"
        val ID2 = "Test2"
        val ID3 = "Test3"

        // First we will get the values
        // Define a boolean value
        println(prefs.getBoolean(ID1, true))
        // Define a string with default "Hello World
        println(prefs.get(ID2, "Hello World"))
        // Define a integer with default 50
        println(prefs.getInt(ID3, 50))
        println("all prefs = $prefs")
        // now set the values
//        prefs.putBoolean(ID1, false)
//        prefs.put(ID2, "Hello Europa")
//        prefs.putInt(ID3, 45)

        // Delete the preference settings for the first value
//        prefs.remove(ID1)
    }

    fun saveProperties() {
        try {
            val USER_NAME = "Some name"
            val DP_ADDRESS = "Some url"
            //create a properties file
            val props = Properties()
            props.setProperty("User name", USER_NAME)
            props.setProperty("Display picture address", DP_ADDRESS)
            val f = File("YOUR_TARGET_FILE_PATH")
            val out: OutputStream = FileOutputStream(f)
            //If you wish to make some comments
            props.store(out, "User properties")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}