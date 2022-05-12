package br.com.alura.sevendaysofcodeandroid.webclient.service

import br.com.alura.sevendaysofcodeandroid.webclient.model.GitHubRepoWeb
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubRepoService {

    @GET("users/{user}/repos")
    suspend fun findRepositoriesBy(@Path("user") user: String):
            List<GitHubRepoWeb>

}
