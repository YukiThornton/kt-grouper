package project.grouper.gateway

import project.grouper.domain.Member
import project.grouper.domain.Request
import project.grouper.domain.RequestType
import project.grouper.domain.RequestedPair
import project.grouper.driver.CsvDriver
import project.grouper.port.RequestPort

class RequestGateway(private val csvDriver: CsvDriver): RequestPort {
    override fun getRequest(): Request {
        val rows = csvDriver.readCells("input/group-request.csv")
        return createRequestedPairsFromRows(rows).let(::Request)
    }

    private fun createRequestedPairsFromRows(rows: List<List<String>>): List<RequestedPair> {
        return rows.flatMap(this::createRequestedPairsFromRow)
    }

    private fun createRequestedPairsFromRow(row: List<String>): List<RequestedPair> {
        val requester = Member(row.first())
        val requestees = row.drop(1)
        return requestees.map { RequestedPair(requester, Member(it), RequestType.SAME_GROUP) }
    }
}