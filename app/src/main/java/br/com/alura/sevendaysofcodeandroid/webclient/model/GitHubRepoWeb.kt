package br.com.alura.sevendaysofcodeandroid.webclient.model

import br.com.alura.sevendaysofcodeandroid.model.GitHubRepo
import com.squareup.moshi.Json

class GitHubRepoWeb(
    val name: String,
    val description: String?,
    @field:Json(name = "updated_at")
    val updatedAt: String,
    @field:Json(name = "stargazers_count")
    val stargazersCount: String? = null,
    @field:Json(name = "forks_count")
    val forksCount: String? = null
)

fun GitHubRepoWeb.toGitHubRepo() = GitHubRepo(
    name = name,
    description = description ?: "",
    updatedAt = updatedAt,
    stargazersCount = stargazersCount ?: "",
    forksCount = forksCount ?: ""
)