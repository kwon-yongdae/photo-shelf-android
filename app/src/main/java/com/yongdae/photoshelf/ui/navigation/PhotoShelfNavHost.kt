package com.yongdae.photoshelf.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yongdae.photoshelf.feature.photos.detail.PhotoDetailScreen
import com.yongdae.photoshelf.feature.photos.favorites.FavoritePhotosScreen
import com.yongdae.photoshelf.feature.photos.photos.LatestPhotosScreen

@Composable
fun PhotoShelfNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "photos") {
        composable("photos") {
            LatestPhotosScreen(
                onPhotoClick = { id ->
                    navController.navigate("photos/${id}")
                },
                onFavoritesClick = {
                    navController.navigate("favorites")
                }
            )
        }
        composable(
            route = "photos/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")!!
            PhotoDetailScreen(
                id = id,
                onBack = {
                    navController.popBackStack()
                },
            )
        }
        composable("favorites") {
            FavoritePhotosScreen(
                onPhotoClick = { id ->
                    navController.navigate("photos/${id}")
                },
                onBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}