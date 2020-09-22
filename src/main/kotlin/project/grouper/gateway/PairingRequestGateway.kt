package project.grouper.gateway

import project.grouper.domain.Member
import project.grouper.domain.PairingRequests
import project.grouper.domain.PairingRequestType
import project.grouper.domain.PairingRequest
import project.grouper.driver.CsvDriver
import project.grouper.port.PairingRequestPort

class PairingRequestGateway(private val csvDriver: CsvDriver): PairingRequestPort {
    companion object {
        private val SUPPORTED_REQUESTS = mapOf(
            PairingRequestType.SAME_GROUP to "input/group-request.csv",
            PairingRequestType.BLOCK to "input/block-request.csv"
        )
    }
    override fun getPairingRequests(): PairingRequests {
        return SUPPORTED_REQUESTS
            .flatMap { (type, path) -> createPairingRequestsFromRows(csvDriver.readCells(path), type) }
            .let(::PairingRequests)
    }

    private fun createPairingRequestsFromRows(rows: List<List<String>>, type: PairingRequestType): List<PairingRequest> {
        return rows.flatMap { row -> this.createPairingRequestsFromRow(row, type) }
    }

    private fun createPairingRequestsFromRow(row: List<String>, type: PairingRequestType): List<PairingRequest> {
        val requester = Member(row.first())
        val requestees = row.drop(1)
        return requestees.map { PairingRequest(requester, Member(it), type) }
    }
}