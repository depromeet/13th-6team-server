package com.depromeet.whatnow.api.example.controller

import com.depromeet.whatnow.annotation.ApiErrorCodeExample
import com.depromeet.whatnow.api.config.KakaoKauthErrorCode
import com.depromeet.whatnow.domains.user.exception.UserErrorCode
import com.depromeet.whatnow.exception.GlobalErrorCode
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/example")
@Tag(name = "X. [예시]")
class ExampleController() {

    @GetMapping("/global")
    @Operation(summary = "글로벌 ( 인증 , aop, 서버 내부 오류등)  관련 에러 코드 나열")
    @ApiErrorCodeExample(GlobalErrorCode::class)
    fun globalErrorcode() {}

    @GetMapping("/user")
    @Operation(summary = "유저 도메인 관련 에러 코드 나열")
    @ApiErrorCodeExample(UserErrorCode::class)
    fun userErrorCode() {}

    @GetMapping("/kakao")
    @Operation(summary = "카카오 auth 관련 에러코드 나열")
    @ApiErrorCodeExample(KakaoKauthErrorCode::class)
    fun kakaoErrorCode() {}
}
