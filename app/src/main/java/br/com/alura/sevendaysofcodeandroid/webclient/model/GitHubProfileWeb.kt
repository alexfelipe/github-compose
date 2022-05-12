package br.com.alura.sevendaysofcodeandroid.webclient.model

import br.com.alura.sevendaysofcodeandroid.model.User
import com.squareup.moshi.Json

class GitHubProfile(
    val login: String?,
    @field:Json(name = "avatar_url")
    val avatarUrl: String?,
    val name: String?,
    val bio: String?,
    @field:Json(name = "public_repos")
    val publicRepo: String?,
    val followers: String?,
    val following: String?
)

fun GitHubProfile.toProfile(): User {
    return User(
        login = this.login ?: "",
        avatar = this.avatarUrl ?: "",
        name = this.name ?: "",
        bio = this.bio ?: "",
        repositories = this.publicRepo ?: "",
        followers = followers ?: "",
        following = following ?: ""
    )
}