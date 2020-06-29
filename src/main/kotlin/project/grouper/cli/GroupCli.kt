package project.grouper.cli

import project.grouper.domain.*
import project.grouper.usecase.HighestScoredGroupLot
import project.grouper.util.readAsLines
import project.grouper.util.writeTo
import java.text.SimpleDateFormat
import java.util.*

class GroupCli(private val usecase: HighestScoredGroupLot) {
    fun generateGroupLot(groupCount: Int, groupRequestPath: String) {
        val userInput = loadUserInput(groupCount, groupRequestPath)
        val scoredGroupLot = usecase.generate(userInput)
        writeGroupLot(scoredGroupLot)
    }

    fun loadUserInput(groupCount: Int, groupRequestPath: String): UserInput =
        UserInput(Requirement(GroupCount(groupCount), createMembers(readAsLines(groupRequestPath))))

    private fun createMembers(csvLines: List<List<String>>): Members {
        val members = csvLines.asSequence()
            .map { it.first() }
            .filter { it.isNotEmpty() }
            .map { Member(it) }
            .toList()
        return Members(members)
    }

    fun writeGroupLot(scoredGroupLot: ScoredGroupLot) {
        println(scoredGroupLot)
        val content = scoredGroupLot.groups.map { group ->
            group.members.values.map { member -> member.name }
        }
        writeTo(generateResultFileName(), content)
    }

    fun generateResultFileName(): String = buildString {
        append("groups-")
        append(SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date()))
        append(".csv")
    }
}
