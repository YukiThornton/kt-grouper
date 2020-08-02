package project.grouper.gateway

import project.grouper.domain.GroupCount
import project.grouper.domain.Member
import project.grouper.domain.Members
import project.grouper.domain.Requirement
import project.grouper.driver.CsvDriver
import project.grouper.port.RequirementPort

class RequirementGateway(private val csvDriver: CsvDriver) : RequirementPort {
    override fun getRequirement(): Requirement {
        return Requirement(loadGroupCount(), loadMembers())
    }

    private fun loadGroupCount(): GroupCount {
        val csvLines = csvDriver.readCells("input/group-count.csv")
        return csvLines.first().first().toInt().let(::GroupCount)
    }

    private fun loadMembers(): Members {
        val csvLines = csvDriver.readCells("input/members.csv")
        val members = csvLines.asSequence()
            .map { it.first() }
            .filter { it.isNotEmpty() }
            .map { Member(it) }
            .toList()
        return Members(members)
    }

}