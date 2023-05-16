package com.depromeet.whatnow.domains.user.service

import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.exception.AlreadySignUpUserException
import com.depromeet.whatnow.domains.user.exception.UserNotFoundException
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDomainService(
    val userRepository: UserRepository,
) {

    /**
     * 백엔드 개발용 회원가입 메서드
     * upsert로 동작합니다.
     */
    @Transactional
    fun upsertUser(
        username: String,
        profileImage: String,
        defaultImage: Boolean,
        oauthInfo: OauthInfo,
        oauthId: String,
    ): User {
        return userRepository.findByOauthInfo(oauthInfo) ?: run {
            val newUser = userRepository.save(User(oauthInfo, username, profileImage, defaultImage))
            newUser.registerEvent()
            return newUser
        }
    }

    fun checkUserCanRegister(oauthInfo: OauthInfo): Boolean {
        userRepository.findByOauthInfo(oauthInfo)?. let { return false }
        return true
    }

    fun registerUser(username: String, profileImage: String, defaultImage: Boolean, oauthInfo: OauthInfo, oauthId: String): User {
        userRepository.findByOauthInfo(oauthInfo)?. let { throw AlreadySignUpUserException.EXCEPTION }
        return userRepository.save(User(oauthInfo, username, profileImage, defaultImage))
    }

    @Transactional
    fun loginUser(oauthInfo: OauthInfo): User {
        val user = userRepository.findByOauthInfo(oauthInfo) ?: run { throw UserNotFoundException.EXCEPTION }
        user.login()
        return user
    }

    @Transactional
    fun withDrawUser(currentUserId: Long) {
        val user = userRepository.findByIdOrNull(currentUserId) ?: run { throw UserNotFoundException.EXCEPTION }
        user.withDraw()
    }
}
