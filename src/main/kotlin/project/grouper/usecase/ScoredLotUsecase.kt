package project.grouper.usecase

import project.grouper.domain.Evaluator
import project.grouper.port.HistoryPort
import project.grouper.port.LotPort
import project.grouper.port.LotRequirementPort
import project.grouper.port.PairingRequestPort

class ScoredLotUsecase(
    private val lotRequirementPort: LotRequirementPort,
    private val pairingRequestPort: PairingRequestPort,
    private val historyPort: HistoryPort,
    private val lotPort: LotPort
) {

    fun generateLotsAndSaveHighest() {
        val lots = lotRequirementPort.getLotRequirement().generateRandomLots(100)
        val evaluator = Evaluator.with(pairingRequestPort.getPairingRequests(), historyPort.getHistory())
        val highestLot = evaluator.attachScore(lots).highest()
        lotPort.saveScoredLot(highestLot)
    }
}