package br.com.alura.sevendaysofcodeandroid.ui.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import br.com.alura.sevendaysofcodeandroid.preferences.signedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    fun user(): Flow<String?> {
        return dataStore.data
            .map {
                it[signedUser]
            }
    }

}