package br.com.alura.sevendaysofcodeandroid.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import br.com.alura.sevendaysofcodeandroid.model.GitHubRepo
import br.com.alura.sevendaysofcodeandroid.model.User
import br.com.alura.sevendaysofcodeandroid.preferences.signedUser
import br.com.alura.sevendaysofcodeandroid.repository.GitHubRepository
import br.com.alura.sevendaysofcodeandroid.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val repository: UsersRepository,
    private val gitHubRepo: GitHubRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    var currentUser: String? by mutableStateOf(null)
        private set

    var uiState by mutableStateOf(ProfileUiState())
        private set

    suspend fun loadUser(
        id: String,
        onFailureUserLoading: () -> Unit
    ) {
        repository.findUserById(id)?.run {
            uiState = ProfileUiState(this)
            findFollowingUsersBy(this)
            findRepositoriesBy(this)
        } ?: onFailureUserLoading()
        currentUser = dataStore.data.first()[signedUser]
    }

    private suspend fun findRepositoriesBy(user: User) {
        val repositories = gitHubRepo.findRepositoriesBy(user)
        uiState = uiState.copy(repositories = repositories)
    }

    private suspend fun findFollowingUsersBy(user: User) {
        val users = repository.findFollowingUsers(user.login)
        uiState = uiState.copy(followingUsers = users)
    }

    suspend fun logout() {
        dataStore.edit { pref ->
            pref.remove(signedUser)
        }
    }

}

data class ProfileUiState(
    val login: String = "",
    val avatar: String = "",
    val name: String = "",
    val bio: String = "",
    val repositoriesCount: String = "",
    val followersCount: String = "",
    val followingCount: String = "",
    val error: String? = null,
    val followingUsers: List<User> = emptyList(),
    val repositories: List<GitHubRepo> = emptyList()
) {

    constructor(user: User) : this(
        login = user.login,
        avatar = user.avatar,
        name = user.name,
        bio = user.bio,
        followersCount = user.followers,
        followingCount = user.following,
        repositoriesCount = user.repositories,
        error = null
    )

}