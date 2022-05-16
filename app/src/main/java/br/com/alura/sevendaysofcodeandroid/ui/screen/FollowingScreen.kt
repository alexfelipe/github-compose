package br.com.alura.sevendaysofcodeandroid.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.alura.sevendaysofcodeandroid.ui.components.UsersList
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.FollowingVM

@Composable
fun FollowingScreen(
    viewModel: FollowingVM,
    userId: String,
    onUserClick: (userId: String) -> Unit = {}
) {
    val uiState = viewModel.uiState
    LaunchedEffect(null) {
        viewModel.findFollowingBy(userId)
    }
    UsersList(
        title = "Seguindo",
        uiState.users,
        onUserClick = { user ->
            onUserClick(user.login)
        }
    )
}