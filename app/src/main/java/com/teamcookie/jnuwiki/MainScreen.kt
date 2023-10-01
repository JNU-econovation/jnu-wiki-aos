package com.teamcookie.jnuwiki

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.teamcookie.jnuwiki.Const.CREATE
import com.teamcookie.jnuwiki.Const.HOME
import com.teamcookie.jnuwiki.Const.MY
import com.teamcookie.jnuwiki.ui.create.CreateScreen
import com.teamcookie.jnuwiki.ui.home.HomeScreen
import com.teamcookie.jnuwiki.ui.my.MyScreen

@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar()
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

    androidx.compose.material.BottomNavigation(
        backgroundColor = White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                        color = colorResource(id = R.color.nav_selected),
                        fontWeight = FontWeight.Bold
                    )
                },
                selectedContentColor = colorResource(id = R.color.nav_selected),
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

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
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
}

@Composable
fun TopAppBar() {
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
        UserLoginStateComponent()
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Composable
fun UserLoginStateComponent() {
    LoginBtnComponent()
}

@Composable
fun LoginBtnComponent() {
    Button(
        colors = buttonColors(
            backgroundColor = colorResource(id = R.color.button_color)
        ),
        onClick = {

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