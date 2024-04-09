import com.formdev.flatlaf.FlatLightLaf
import java.awt.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import javax.swing.*


class SimpleEx(title: String) : JFrame() {

    private val curPath = System.getProperty("user.dir")
    private val backgroundImage = "items/background.jpg"
    private var filesSet =  listOf("$curPath/welcome.txt", "$curPath/$backgroundImage")
    private val textArea = JTextArea()
//    private val imageHolder = JButton("")
    private val imageHolder = JLabel()
    init {
        createUI(title)
        println("filesSet = $filesSet")
    }

    fun getFilesSet() = filesSet

    fun getImageHolder() = imageHolder

    private fun createUI(title: String) {
        setTitle(title)
        defaultCloseOperation = EXIT_ON_CLOSE
        setUpperBar()
        setCentralPart()
        setSize(400, 300)
        setLocationRelativeTo(null)
    }

    private fun setUpperBar() {
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
//        itemsComboBox.va
        val itemsMap = getItemsMap()
//        val curPath = System.getProperty("user.dir")
//        val backgroundImage = "items/background.jpg"
//        var filesSet =  listOf("$curPath/welcome.txt", "$curPath/$backgroundImage")

        val itemsList = itemsMap.keys.toList()
        val comboBoxModel = DefaultComboBoxModel<String>()
        itemsComboBox.setRenderer(MyComboBoxRenderer("Выберите экспонат: ▼"));
        comboBoxModel.addAll(itemsList)
        itemsComboBox.addActionListener{e->
            println("selected = ${e.actionCommand}")
            println("selected = ${itemsComboBox.selectedItem}")
            val tempList = itemsMap[itemsComboBox.selectedItem]?.toList()
            filesSet = listOf()
            filesSet = filesSet.plus(tempList!!.first())
            filesSet = filesSet.plus(tempList.last())
            println("filesSet = $filesSet")
            val text = File(filesSet.first()).readText()
            textArea.text = text
        }
        itemsComboBox.model = comboBoxModel
        val lowerNorthBox = Box(BoxLayout.X_AXIS)
        lowerNorthBox.add(itemsComboBox)
        lowerNorthBox.add(Box.createHorizontalGlue())
        northBoxMain.add(upperNorthBox)
        northBoxMain.add(lowerNorthBox)
        add(northBoxMain, BorderLayout.NORTH)

    }

    private fun getItemsMap(): MutableMap<String, Set<String>> {
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

    private fun setCentralPart(){
        val pane = JPanel(GridBagLayout())
        pane.border = BorderFactory.createLineBorder(Color.GREEN, 3)
//        pane.layout = GridBagLayout()
        val text = File(filesSet.first()).readText()
        textArea.text = text
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.border = BorderFactory.createLineBorder(Color.BLUE, 2)
        var font = textArea.font
        font = font.deriveFont(22f)
        textArea.font = font
        val c = GridBagConstraints()
        c.fill = GridBagConstraints.BOTH
        c.gridx = 0 //aligned with button 2
//        c.gridwidth = 3 //2 columns wide
        c.weightx = 3.0
        c.weighty = 1.0
        c.gridy = 0 //
        pane.add(JScrollPane(textArea), c)

//        var button = JButton("")
        c.fill = GridBagConstraints.BOTH
        c.gridx = 1
//        c.gridwidth = 1
        c.weightx = 0.5
        c.gridy = 0
        imageHolder.border = BorderFactory.createLineBorder(Color.RED, 2)
//        val filesSet = frame.getFilesSet()
        var itemImage = ImageIcon(filesSet.last())
        var image = itemImage.image
//        val imageHolder = frame.getImageHolder()
        val imageWidth = itemImage.iconWidth
        val newWidth = 500.0
        val ratio = imageWidth / newWidth
        val imageHeight = itemImage.iconHeight / ratio
        var newimg = image.getScaledInstance(newWidth.toInt(), imageHeight.toInt(), Image.SCALE_SMOOTH) // задаем размер
        imageHolder.icon = ImageIcon(newimg)
        imageHolder.horizontalAlignment = JLabel.CENTER
//        imageHolder.icon = itemImage
        imageHolder.preferredSize = Dimension(400, 300)
        pane.add(imageHolder, c)


//        button = JButton("Long-Named Button 4")
////        c.fill = GridBagConstraints.HORIZONTAL
//        c.fill = GridBagConstraints.BOTH
//        c.ipady = 40 //make this component tall
//        c.weightx = 0.0
//        c.gridwidth = 3
//        c.gridx = 0
//        c.gridy = 1
//        pane.add(button, c)
//
//        button = JButton("5")
////        c.fill = GridBagConstraints.HORIZONTAL
//        c.fill = GridBagConstraints.BOTH
//        c.ipady = 0 //reset to default
//        c.weighty = 1.0 //request any extra vertical space
//        c.anchor = GridBagConstraints.PAGE_END //bottom of space
//        c.insets = Insets(10, 0, 0, 0) //top padding
//        c.gridx = 1 //aligned with button 2
//        c.gridwidth = 2 //2 columns wide
//        c.gridy = 2 //third row
//        pane.add(button, c)
        add(pane, BorderLayout.CENTER)
    }
}

private fun createAndShowGUI() {
    val frame = SimpleEx("Simple")
//    frame.isUndecorated = true
    val graphics = GraphicsEnvironment.getLocalGraphicsEnvironment()
    val device = graphics.defaultScreenDevice
    device.setFullScreenWindow(frame) //for full screen
    frame.isVisible = true
//    val filesSet = frame.getFilesSet()
//    var itemImage = ImageIcon(filesSet.last())
//    var image = itemImage.image
//    val imageHolder = frame.getImageHolder()
//    var newimg = image.getScaledInstance(imageHolder.width, imageHolder.height, Image.SCALE_SMOOTH) // задаем размер
////    imageHolder.icon = ImageIcon(newimg)
//    imageHolder.icon = itemImage
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

//класс для отображения заголовка в комбобоксе
internal class MyComboBoxRenderer(private val _title: String) : JLabel(), ListCellRenderer<Any?> {
    override fun getListCellRendererComponent(
        list: JList<*>?, value: Any?,
        index: Int, isSelected: Boolean, hasFocus: Boolean
    ): Component {
        text = if (index == -1 && value == null) _title
        else value.toString()
        return this
    }
}