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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.alura.sevendaysofcodeandroid.model.User
import br.com.alura.sevendaysofcodeandroid.routes.*
import br.com.alura.sevendaysofcodeandroid.ui.screen.AuthenticationScreen
import br.com.alura.sevendaysofcodeandroid.ui.screen.LoadingScreen
import br.com.alura.sevendaysofcodeandroid.ui.screen.ProfileScreen
import br.com.alura.sevendaysofcodeandroid.ui.theme.SevenDaysOfCodeAndroidTheme
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                                userId
                            ) { user ->
                                navController.navigate("$PROFILE?userId=${user.login}")
                            }
                        }
                        composable(
                            "$FOLLOWERS?userId={userId}",
                            arguments = listOf(navArgument("userId") {
                                type = NavType.StringType
                            })
                        ) {
                            val userId = it.arguments?.getString("userId")
//                            FollowersScreen(userId) { user ->
//                                navController.navigate("$PROFILE?userId=${user.login}")
//                            }
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


