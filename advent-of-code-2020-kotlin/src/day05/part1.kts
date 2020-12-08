import java.io.File

data class BoardingPass(val row: Int, val column: Int) {
    fun getId() = row * 8 + column
}

fun parsePass(text: String): BoardingPass {
    val rowSpec = text.substring(0..6)
    val colSpec = text.substring(7..9)

    val row = rowSpec
        .map { if (it == 'B') '1' else '0' }
        .joinToString("")
        .toInt(2)
    val col = colSpec
        .map { if (it == 'R') '1' else '0' }
        .joinToString("")
        .toInt(2)

    return BoardingPass(row, col)
}

val lines = File("part1.txt").readLines()
val passes = lines.map { parsePass(it) }
val max = passes.map { it.getId() }.max()

println(max)

val passesSet = passes.toSet()
for (row in 0..127) {
    for (col in 0..7) {
        val pass = BoardingPass(row, col)
        val seatIsFilled = pass in passesSet
        if (!seatIsFilled) {
            println(pass.toString() + " ID = " + pass.getId().toString())
        }
    }
}
