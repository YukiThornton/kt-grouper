package project.grouper.domain

data class RequestedPair(val requester: Member, val requestee: Member, val requestType: RequestType)