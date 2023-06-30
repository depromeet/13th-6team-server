package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.domain.PromiseType
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.WAIT
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.domains.user.domain.FcmNotificationVo
import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.OauthProvider.KAKAO
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class PromiseReadUseCaseTest {

    @Mock
    private lateinit var promiseUserAdaptor: PromiseUserAdaptor

    @Mock
    private lateinit var promiseAdaptor: PromiseAdaptor

    @Mock
    private lateinit var userAdapter: UserAdapter

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var promiseReadUseCase: PromiseReadUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        promiseReadUseCase = PromiseReadUseCase(
            userRepository = userRepository,
            promiseUserAdaptor = promiseUserAdaptor,
            promiseAdaptor = promiseAdaptor,
            userAdapter = userAdapter,
        )
        val securityContext = SecurityContextHolder.createEmptyContext()
        val authentication = UsernamePasswordAuthenticationToken("1", null, setOf(SimpleGrantedAuthority("ROLE_USER")))
        securityContext.authentication = authentication
        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    fun testFindPromiseDetailByEndTime() {
        // Given
        val userId: Long = 1
        val promiseTime1 = LocalDateTime.now()
        val promiseTime2 = LocalDateTime.now()
        val promiseUsers = listOf(
            PromiseUser(userId = 1, promiseId = 1, promiseUserType = WAIT),
//            PromiseUser(userId = 2, promiseId = 1, promiseUserType = LATE),
            PromiseUser(userId = 1, promiseId = 2, promiseUserType = WAIT),
        )
        val promises = listOf(
            Promise(id = 1, title = "Promise 1", endTime = promiseTime1, mainUserId = 1),
            Promise(id = 2, title = "Promise 2", endTime = promiseTime2, mainUserId = 2L),
        )
        val users = listOf(
            User(
                oauthInfo = OauthInfo("1234", KAKAO),
                nickname = "서현",
                profileImg = "profile1.jpg",
                isDefaultImg = true,
                fcmNotification = FcmNotificationVo("1234", true),
                id = 1,
            ),
            User(
                oauthInfo = OauthInfo("2345", KAKAO),
                nickname = "찬진",
                profileImg = "profile2.jpg",
                isDefaultImg = false,
                fcmNotification = FcmNotificationVo("2345", true),
                id = 2,
            ),
        )

        Mockito.`when`(promiseUserAdaptor.findByUserId(userId)).thenReturn(promiseUsers)
        Mockito.`when`(promiseAdaptor.queryPromises(listOf(1, 2))).thenReturn(promises)
        Mockito.`when`(userAdapter.queryUsers(listOf(1))).thenReturn(users)

        // When
        val result = promiseReadUseCase.findPromiseDetailByStatus(PromiseType.BEFORE)

        // Then
        Assertions.assertEquals(2, result.size)

        Assertions.assertEquals("Promise 1", result[0].title)
        Assertions.assertEquals(promiseTime1, result[0].date)
        Assertions.assertEquals(1, result[0].promiseUsers.size)

        Assertions.assertEquals("Promise 2", result[1].title)
        Assertions.assertEquals(promiseTime2, result[1].date)
        Assertions.assertEquals(1, result[1].promiseUsers.size)
    }
}