package br.com.alura.sevendaysofcodeandroid.webclient.service

import br.com.alura.sevendaysofcodeandroid.webclient.model.GitHubProfile
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("users/{user}")
    suspend fun findProfileBy(@Path("user") user: String): GitHubProfile

    @GET("users/{user}/following")
    suspend fun findFollowingBy(@Path("user") user: String): List<GitHubProfile>

    @GET("users/{user}/followers")
    suspend fun findFollowersBy(@Path("user") user: String): List<GitHubProfile>

}
