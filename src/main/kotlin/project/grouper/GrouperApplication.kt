package project.grouper

import project.grouper.driver.CsvDriver
import project.grouper.gateway.HistoryGateway
import project.grouper.gateway.LotGateway
import project.grouper.gateway.PairingRequestGateway
import project.grouper.gateway.LotRequirementGateway
import project.grouper.usecase.ScoredLotUsecase

fun main(args: Array<String>) {
    val csvDriver = CsvDriver()
    // RandomLotUsecase(RequirementGateway(csvDriver), LotGateway(csvDriver)).generate()
    ScoredLotUsecase(LotRequirementGateway(csvDriver), PairingRequestGateway(csvDriver), HistoryGateway(csvDriver), LotGateway(csvDriver)).generateLotsAndSaveHighest()
}
