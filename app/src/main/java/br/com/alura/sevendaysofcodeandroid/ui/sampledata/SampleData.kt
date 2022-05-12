package br.com.alura.sevendaysofcodeandroid.ui.sampledata

import br.com.alura.sevendaysofcodeandroid.model.GitHubRepo
import br.com.alura.sevendaysofcodeandroid.model.User

val sampleUsers = listOf(
    User(
        login = "alexfelipe",
        avatar = "https://avatars.githubusercontent.com/u/8989346?v=4",
        name = "Alex Felipe",
        bio = "Instructor and Software Developer at @alura-cursos",
        repositories = "312",
        followers = "872",
        following = "213"
    ),
    User(
        login = "peas",
        avatar = "https://avatars.githubusercontent.com/u/71636?v=4",
        name = "Paulo Silveira",
        bio = "CEO do Grupo Alura de ensino e inovação em tecnologia. Senior Email Responder. Podcaster nas horas vagas.",
        repositories = "931",
        followers = "2123",
        following = "464"
    )
)

val sampleRepositories = listOf<GitHubRepo>(
    GitHubRepo(
        name = "seven-days-of-code-kotlin",
        description = "#7DaysOfCode Java implementation, but in Kotlin \uD83D\uDE4A",
        updatedAt = "2020-05-14T11:40:30Z",
        stargazersCount = "7",
        forksCount = "2",
    ),
    GitHubRepo(
        name = "ceep-compose",
        description = "Sample project to practice the Jetpack Compose Apps",
        updatedAt = "2022-03-09T19:23:01Z",
        stargazersCount = "1",
    ),
    GitHubRepo(
        name = "orgs-room-2",
        updatedAt = "2021-11-25T19:54:53Z",
        stargazersCount = "0",
        forksCount = "0"
    ),
    GitHubRepo(
        name = "alexfelipe",
        description = "My personal informations",
        updatedAt = "2021-10-12T10:55:11Z",
        forksCount = "5",
    ),
)