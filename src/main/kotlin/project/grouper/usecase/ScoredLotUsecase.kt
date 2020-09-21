package project.grouper.usecase

import project.grouper.domain.Evaluator
import project.grouper.port.HistoryPort
import project.grouper.port.LotPort
import project.grouper.port.LotRequirementPort
import project.grouper.port.RequestPort

class ScoredLotUsecase(
    private val lotRequirementPort: LotRequirementPort,
    private val requestPort: RequestPort,
    private val historyPort: HistoryPort,
    private val lotPort: LotPort
) {

    fun generateLotsAndSaveHighest() {
        val lots = lotRequirementPort.getRequirement().generateRandomLots(100)
        val request = requestPort.getRequest()
        val history = historyPort.getHistory()
        val highestLot = Evaluator.with(request, history).addScore(lots).highest()
        lotPort.saveScoredLot(highestLot)
    }
}