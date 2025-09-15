package com.kaelmoreno.compose.composemultiplatformbase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.MainScreen
import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.PostsListScreen
import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.UserListScreen

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
            MainScreen(
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
            UserListScreen(
                onBack = {
                    Logger.i("Navigating back from Users screen", "Navigation")
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.Posts> {
            Logger.d("Navigating to Posts screen", "Navigation")
            PostsListScreen(
                onBack = {
                    Logger.i("Navigating back from Posts screen", "Navigation")
                    navController.popBackStack()
                }
            )
        }
    }
}
