package com.depromeet.whatnow.api.image.controller

import com.depromeet.whatnow.api.image.usecase.GetPresignedUrlUseCase
import com.depromeet.whatnow.config.s3.ImageFileExtension
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ImageController::class)
@ContextConfiguration(classes = [ImageController::class])
@AutoConfigureMockMvc(addFilters = false)
class ImageControllerTest {
    @MockBean
    lateinit var getPresignedUrlUseCase: GetPresignedUrlUseCase

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `presignedUrl 요청에 성공하면 200을 응답한다`() {
        // given
        val promiseId = 1L
        val fileExtension = ImageFileExtension.JPEG.name

        // when, then
        mockMvc.perform(
            get("/v1/promises/{promiseId}/images", promiseId)
                .param("fileExtension", fileExtension),
        )
            .andExpect(status().isOk)
            .andDo { print(it) }
    }
}
