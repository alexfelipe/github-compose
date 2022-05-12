package br.com.alura.sevendaysofcodeandroid.model

data class User(
    val login: String,
    val avatar: String,
    val name: String,
    val bio: String,
    val repositories: String = "",
    val followers: String = "",
    val following: String = ""
)