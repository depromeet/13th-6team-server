package com.depromeet.whatnow.domains.promiseprogress.domain

enum class PromiseProgressType(val kr: String) {
    PREPARING("출발 전"),
    MOVING("가는 중"),
    ARRIVED("도착"),
    LATE("지각"),
    EXPECTED_TIME("예정시간"),
}
