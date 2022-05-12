package br.com.alura.sevendaysofcodeandroid.repository

import br.com.alura.sevendaysofcodeandroid.model.User
import br.com.alura.sevendaysofcodeandroid.webclient.model.toProfile
import br.com.alura.sevendaysofcodeandroid.webclient.service.GitHubService
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val service: GitHubService
) {

    suspend fun findUserById(id: String): User? {
        return try {
            service.findProfileBy(id).toProfile()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun findFollowingUsers(user: User): List<User> {
        return service.findFollowingBy(user.login).map {
            it.toProfile()
        }
    }

    suspend fun findFollowersUsers(user: String): List<User> {
        return service.findFollowersBy(user).map {
            it.toProfile()
        }
    }

}