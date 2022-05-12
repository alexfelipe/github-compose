package br.com.alura.sevendaysofcodeandroid.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.alura.sevendaysofcodeandroid.ui.components.UsersList
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.FollowersVM

@Composable
fun FollowersScreen(
    viewModel: FollowersVM,
    userId: String
) {
    val uiState = viewModel.uiState
    LaunchedEffect(null) {
        viewModel.findFollowersBy(userId)
    }
    UsersList(title = "Followers", uiState.users)
}