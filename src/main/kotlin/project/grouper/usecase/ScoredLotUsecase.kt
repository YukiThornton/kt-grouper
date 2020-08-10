package project.grouper.usecase

import project.grouper.domain.Evaluator
import project.grouper.domain.LotGenerator
import project.grouper.port.HistoryPort
import project.grouper.port.LotPort
import project.grouper.port.RequestPort
import project.grouper.port.RequirementPort

class ScoredLotUsecase(
    private val requirementPort: RequirementPort,
    private val requestPort: RequestPort,
    private val historyPort: HistoryPort,
    private val lotPort: LotPort
) {

    fun generate() {
        val requirement = requirementPort.getRequirement()
        val request = requestPort.getRequest()
        val history = historyPort.getHistory()
        val lots = LotGenerator.generateRandomLots(requirement.maxGroupSize(), requirement.members, 100)
        val highestLot = Evaluator.with(request, history).addScore(lots).highest()
        lotPort.saveScoredLot(highestLot)
    }
}