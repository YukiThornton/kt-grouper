package project.grouper.gateway

import project.grouper.domain.Lot
import project.grouper.driver.CsvDriver
import project.grouper.port.LotPort
import java.text.SimpleDateFormat
import java.util.*

class LotGateway(private val csvDriver: CsvDriver): LotPort {
    override fun saveLot(lot: Lot) {
        val csvContent = lot.list.map { group ->
            group.members.list.map { member -> member.name }
        }
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