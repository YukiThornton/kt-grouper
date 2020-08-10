package project.grouper.port

import project.grouper.domain.Request

interface RequestPort {
    fun getRequest(): Request
}