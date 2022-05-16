package br.com.alura.sevendaysofcodeandroid.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.alura.sevendaysofcodeandroid.model.User
import br.com.alura.sevendaysofcodeandroid.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowingVM @Inject constructor(
    private val repository: UsersRepository,
) : ViewModel() {

    var uiState: FollowersUiState by mutableStateOf(FollowersUiState())

    suspend fun findFollowingBy(user: String) {
        uiState = uiState
            .copy(users = repository.findFollowingUsers(user))
    }

}

data class FollowingUiState(
    val users: List<User> = emptyList()
)
