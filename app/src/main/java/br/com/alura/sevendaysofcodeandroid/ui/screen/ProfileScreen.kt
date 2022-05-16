package br.com.alura.sevendaysofcodeandroid.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.sevendaysofcodeandroid.R
import br.com.alura.sevendaysofcodeandroid.model.GitHubRepo
import br.com.alura.sevendaysofcodeandroid.model.User
import br.com.alura.sevendaysofcodeandroid.ui.components.RepositoryItem
import br.com.alura.sevendaysofcodeandroid.ui.sampledata.sampleUsers
import br.com.alura.sevendaysofcodeandroid.ui.theme.DefaultDarkBg
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.ProfileUiState
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.ProfileVM
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    viewModel: ProfileVM,
    userId: String? = null,
    onFollowersClick: (user: String?) -> Unit = {},
    onFollowingClick: (user: String?) -> Unit = {},
    onUserTitleClick: () -> Unit = {},
    onFailureLoadingUser: () -> Unit = {}
) {
    val uiState = viewModel.uiState
    userId?.let {
        LaunchedEffect(null) {
            viewModel.loadUser(
                it,
                onFailureUserLoading = onFailureLoadingUser
            )
        }
    }
    val currentUser = viewModel.currentUser
    val scope = rememberCoroutineScope()
    ProfileWithError(
        uiState,
        currentUser,
        onLogoutClick = {
            scope.launch {
                viewModel.logout()
            }
        }, onFollowersClick = {
            onFollowersClick(userId)
        },
        onFollowingClick = {
            onFollowingClick(userId)
        },
        onUserTitleClick = {
            onUserTitleClick()
        }
    )
}

@Composable
private fun ProfileWithError(
    uiState: ProfileUiState,
    currentUser: String? = null,
    onLogoutClick: () -> Unit = {},
    onFollowersClick: () -> Unit = {},
    onFollowingClick: () -> Unit = {},
    onUserTitleClick: () -> Unit = {}
) {
    if (uiState.error != null) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                "Não foi possível encontrar usuário",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Center),
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    if (currentUser != null) {
                        Text(
                            text = currentUser, modifier = Modifier
                                .clickable(onClick = onUserTitleClick)
                        )
                    }
                }, actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Logout user"
                        )
                    }
                })
            }) {
            Profile(
                uiState,
                onFollowersClick = onFollowersClick,
                onFollowingClick = onFollowingClick
            )
        }
    }
}

@Composable
private fun Profile(
    uiState: ProfileUiState,
    onFollowingClick: () -> Unit = {},
    onFollowersClick: () -> Unit = {}
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        item {
            ProfileHeader(
                uiState,
                onFollowingClick = onFollowingClick,
                onFollowersClick = onFollowersClick
            )
        }
        item {
            if (uiState.repositories.isNotEmpty()) {
                Text(
                    "Repositórios: ${uiState.repositoriesCount}",
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        items(uiState.repositories) { repo: GitHubRepo ->
            RepositoryItem(repo = repo)
        }
    }
}


@Composable
private fun ProfileHeader(
    uiState: ProfileUiState,
    onFollowingClick: () -> Unit = {},
    onFollowersClick: () -> Unit = {}
) {
    val imageSize by remember {
        mutableStateOf(120.dp)
    }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageSize)
                .background(
                    DefaultDarkBg, shape = RoundedCornerShape(
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    )
                )
        ) {
            AsyncImage(
                model = uiState.avatar,
                contentDescription = "Profile pic",
                placeholder = painterResource(
                    id = R.drawable.default_profile_avatar
                ),
                modifier = Modifier
                    .offset(y = imageSize / 2)
                    .size(imageSize)
                    .clip(CircleShape)
                    .align(BottomCenter)
            )
        }
        Spacer(modifier = Modifier.height(imageSize / 2))
        Text(
            uiState.name,
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Text(
            uiState.bio,
            Modifier
                .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            uiState.login,
            Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onFollowingClick)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Seguindo")
                Text(text = uiState.followingCount)
            }
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onFollowersClick()
                    }
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Seguidores")
                Text(text = uiState.followersCount)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader(
        uiState = profileUiStateExample()
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileUsersPreview() {
    Profile(uiState = profileUiStateExample(sampleUsers))
}

@Preview(showBackground = true)
@Composable
fun ProfileWithErrorPreview() {
    ProfileWithError(
        uiState = ProfileUiState(
            login = "",
            avatar = "https://avatars.githubusercontent.com/u/8989346?v=4",
            name = "Alex Felipe",
            bio = "Instructor and Software Developer at @alura-cursos",
            error = "Test error"
        )
    )
}

private fun profileUiStateExample(users: List<User> = emptyList()) = ProfileUiState(
    login = "alexfelipe",
    avatar = "https://avatars.githubusercontent.com/u/8989346?v=4",
    name = "Alex Felipe",
    bio = "Instructor and Software Developer at @alura-cursos",
    repositoriesCount = "100",
    followersCount = "100",
    followingCount = "23",
    followingUsers = users
)
