package project.grouper.gateway

import project.grouper.domain.Member
import project.grouper.domain.Request
import project.grouper.domain.RequestType
import project.grouper.domain.RequestedPair
import project.grouper.driver.CsvDriver
import project.grouper.port.RequestPort

class RequestGateway(private val csvDriver: CsvDriver): RequestPort {
    companion object {
        private val SUPPORTED_REQUESTS = mapOf(
            RequestType.SAME_GROUP to "input/group-request.csv",
            RequestType.BLOCK to "input/block-request.csv"
        )
    }
    override fun getRequest(): Request {
        return SUPPORTED_REQUESTS
            .flatMap { (type, path) -> createRequestedPairsFromRows(csvDriver.readCells(path), type) }
            .let(::Request)
    }

    private fun createRequestedPairsFromRows(rows: List<List<String>>, type: RequestType): List<RequestedPair> {
        return rows.flatMap { row -> this.createRequestedPairsFromRow(row, type) }
    }

    private fun createRequestedPairsFromRow(row: List<String>, type: RequestType): List<RequestedPair> {
        val requester = Member(row.first())
        val requestees = row.drop(1)
        return requestees.map { RequestedPair(requester, Member(it), type) }
    }
}