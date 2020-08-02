package project.grouper

import project.grouper.driver.CsvDriver
import project.grouper.gateway.HistoryGateway
import project.grouper.gateway.LotGateway
import project.grouper.gateway.RequirementGateway
import project.grouper.usecase.LotUsecase

fun main(args: Array<String>) {
    val csvDriver = CsvDriver()
    LotUsecase(RequirementGateway(csvDriver), HistoryGateway(csvDriver), LotGateway(csvDriver)).generateLotWithHighestScore()
}
