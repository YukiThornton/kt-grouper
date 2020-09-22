package project.grouper.port

import project.grouper.domain.LotRequirement

interface LotRequirementPort {
    fun getLotRequirement(): LotRequirement
}