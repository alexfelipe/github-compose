package br.com.alura.sevendaysofcodeandroid.repository

import br.com.alura.sevendaysofcodeandroid.model.GitHubRepo
import br.com.alura.sevendaysofcodeandroid.model.User
import br.com.alura.sevendaysofcodeandroid.webclient.model.GitHubRepoWeb
import br.com.alura.sevendaysofcodeandroid.webclient.model.toGitHubRepo
import br.com.alura.sevendaysofcodeandroid.webclient.service.GitHubRepoService
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val service: GitHubRepoService
) {

    suspend fun findRepositoriesBy(user: User): List<GitHubRepo> {
        return service.findRepositoriesBy(user.login)
            .map {
                it.toGitHubRepo()
            }
    }
}
