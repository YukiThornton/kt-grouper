package project.grouper.gateway

import project.grouper.domain.GroupCount
import project.grouper.domain.Member
import project.grouper.domain.Members
import project.grouper.domain.Requirement
import project.grouper.driver.CsvDriver
import project.grouper.port.RequirementPort

class RequirementGateway(private val csvDriver: CsvDriver) : RequirementPort {
    override fun getRequirement(): Requirement {
        return Requirement(GroupCount(5), createMembers(csvDriver.readCells("input/requirement.csv")))
    }

    private fun createMembers(csvLines: List<List<String>>): Members {
        val members = csvLines.asSequence()
            .map { it.first() }
            .filter { it.isNotEmpty() }
            .map { Member(it) }
            .toList()
        return Members(members)
    }

}