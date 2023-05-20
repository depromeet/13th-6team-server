package com.depromeet.whatnow.api.promiseprogress.usecase

import com.depromeet.whatnow.api.promiseprogress.dto.response.PromiseProgressDto
import com.depromeet.whatnow.api.promiseprogress.dto.response.PromiseProgressGroupElement
import com.depromeet.whatnow.domains.promiseprogress.adapter.PromiseProgressAdapter
import org.springframework.stereotype.Component

@Component
class PromiseProgressReadUseCase(
    val promiseProgressAdapter: PromiseProgressAdapter,
) {

    fun execute(): List<PromiseProgressGroupElement> {
        return promiseProgressAdapter.findAll().map {
                p ->
            PromiseProgressDto.from(p)
        }.groupBy {
                p ->
            p.group
        }.map {
                (k, value) ->
            PromiseProgressGroupElement(k, value)
        }
    }
}