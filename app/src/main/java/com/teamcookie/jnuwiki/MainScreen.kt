package com.teamcookie.jnuwiki

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.teamcookie.jnuwiki.Const.CREATE
import com.teamcookie.jnuwiki.Const.HOME
import com.teamcookie.jnuwiki.Const.MY
import com.teamcookie.jnuwiki.ui.login.LoginScreen
import com.teamcookie.jnuwiki.ui.maps.MapViewModel
import com.teamcookie.jnuwiki.ui.maps.create.CreateScreen
import com.teamcookie.jnuwiki.ui.maps.home.HomeScreen
import com.teamcookie.jnuwiki.ui.maps.my.MyScreen
@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(navController = navController)

        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavigationGraph(navController = navController)
        }
    }
}

sealed class BottomNavItem(val title: Int, val icon: Int, val screenRoute: String) {
    object Home : BottomNavItem(R.string.nav_home, R.drawable.home_icon, HOME)

    object Create : BottomNavItem(R.string.nav_create, R.drawable.edit_icon, CREATE)

    object My : BottomNavItem(R.string.nav_my, R.drawable.my_icon, MY)
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val list = listOf(BottomNavItem.Home, BottomNavItem.Create, BottomNavItem.My)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        BottomNavItem.My.screenRoute, BottomNavItem.Create.screenRoute, BottomNavItem.Home.screenRoute -> {
            androidx.compose.material.BottomNavigation(
                backgroundColor = White
            ) {
                val currentRoute = navBackStackEntry?.destination?.route

                list.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = stringResource(id = item.title)
                            )
                        },
                        label = {
                            Text(
                                stringResource(id = item.title),
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.main_color_bright),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        selectedContentColor = colorResource(id = R.color.main_color_bright),
                        unselectedContentColor = Gray,
                        selected = currentRoute == item.screenRoute,
                        alwaysShowLabel = false,
                        onClick = {
                            navController.navigate(item.screenRoute) {
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }

        else -> Unit
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Const.MAPS) {
        navigation(startDestination = BottomNavItem.Home.screenRoute, route = Const.MAPS

        ) {
            composable(BottomNavItem.Home.screenRoute) {
                HomeScreen()
            }

            composable(BottomNavItem.Create.screenRoute) {
                CreateScreen()
            }

            composable(BottomNavItem.My.screenRoute) {
                MyScreen()
            }
        }

        composable(Const.LOGIN){
            LoginScreen(navController)
        }
    }
}

@Composable
fun TopAppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        BottomNavItem.My.screenRoute, BottomNavItem.Create.screenRoute, BottomNavItem.Home.screenRoute -> {
            androidx.compose.material.TopAppBar(
                backgroundColor = White,
                modifier = Modifier.height(73.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "jnu wiki 로고",
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.weight(1f))
                UserLoginStateComponent(navController)
                Spacer(modifier = Modifier.width(12.dp))
            }
        }

        else -> Unit
    }


}

@Composable
fun UserLoginStateComponent(navController: NavHostController, mapViewModel: MapViewModel = viewModel()) {
    val uiState by mapViewModel.uiState.collectAsState()

    if(uiState.isLogin == null){
        mapViewModel.checkLogin()
    }else{
        if(uiState.isLogin!!) UserNameComponent(uiState.nickName) else LoginBtnComponent(navController = navController)
    }
}

@Composable
fun LoginBtnComponent(navController: NavHostController) {
    Button(
        colors = buttonColors(
            backgroundColor = colorResource(id = R.color.main_color)
        ),
        onClick = {
            navController.navigate(Const.LOGIN)
        },
        shape = RoundedCornerShape(5.dp),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp)
    ) {
        Text(
            text = "로그인",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = White,
        )

    }
}

@Composable
fun UserNameComponent(nickName : String) {
    Row {
        Text(
            text = nickName,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = "로그아웃",
            tint = Gray,
            modifier = Modifier.clickable {
                //todo : logout
            }
        )
    }
}