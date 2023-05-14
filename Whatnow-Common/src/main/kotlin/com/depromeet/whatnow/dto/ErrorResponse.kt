package com.depromeet.whatnow.dto

import java.time.LocalDateTime

class ErrorResponse(
    var status: Int? = null,
    var code: String? = null,
    var reason: String? = null,
    var timeStamp: LocalDateTime? = LocalDateTime.now(),
    var path: String? = null,
) {
    companion object {
        fun of(errorReason: ErrorReason?, path: String): ErrorResponse {
            return ErrorResponse(errorReason?.status, errorReason?.code, errorReason?.reason, path = path)
        }
    }
}
