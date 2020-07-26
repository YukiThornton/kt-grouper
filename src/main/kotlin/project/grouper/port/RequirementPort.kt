package project.grouper.port

import project.grouper.domain.Requirement

interface RequirementPort {
    fun getRequirement(): Requirement
}