package com.depromeet.whatnow.api.config

import com.depromeet.whatnow.api.dto.KakaoKauthErrorResponse
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.WhatNowDynamicException
import feign.Response
import feign.codec.ErrorDecoder

class KauthErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        val body: KakaoKauthErrorResponse = KakaoKauthErrorResponse.from(response)
        try {
            val kakaoKauthErrorCode = KakaoKauthErrorCode.valueOf(body.errorCode)
            val errorReason: ErrorReason = kakaoKauthErrorCode.errorReason
            throw WhatNowDynamicException(
                errorReason.status,
                errorReason.code,
                errorReason.reason,
            )
        } catch (e: IllegalArgumentException) {
            val koeInvalidRequest = KakaoKauthErrorCode.KOE_INVALID_REQUEST
            val errorReason: ErrorReason = koeInvalidRequest.errorReason
            throw WhatNowDynamicException(
                errorReason.status,
                errorReason.code,
                errorReason.reason,
            )
        }
    }
}
