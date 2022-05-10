package com.mcssoft.racedaycompose.ui.components.navigation

/** TBA **/

//import androidx.compose.material.BottomNavigation
//import androidx.compose.material.BottomNavigationItem
//import androidx.compose.material.Icon
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import com.mcssoft.racedaycompose.R
//
//@Composable
//fun BottomBar(
//    navController: NavController
//) {
//    val bottomNavItems = listOf(
//        BottomNavItem.Home,
//        BottomNavItem.Settings,
//        BottomNavItem.Summary
//    )
//    BottomNavigation(
//        backgroundColor = colorResource(id = R.color.colourPrimary),
//        contentColor = Color.White
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        bottomNavItems.forEach { item ->
//            BottomNavigationItem(
//                icon = {
//                    Icon(
//                        painterResource(id = item.icon),
//                        contentDescription = item.title)
//                },
//                label = {
//                    Text(text = item.title)
//                },
//                selectedContentColor = Color.White,
//                unselectedContentColor = Color.White.copy(0.4f),
//                alwaysShowLabel = true,
//                selected = currentRoute == item.route,
//                onClick = {
//                    navController.navigate(item.route) {
//                        // Pop up to the start destination of the graph to avoid building up a large
//                        // stack of destinations on the back stack as users select items.
//                        navController.graph.startDestinationRoute?.let { route ->
//                            popUpTo(route) {
//                                saveState = true
//                            }
//                        }
//                        // Avoid multiple copies of the same destination when re-selecting the same
//                        // item.
//                        launchSingleTop = true
//                        // Restore state when re-selecting a previously selected item
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    }
//}