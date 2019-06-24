package Tennis

import com.opencsv.CSVReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.ArrayList
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import org.apache.commons.collections4.MultiValuedMap
import org.jsoup.Jsoup
import java.io.BufferedReader



// Download rankings .csv from:
// https://datahub.io/sports-data/atp-world-tour-tennis-data
// include that in the Data package.

fun main(args : Array<String>) {
    val playerYearPairs = readPlayersFromCSV()
    playerYearPairs.forEach{ getAge(it[1]!!, it[0]!!.toInt())}

}

fun readPlayersFromCSV(): ArrayList<Array<String?>> {
    val path = Paths.get("C:\\Users\\psyjb12.AD.000\\IdeaProjects\\TennisScrape\\src\\Tennis\\Data\\rankings_1973-2017_csv.csv")
    val reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)
    val csvReader = CSVReader(reader)
    var line = csvReader.readNext()

    val seenYears = ArrayList<String>()

    var yearsSeen = -1
    val playersSeenPerYear = IntArray(45)
    val playersPerYear = ArrayListValuedHashMap<String, String>()

    line = csvReader.readNext()
    do {
        val rankString = line[4].replace("T", "")
        val rank = Integer.parseInt(rankString)

        if (!seenYears.contains(line[1])) {
            seenYears.add(line[1])
            yearsSeen++
        }

        if (rank <= 50 && playersSeenPerYear[yearsSeen] < 50) {
            playersSeenPerYear[yearsSeen]++
            playersPerYear.put(line[1], line[11])
        }
        line = csvReader.readNext()
    } while (line != null)

    //1981 is borked.

    val playerPairs = ArrayList<Array<String?>>()

    for ((key, value) in playersPerYear.entries()) {
        val player = arrayOfNulls<String>(2)
        player[0] = key
        player[1] = value
        playerPairs.add(player)
    }

    reader.close()
    csvReader.close()

    return playerPairs
}

fun getAge(playerStub : String, year : Int) : Int {
    val webPage = "https://www.atptour.com$playerStub"
    val html = Jsoup.connect(webPage).get().html()
    val document = Jsoup.parse(html)

    val elements = document.select(".table-birthday")
    var dateOfBirth = elements[0].ownText()
    var date = dateOfBirth.subSequence(1,5).toString().toInt()
    println("ATP Year: $year || Age: ${year - date}")

    return 1
}