package br.com.alura.sevendaysofcodeandroid.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.alura.sevendaysofcodeandroid.ui.components.UsersList
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.FollowersVM

@Composable
fun FollowersScreen(
    viewModel: FollowersVM,
    userId: String,
    onUserClick: (userId: String) -> Unit = {}
) {
    val uiState = viewModel.uiState
    LaunchedEffect(null) {
        viewModel.findFollowersBy(userId)
    }
    UsersList(
        title = "Seguidores",
        uiState.users,
        onUserClick = { user ->
            onUserClick(user.login)
        }
    )
}