import java.io.File
import java.util.Scanner

object NewRomanFont {
    val NEW_ROMAN_LETTERS = mutableListOf<Letter>()
    var NUMBER_LINES = 0

    fun addLetter(letter: Letter) {
        NEW_ROMAN_LETTERS.add(letter)
    }

    fun getLowerCase(c: Char) : Letter {
        return NEW_ROMAN_LETTERS[c.toInt() - 'a'.toInt()]
    }

    fun getUpperCase(c: Char) : Letter {
        return NEW_ROMAN_LETTERS[c.toInt() - 'A'.toInt() + 26]
    }

    fun getWhiteSpace() : Letter {
        return Letter(' ', 10, MutableList<String>(10) { " ".repeat(10) })
    }
}

object MediumFont {
    val MEDIUM_LETTERS = mutableListOf<Letter>()
    var NUMBER_LINES = 0

    fun addLetter(letter: Letter) {
        MEDIUM_LETTERS.add(letter)
    }

    fun getLowerCase(c: Char) : Letter {
        return MEDIUM_LETTERS[c.toInt() - 'a'.toInt()]
    }

    fun getUpperCase(c: Char) : Letter {
        return MEDIUM_LETTERS[c.toInt() - 'A'.toInt() + 26]
    }

    fun getWhiteSpace() : Letter {
        return Letter(' ', 5, MutableList<String>(3) { " ".repeat(5) })
    }
}


class Letter(val letter: Char, val length: Int,  val strings: MutableList<String>) {
}

fun getLettersFromFile(filePath: String, type: String) {
    val file_scanner = Scanner(File(filePath))
    val numberOfLines = file_scanner.nextInt()
    val numberOfSymbols = file_scanner.nextInt()

    repeat(numberOfSymbols) {
        var letter = file_scanner.next().first()
        var length = file_scanner.nextInt()
        file_scanner.nextLine()
        var letterStrings = mutableListOf<String>()
        repeat(numberOfLines) {
            letterStrings.add(file_scanner.nextLine())
        }
        if (type == "New Roman") {
            NewRomanFont.addLetter(Letter(letter, length, letterStrings))
            NewRomanFont.NUMBER_LINES = numberOfLines
        } else if (type == "Medium") {
            MediumFont.addLetter(Letter(letter, length, letterStrings))
            MediumFont.NUMBER_LINES = numberOfLines
        }
    }
}

fun constructName(name: String) : MutableList<String> {
    val strings = MutableList(NewRomanFont.NUMBER_LINES) { "" }
    for (j in name.indices) {
        var letter: Letter
        if (name[j].isWhitespace()) {
            letter = NewRomanFont.getWhiteSpace()
        }
        else if (name[j].isUpperCase()) {
            letter = NewRomanFont.getUpperCase(name[j])
        } else {
            letter = NewRomanFont.getLowerCase(name[j])
        }
        for (i in letter.strings.indices) {
                strings[i] += letter.strings[i]
            }
    }
    return strings
}

fun lengthOfCustomString(str: MutableList<String>) : Int {
    return str[0].length
}

fun constructStatus(status: String) : MutableList<String> {
    val strings = MutableList(MediumFont.NUMBER_LINES) { "" }
    for (j in status.indices) {
        var letter: Letter
        if (status[j].isWhitespace()) {
            letter = MediumFont.getWhiteSpace()
        }
        else if (status[j].isUpperCase()) {
            letter = MediumFont.getUpperCase(status[j])
        } else {
            letter = MediumFont.getLowerCase(status[j])
        }
        for (i in letter.strings.indices) {
            strings[i] += letter.strings[i]
        }
    }
    return strings
}

fun leftWhiteSpacesForName(customName: MutableList<String>, customStatus: MutableList<String>) : String {
    val customNameLength = lengthOfCustomString(customName)
    val customStatusLength = lengthOfCustomString(customStatus)
    if (customStatusLength <= customNameLength) {
        return " ".repeat(0)
    } else {
        return " ".repeat((customStatusLength - customNameLength) / 2)
    }
}

fun rightWhiteSpacesForName(customName: MutableList<String>, customStatus: MutableList<String>) : String {
    val customNameLength = lengthOfCustomString(customName)
    val customStatusLength = lengthOfCustomString(customStatus)
    if (customStatusLength <= customNameLength) {
        return " ".repeat(0)
    } else {
        return " ".repeat((customStatusLength - customNameLength) / 2 + (customStatusLength - customNameLength) % 2)
    }
}

fun leftWhiteSpacesForStatus(customName: MutableList<String>, customStatus: MutableList<String>) : String {
    val customNameLength = lengthOfCustomString(customName)
    val customStatusLength = lengthOfCustomString(customStatus)
    if (customStatusLength > customNameLength) {
        return " ".repeat(0)
    } else {
        return " ".repeat((customNameLength - customStatusLength) / 2)
    }
}

fun rightWhiteSpacesForStatus(customName: MutableList<String>, customStatus: MutableList<String>) : String {
    val customNameLength = lengthOfCustomString(customName)
    val customStatusLength = lengthOfCustomString(customStatus)
    if (customStatusLength > customNameLength) {
        return " ".repeat(0)
    } else {
        return " ".repeat((customNameLength - customStatusLength) / 2 + (customNameLength - customStatusLength) % 2)
    }
}

fun alignCustomName(customName: MutableList<String>, customStatus: MutableList<String>) : MutableList<String> {
    val leftSpaces = leftWhiteSpacesForName(customName, customStatus)
    val rightSpaces = rightWhiteSpacesForName(customName, customStatus)
    val newCustomName = MutableList(NewRomanFont.NUMBER_LINES) { "" }

    for (i in customName.indices) {
        newCustomName[i] = leftSpaces + customName[i] + rightSpaces
    }

    return newCustomName
}

fun alignCustomStatus(customName: MutableList<String>, customStatus: MutableList<String>) : MutableList<String> {
    val leftSpaces = leftWhiteSpacesForStatus(customName, customStatus)
    val rightSpaces = rightWhiteSpacesForStatus(customName, customStatus)
    val newCustomStatus = MutableList(MediumFont.NUMBER_LINES) { "" }

    for (i in customStatus.indices) {
        newCustomStatus[i] = leftSpaces + customStatus[i] + rightSpaces
    }

    return newCustomStatus
}

fun addBorder(customString: MutableList<String>) : MutableList<String> {
    val newCustomString = MutableList(customString.size) { "" }

    for (i in customString.indices) {
        newCustomString[i] = "88  " + customString[i] + "  88"
    }
    return newCustomString
}

fun constructSolidBorder(length: Int) : String {
    return "8".repeat(length)
}

fun printGrid(customName: MutableList<String>, customStatus: MutableList<String>){
    val length = lengthOfCustomString(customName)
    val solidBorder = constructSolidBorder(length)
    println(solidBorder)
    for (s in customName) {
        println(s)
    }
    for (s in customStatus) {
        println(s)
    }
    println(solidBorder)
}

fun main() {
    val scanner = Scanner(System.`in`)
    print("Enter name and surname: ")
    val name = scanner.nextLine()
    print("Enter person's status: ")
    val status = scanner.nextLine()

    getLettersFromFile("/Users/eek/IdeaProjects/ASCII Text Signature/ASCII Text Signature/task/src/signature/roman.txt", "New Roman")
    getLettersFromFile("/Users/eek/IdeaProjects/ASCII Text Signature/ASCII Text Signature/task/src/signature/medium.txt", "Medium")

    var nameInNewRoman = constructName(name)
    var statusInMedium = constructStatus(status)

    nameInNewRoman = alignCustomName(nameInNewRoman, statusInMedium)
    statusInMedium = alignCustomStatus(nameInNewRoman, statusInMedium)

    nameInNewRoman = addBorder(nameInNewRoman)
    statusInMedium = addBorder(statusInMedium)

    printGrid(nameInNewRoman, statusInMedium)

}

