package project.grouper.usecase

import project.grouper.domain.LotGenerator
import project.grouper.port.LotPort
import project.grouper.port.RequirementPort

class LotUsecase(private val requirementPort: RequirementPort, private val lotPort: LotPort) {

    fun generateRandomLot() {
        val requirement = requirementPort.getRequirement()
        val lot = LotGenerator.generateRandomLot(requirement.maxGroupSize(), requirement.members)
        lotPort.saveLot(lot)
    }

}