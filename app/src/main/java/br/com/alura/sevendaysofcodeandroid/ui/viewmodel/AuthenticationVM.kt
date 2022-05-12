package br.com.alura.sevendaysofcodeandroid.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import br.com.alura.sevendaysofcodeandroid.preferences.signedUser
import br.com.alura.sevendaysofcodeandroid.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationVM @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val repository: UsersRepository
) : ViewModel() {

    var uiState by mutableStateOf(AuthenticationUiState())

    suspend fun setUser(user: String) {
        uiState = uiState.copy(user = user)
        repository.findUserById(user)?.let {
            uiState = uiState.copy(image = it.avatar)
        }
    }

    suspend fun save(user: String) {
        dataStore.edit { setting ->
            setting[signedUser] = user
        }
    }

}

data class AuthenticationUiState(
    val user: String = "",
    val image: String = ""
)