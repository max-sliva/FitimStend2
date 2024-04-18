
import java.awt.BorderLayout
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import java.util.prefs.Preferences
import javax.swing.*


class SettingsWork: JFrame() {
    private lateinit var prefs: Preferences
    private val curPath = System.getProperty("user.dir")
//    val rootPath = curPath
    private val appConfigPath = "$curPath/app.properties"
    private val folderNamePath = "$curPath/folderName.properties"
    private lateinit var itemsFolderName: String
    private var itemsDir: String
    private var dirsList:  Set<String>

    init {
        val appProps = Properties()
        appProps.load(FileInputStream(folderNamePath))
        println("props = ${appProps.entries}")
        itemsFolderName = appProps.getProperty("itemsFolder")
        itemsDir = "$curPath/items/$itemsFolderName"
        dirsList = listDirsUsingDirectoryStream(itemsDir)
        println("dirsList = $dirsList")
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
//        setProperties()
        readProperties()
        val savePropsButton = JButton("Сохранить")
        savePropsButton.addActionListener {
            saveProperties()
        }

        val setItemsDirectory = JButton("Папка с файлами...")
        setItemsDirectory.addActionListener {
            val label = JLabel("")
            val fileChooser = JFileChooser()
            fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
//            println("curPath = $curPath")
            fileChooser.currentDirectory = File(curPath)
            val option = fileChooser.showOpenDialog(this)
            if (option == JFileChooser.APPROVE_OPTION) {
                val file = fileChooser.selectedFile
                label.text = "Folder Selected: ${file.name}"
                println("Folder Selected: ${file.name}")
            //todo сделать сохранение пути file.name в файл с Properties (folderName.properties)
                val FOLDER_NAME = file.name
                val props = Properties()
                props.setProperty("itemsFolder", FOLDER_NAME)
                val f = File(folderNamePath)
                val out: OutputStream = FileOutputStream(f)
                props.store(out, "folder properties")

            } else {
                label.text = "Open command canceled"
                println("Open command canceled")
            }
        }

        val northUpperBox = Box(BoxLayout.X_AXIS)
        northUpperBox.add(setItemsDirectory)
        northUpperBox.add(Box.createHorizontalGlue())
        northUpperBox.add(savePropsButton)

        val northLowerBox = Box(BoxLayout.X_AXIS) // или JPanel с FlowLayout сделать для номеров кнопок

        val northBox = Box(BoxLayout.Y_AXIS)
        northBox.add(northUpperBox)
        northBox.add(northLowerBox)

        add(northBox, BorderLayout.NORTH)
    }

    fun readProperties(){
//        val rootPath = Thread.currentThread().contextClassLoader.getResource("").path

        val appProps = Properties()
        appProps.load(FileInputStream(appConfigPath))
        println("props = ${appProps.entries}")
//        val appVersion = appProps.getProperty("version")
//        println("version = $appVersion")
    }

    fun setProperties() {
//        // This will define a node in which the preferences can be stored
//        prefs = Preferences.userRoot().node(this.javaClass.name)
//        val ID1 = "Test1"
//        val ID2 = "Test2"
//        val ID3 = "Test3"
//
//        // First we will get the values
//        // Define a boolean value
//        println(prefs.getBoolean(ID1, true))
//        // Define a string with default "Hello World
//        println(prefs.get(ID2, "Hello World"))
//        // Define a integer with default 50
//        println(prefs.getInt(ID3, 50))
//        println("all prefs = $prefs")
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
            val f = File(appConfigPath)
            val out: OutputStream = FileOutputStream(f)
            //If you wish to make some comments
            props.store(out, "User properties")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}