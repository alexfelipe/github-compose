package br.com.alura.sevendaysofcodeandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.alura.sevendaysofcodeandroid.routes.*
import br.com.alura.sevendaysofcodeandroid.ui.screen.*
import br.com.alura.sevendaysofcodeandroid.ui.theme.SevenDaysOfCodeAndroidTheme
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scope = rememberCoroutineScope()
            SevenDaysOfCodeAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = LOADING
                    ) {
                        composable(LOADING) {
                            LoadingScreen()
                            LaunchedEffect(null) {
                                viewModel.user().first().let { currentUser ->
                                    val destination =
                                        if (currentUser != null && currentUser.isNotBlank()) {
                                            "$PROFILE?userId=$currentUser"
                                        } else {
                                            AUTHENTICATION
                                        }
                                    navController.navigate(destination) {
                                        popUpTo(LOADING) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }
                        composable(AUTHENTICATION) {
                            AuthenticationScreen(hiltViewModel()) { user ->
                                navController.navigate("$PROFILE?userId=$user") {
                                    popUpTo(AUTHENTICATION)
                                }
                            }
                        }
                        composable(
                            "$PROFILE?userId={userId}",
                            arguments = listOf(navArgument("userId") {
                                type = NavType.StringType
                            })
                        ) {
                            val userId = it.arguments?.getString("userId")
                            ProfileScreen(
                                hiltViewModel(),
                                userId,
                                onFollowersClick = { user ->
                                    navController.navigate("$FOLLOWERS?userId=$user") {
                                        popUpTo(AUTHENTICATION)
                                    }
                                }, onFollowingClick = { user ->
                                    navController.navigate("$FOLLOWING?userId=$user") {
                                        popUpTo(AUTHENTICATION)
                                    }
                                }, onUserTitleClick = {
                                    scope.launch {
                                        viewModel.user().first()?.let { currentUser ->
                                            if (currentUser.isNotBlank()) {
                                                navController.navigate("$PROFILE?userId=$currentUser") {
                                                    popUpTo(0) {
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        }

                                    }
                                },
                                onFailureLoadingUser = {
                                    navController.navigate(AUTHENTICATION) {
                                        popUpTo(0) {
                                            inclusive = true
                                        }
                                    }
                                })
                        }
                        composable(
                            "$FOLLOWERS?userId={userId}",
                            arguments = listOf(navArgument("userId") {
                                type = NavType.StringType
                            })
                        ) {
                            val userId = it.arguments?.getString("userId")
                            userId?.let { id ->
                                FollowersScreen(hiltViewModel(), id, onUserClick = { user ->
                                    navController.navigate("$PROFILE?userId=$user")
                                })
                            }
                        }
                        composable(
                            "$FOLLOWING?userId={userId}",
                            arguments = listOf(navArgument("userId") {
                                type = NavType.StringType
                            })
                        ) {
                            val userId = it.arguments?.getString("userId")
                            userId?.let { id ->
                                FollowingScreen(hiltViewModel(), id, onUserClick = { user ->
                                    navController.navigate("$PROFILE?userId=$user")
                                })
                            }
                        }
                    }
                    val currentUser by viewModel.user().collectAsState(initial = "")
                    if (currentUser == null) {
                        navController.navigate(AUTHENTICATION) {
                            popUpTo(navController.graph.findStartDestination().id)
                        }
                    }
                }
            }
        }
    }

}


