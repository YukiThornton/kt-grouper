package project.grouper.gateway

import project.grouper.domain.Group
import project.grouper.domain.History
import project.grouper.domain.Member
import project.grouper.domain.Members
import project.grouper.driver.CsvDriver
import project.grouper.port.HistoryPort

class HistoryGateway(private val csvDriver: CsvDriver) : HistoryPort {
    companion object {
        const val FILE_ROOT = "input/history"
    }

    override fun getHistory(): History {
        val filePaths = csvDriver.findCsvFileNames(FILE_ROOT).map { "$FILE_ROOT/$it" }
        return filePaths.map(::getGroupsFrom).flatten().let(::History)
    }

    private fun getGroupsFrom(filePath: String): List<Group> {
        return csvDriver.readCells(filePath).toGroups()
    }

    private fun List<List<String>>.toGroups(): List<Group> {
        return this.asSequence()
            .filter { it.isNotEmpty() }
            .map { it.toGroup() }
            .toList()
    }

    private fun List<String>.toGroup(): Group {
        val members = this.filter(String::isNotBlank).map(::Member)
        return Group(Members(members))
    }
}