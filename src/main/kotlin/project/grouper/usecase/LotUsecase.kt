package project.grouper.usecase

import project.grouper.domain.LotGenerator
import project.grouper.port.HistoryPort
import project.grouper.port.LotPort
import project.grouper.port.RequirementPort

class LotUsecase(private val requirementPort: RequirementPort, private val historyPort: HistoryPort, private val lotPort: LotPort) {

    fun generateRandomLot() {
        val requirement = requirementPort.getRequirement()
        val lot = LotGenerator.generateRandomLot(requirement.maxGroupSize(), requirement.members)
        lotPort.saveLot(lot)
    }

    fun generateLotWithHighestScore() {
        val requirement = requirementPort.getRequirement()
        val history = historyPort.getHistory()
        val lots = LotGenerator.generateRandomLots(requirement.maxGroupSize(), requirement.members, 100)
        val highestLot = history.score(lots).highest()
        lotPort.saveScoredLot(highestLot)
    }

}