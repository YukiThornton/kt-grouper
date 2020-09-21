package project.grouper.domain

import java.lang.RuntimeException

class InvalidLotRequirement(override val message: String): RuntimeException(message)