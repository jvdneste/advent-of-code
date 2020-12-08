package day04

import java.io.File

data class Record(val key: String, val value: String)

data class Passport(val records: List<Record>)

fun readRecord(line: String): Record {
    val segments = line.split(":")
    return Record(segments[0], segments[1])
}

fun readPassport(text: String): Passport {
    val fieldLines = text.split("\n")
        .flatMap { it.split(" ") }
        .filter { it.trim().isNotEmpty() }
        .map { readRecord(it) }
    return Passport(fieldLines.toList())
}

fun readPassports(): List<Passport> {
    val input = File("part1.txt").readText()
    val passportStrings = input.split("\n\n")
    return passportStrings.map { readPassport(it) }
}

fun validateBirthYear(value: String): Boolean = value.toIntOrNull() in 1920..2002
fun validateIssueYear(value: String): Boolean = value.toIntOrNull() in 2010..2020
fun validateExpirationYear(value: String): Boolean = value.toIntOrNull() in 2020..2030

fun validateHeight(value: String): Boolean {
    val type = value.substring(value.length - 2)
    val heightStr = value.substring(0..value.length - 3)
    val height = heightStr.toIntOrNull()
    return when (type) {
        "in" -> height in 59..76
        "cm" -> height in 150..193
        else -> false
    }
}

fun validateHairColor(value: String): Boolean {
    if (value.length != 7) {
        return false
    }
    if (value[0] != '#') {
        return false
    }
    return value.substring(1..6).all { it in '0'..'9' || it in 'a'..'f' }
}

val validEyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
fun validateEyeColor(value: String): Boolean = value in validEyeColors

fun validatePassportId(value: String): Boolean =
    value.length == 9 && value.all { it in '0'..'9' }

fun validateRecord(record: Record): Boolean =
    when (record.key) {
        "byr" -> validateBirthYear(record.value)
        "iyr" -> validateIssueYear(record.value)
        "eyr" -> validateExpirationYear(record.value)
        "hgt" -> validateHeight(record.value)
        "hcl" -> validateHairColor(record.value)
        "ecl" -> validateEyeColor(record.value)
        "pid" -> validatePassportId(record.value)
        "cid" -> true
        else -> false
    }

fun validateRecords(passport: Passport): Boolean = passport.records.all { validateRecord(it) }

val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
fun isValid(passport: Passport): Boolean {
    val fields = passport.records
        .map { it.key }
        .toSet()
    val hasAll = requiredFields
        .all { it in fields }
    return hasAll && validateRecords(passport)
}

val passports = readPassports()
val valid = passports.filter { isValid(it) }.count()
println(valid)
