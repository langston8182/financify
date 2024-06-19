package com.financify.ui.view

import AuthViewModelFactory
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.financify.model.Screens
import com.financify.viewmodel.AuthViewModel
import com.financify.viewmodel.UserViewModel
import com.financify.viewmodel.UserViewModelFactory
import com.financify.viewmodel.UsersViewModel
import com.financify.viewmodel.UsersViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    authViewModelFactory: AuthViewModelFactory,
    usersViewModelFactory: UsersViewModelFactory,
    userViewModelFactory: UserViewModelFactory
) {
    val authViewModel: AuthViewModel = viewModel(factory = authViewModelFactory)
    val usersViewModel: UsersViewModel = viewModel(factory = usersViewModelFactory)
    val userViewModel: UserViewModel = viewModel(factory = userViewModelFactory);
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope();
    val openDrawer = {
        coroutineScope.launch { drawerState.open() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                DrawerMenu(
                    authViewModel = authViewModel,
                    onSelected = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(route = it.title) {
                            popUpTo(route = it.title) { inclusive = true }
                        }
                    }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.WELCOME.title
        ) {
            composable(route = Screens.WELCOME.title) {
                ScaffoldWelcome(
                    authViewModel = authViewModel,
                    openDrawer = { openDrawer() },
                    login = { navController.navigate(Screens.LOGIN.title) },
                )
            }
            composable(route = Screens.LOGIN.title) {
                ScaffoldLogin(
                    openDrawer = { openDrawer() },
                    login = { navController.navigate(Screens.LOGIN.title) },
                    authViewModel = authViewModel
                )
            }
            composable(route = Screens.LOGOUT.title) {
                LogoutScreen(authViewModel = authViewModel, navController = navController)
            }
            composable(route = Screens.USER.title) {
                ScaffoldUserDetail(
                    userViewModel = userViewModel,
                    authViewModel = authViewModel,
                    login = { navController.navigate(Screens.LOGIN.title) },
                    openDrawer = { openDrawer() },
                    navController = navController
                )
            }
            composable(route = Screens.USERS.title) {
                ScaffoldUsers(
                    openDrawer = { openDrawer() },
                    login = { navController.navigate(Screens.LOGIN.title) },
                    authViewModel = authViewModel,
                    usersViewModel = usersViewModel,
                    selectUser = { user ->
                        userViewModel.selectUser(user)
                        navController.navigate(Screens.USER.title)
                    }
                )
            }
        }
    }
}