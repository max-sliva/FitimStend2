import com.formdev.flatlaf.FlatLightLaf
import java.awt.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import javax.swing.*


class SimpleEx(title: String) : JFrame() {

    init {
        createUI(title)
    }

    private fun createUI(title: String) {

        setTitle(title)

        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(400, 300)
        setLocationRelativeTo(null)
    }
}


fun setCentralPart(frame: SimpleEx){
    val pane = JPanel(GridBagLayout())
    pane.layout = GridBagLayout()
    val c = GridBagConstraints()
    val shouldFill = true
    if (shouldFill) {
        //natural height, maximum width
        c.fill = GridBagConstraints.HORIZONTAL
    }

    var button = JButton("Button 1")
    val shouldWeightX = true
    if (shouldWeightX) {
        c.weightx = 0.5
    }
    c.fill = GridBagConstraints.HORIZONTAL
    c.gridx = 0
    c.gridy = 0
    pane.add(button, c)

    button = JButton("Button 2")
    c.fill = GridBagConstraints.HORIZONTAL
    c.weightx = 0.5
    c.gridx = 1
    c.gridy = 0
    pane.add(button, c)

    button = JButton("Button 3")
    c.fill = GridBagConstraints.HORIZONTAL
    c.weightx = 0.5
    c.gridx = 2
    c.gridy = 0
    pane.add(button, c)

    button = JButton("Long-Named Button 4")
    c.fill = GridBagConstraints.HORIZONTAL
    c.ipady = 40 //make this component tall
    c.weightx = 0.0
    c.gridwidth = 3
    c.gridx = 0
    c.gridy = 1
    pane.add(button, c)

    button = JButton("5")
    c.fill = GridBagConstraints.HORIZONTAL
    c.ipady = 0 //reset to default
    c.weighty = 1.0 //request any extra vertical space
    c.anchor = GridBagConstraints.PAGE_END //bottom of space
    c.insets = Insets(10, 0, 0, 0) //top padding
    c.gridx = 1 //aligned with button 2
    c.gridwidth = 2 //2 columns wide
    c.gridy = 2 //third row
    pane.add(button, c)
    frame.add(pane, BorderLayout.CENTER)
}

private fun createAndShowGUI() {
    val frame = SimpleEx("Simple")
//    frame.isUndecorated = true
    val graphics = GraphicsEnvironment.getLocalGraphicsEnvironment()
    val device = graphics.defaultScreenDevice
//    device.setFullScreenWindow(frame) //for full screen
    setUpperBar(frame)
    setCentralPart(frame)
    frame.isVisible = true
}

fun setUpperBar(frame: SimpleEx) {
    val upperNorthBox = Box(BoxLayout.X_AXIS) //верхняя панель
    val newColor = Color("FF1E63B2".toLong(16).toInt())
    upperNorthBox.isOpaque = true
    upperNorthBox.background = newColor
    val facultyLogoWhite = "faculty_white.png"
    val nvsuLogoWhite = "NVSU_white.png"
    var nvsuLogo = ImageIcon(nvsuLogoWhite) //лого универа
    var image = nvsuLogo.image // объект для преобразования
    var newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH) // задаем размер
    nvsuLogo = ImageIcon(newimg) //применяем новые параметры

    val nvsuLabel = JLabel(nvsuLogo) //лейбл с лого универа
//    nvsuLabel.background = backColor

    val text = File("captionText.txt").readText()
    val textLabel = JLabel(text)


//		Font font = new Font("Serif", Font.BOLD, 12);
    var font: Font? = null
    try {
        font = Font.createFont(
            Font.TRUETYPE_FONT,
            File(System.getProperty("user.dir") + "/Fonts/RobotoMedium/Roboto-Medium.ttf")
        )
    } catch (e: FontFormatException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    val genv = GraphicsEnvironment.getLocalGraphicsEnvironment() // объект для регистрации шрифта
    genv.registerFont(font) // регистрируем шрифт
    font = font!!.deriveFont(41f) // задаем ему размер
    textLabel.foreground = Color.WHITE
    textLabel.font = font
    //		nvsuLabel.setBorder(BorderFactory.createLineBorder(myColor, 5));
    var fitimLogo = ImageIcon(facultyLogoWhite) //лого факультета
    image = fitimLogo.image //объект для преобразования
    newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH) // задаем размер
    fitimLogo = ImageIcon(newimg) //применяем новые параметры
    val fitimLabel = JLabel(fitimLogo) //лейбл с лого факультета

    upperNorthBox.add(Box.createHorizontalGlue())
    upperNorthBox.add(nvsuLabel)
    upperNorthBox.add(Box.createHorizontalGlue())
    upperNorthBox.add(textLabel)
    upperNorthBox.add(Box.createHorizontalGlue())
    upperNorthBox.add(fitimLabel)
    upperNorthBox.add(Box.createHorizontalGlue())

    val northBoxMain = Box(BoxLayout.Y_AXIS)
    val itemsComboBox = JComboBox<String>()
    val itemsMap = getItemsMap()
    val curPath = System.getProperty("user.dir")
    val backgroundImage = "items/background.jpg"
    var filesSet =  listOf("$curPath/welcome.txt", "$curPath/$backgroundImage")

    val itemsList = itemsMap.keys.toList()
    val comboBoxModel = DefaultComboBoxModel<String>()
    comboBoxModel.addAll(itemsList)
    itemsComboBox.addActionListener{e->
        println("selected = ${e.actionCommand}")
        println("selected = ${itemsComboBox.selectedItem}")

    }
    itemsComboBox.model = comboBoxModel
    northBoxMain.add(upperNorthBox)
    northBoxMain.add(itemsComboBox)
    frame.add(northBoxMain, BorderLayout.NORTH)

}

fun getItemsMap(): MutableMap<String, Set<String>> {
    val curPath = System.getProperty("user.dir")
    val backgroundImage = "items/background.jpg"
    println("user dir = $curPath")
//    var filesSet =  listOf("$curPath/welcome.txt", "$curPath/$backgroundImage")
    val itemsDir = "$curPath/items/copy_Rarit"
    val dirsList = listDirsUsingDirectoryStream(itemsDir)
    println("dirsList = $dirsList")
    //var setOfItems =
//    var itemsMap = mutableMapOf<String, String>()
    val itemsMap2 = mutableMapOf<String, Set<String>>() //мап для хранения названия экспоната и набора из его описания и картинки
    dirsList.forEach {
        val filesList = listFilesUsingDirectoryStream("$itemsDir/$it")
        val txtFile = filesList.find { it.contains(".txt") }
        val imgFile = filesList.find { it != txtFile }
        val txtFilePath = "$itemsDir/$it/$txtFile"
        val imgFilePath = "$itemsDir/$it/$imgFile"
//        val filesSet = setOf(txtFilePath, imgFilePath)
        val file = File(txtFilePath)
        val ioStream = BufferedReader(FileReader(file))
        val firstStringInFile = ioStream.readLine()
        val s = if (firstStringInFile=="") ioStream.readLine() else firstStringInFile //если первая строка пустая
//        println("for $it: $filesList caption = $s")
//        itemsMap[s] = "$itemsDir/$it"
        itemsMap2[s] = setOf(txtFilePath, imgFilePath)
    }
    itemsMap2.forEach {
        println("${it.key} : ${it.value}")
    }
    return itemsMap2
}

fun main() {
    FlatLightLaf.setup()
    UIManager.setLookAndFeel( FlatLightLaf() )
    val looks = UIManager.getInstalledLookAndFeels()
    for (look in looks) {
        println(look.className)
    }
    EventQueue.invokeLater(::createAndShowGUI)
}
