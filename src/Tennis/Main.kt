package Tennis

import com.opencsv.CSVReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.ArrayList
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import org.apache.commons.collections4.MultiValuedMap
import java.io.BufferedReader



// Download rankings .csv from:
// https://datahub.io/sports-data/atp-world-tour-tennis-data
// include that in the Data package.

fun main(args : Array<String>) {
    val players = readPlayersFromCSV()
    players.forEach{ it.forEach { print("$it | ")}
                    println()}

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