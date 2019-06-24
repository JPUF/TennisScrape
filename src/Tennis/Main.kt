package Tennis

import com.opencsv.CSVReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

// Download rankings .csv from:
// https://datahub.io/sports-data/atp-world-tour-tennis-data
// include that in the Data package.

fun main(args : Array<String>) {
    readPlayersFromCSV()
}

fun readPlayersFromCSV(){
    val filePath = Paths.get("C:\\Users\\psyjb12.AD.000\\IdeaProjects\\TennisScrape\\src\\Tennis\\Data\\rankings_1973-2017_csv.csv")
    val reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)
    val csvReader = CSVReader(reader)

    var line = csvReader.readNext()
    val seenYears : List<String>
    var numberOfSeenYears = -1

    do {
        line.forEach { print("$it | ") }
        print("\n\n")
        line = csvReader.readNext()
    } while (line != null)

}