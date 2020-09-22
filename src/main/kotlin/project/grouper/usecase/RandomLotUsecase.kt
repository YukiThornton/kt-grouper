package project.grouper.usecase

import project.grouper.port.LotPort
import project.grouper.port.LotRequirementPort

class RandomLotUsecase(private val lotRequirementPort: LotRequirementPort, private val lotPort: LotPort) {

    fun generateAndSave() {
        val lot = lotRequirementPort.getLotRequirement().generateRandomLot()
        lotPort.saveLot(lot)
    }
}