package com.kaelmoreno.compose.composemultiplatformbase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.main_screen.MainScreenRoot
import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.post_list_screen.PostListScreenRoot
import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.user_list_screen.UserListScreenRoot

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main,
        modifier = modifier
    ) {
        composable<Screen.Main> {
            Logger.d("Navigating to Main screen", "Navigation")
            MainScreenRoot(
                onNavigateToUsers = {
                    Logger.i("Navigating to Users screen via NavController", "Navigation")
                    navController.navigate(Screen.Users)
                },
                onNavigateToPosts = {
                    Logger.i("Navigating to Posts screen via NavController", "Navigation")
                    navController.navigate(Screen.Posts)
                }
            )
        }

        composable<Screen.Users> {
            Logger.d("Navigating to Users screen", "Navigation")
            UserListScreenRoot(
                onBack = {
                    Logger.i("Navigating back from Users screen", "Navigation")
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.Posts> {
            Logger.d("Navigating to Posts screen", "Navigation")
            PostListScreenRoot(
                onBack = {
                    Logger.i("Navigating back from Posts screen", "Navigation")
                    navController.popBackStack()
                }
            )
        }
    }
}
