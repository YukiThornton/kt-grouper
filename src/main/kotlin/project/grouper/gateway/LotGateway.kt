package project.grouper.gateway

import project.grouper.domain.*
import project.grouper.driver.CsvDriver
import project.grouper.port.LotPort
import java.text.SimpleDateFormat
import java.util.*

class LotGateway(private val csvDriver: CsvDriver): LotPort {
    override fun saveLot(lot: Lot) {
        val csvContent = lot.toCsvRows()
        saveToNewCsvFile(csvContent)
    }

    override fun saveScoredLot(scoredLot: ScoredLot) {
        val csvContent = scoredLot.toCsvRows()
        saveToNewCsvFile(csvContent)
    }

    private fun Lot.toCsvRows(): List<List<String>> {
        return map { it.toCsvRow()}
    }

    private fun ScoredLot.toCsvRows(): List<List<String>> {
        return map { listOf(it.score.toCsvCell()) + it.group.toCsvRow() }
    }

    private fun Score.toCsvCell(): String {
        return "Score=${this.value}"
    }

    private fun Group.toCsvRow(): List<String> {
        return members.map(Member::name)
    }

    private fun saveToNewCsvFile(csvContent: List<List<String>>) {
        val filePath = createOutputPath(generateUniqueLotFileName())
        csvDriver.writeCells(filePath, csvContent)
    }

    fun generateUniqueLotFileName(): String {
        return buildString {
            append("lot-")
            append(SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date()))
            append(".csv")
        }
    }

    private fun createOutputPath(fileName: String): String {
        return buildString {
            append("output/")
            append(fileName)
        }
    }
}