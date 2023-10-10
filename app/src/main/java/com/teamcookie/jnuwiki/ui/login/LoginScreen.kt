package com.teamcookie.jnuwiki.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.teamcookie.jnuwiki.Const
import com.teamcookie.jnuwiki.R

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val loginUiState by loginViewModel.uiState.collectAsState()

    if(loginUiState.isLogin){
        navController.navigate(Const.HOME) {
            popUpTo(Const.HOME){
                inclusive = true
            }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { _ ->

        LaunchedEffect(loginUiState){
            if(loginUiState.message != ""){
                snackbarHostState.showSnackbar(loginUiState.message)
                loginViewModel.resetMessage()
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "뒤로가기",
                    tint = Color.Gray
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Row {
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = stringResource(R.string.login_text),
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.main_color)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = stringResource(R.string.login_welcome_text),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.main_color)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.id_text),
                        color = colorResource(id = R.color.main_color_bright),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = loginUiState.id,
                        onValueChange = {
                            loginViewModel.updateId(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.id_placeholder_text),
                                color = colorResource(id = R.color.placeholder_color),
                                fontSize = 14.sp
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = colorResource(id = R.color.main_color_bright),
                            focusedIndicatorColor = colorResource(id = R.color.main_color_bright),
                            backgroundColor = White
                        ),
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(R.string.pw_text),
                        color = colorResource(id = R.color.main_color_bright),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = loginUiState.pw,
                        onValueChange = {
                            loginViewModel.updatePw(it)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = colorResource(id = R.color.main_color_bright),
                            focusedIndicatorColor = colorResource(id = R.color.main_color_bright),
                            backgroundColor = White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.pw_placeholder_text),
                                color = colorResource(id = R.color.placeholder_color),
                                fontSize = 14.sp
                            )
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                R.drawable.visible_icon
                            else R.drawable.invisible_icon

                            val description = if (passwordVisible) "비밀번호 숨기기" else "비밀번호 표시"


                            Icon(
                                painter = painterResource(id = image),
                                description,
                                modifier = Modifier.clickable {
                                    passwordVisible = !passwordVisible
                                })
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                    )
                }
            }


            Spacer(modifier = Modifier.weight(1f))

            Row {

                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = {
                        loginViewModel.submit()
                    },
                    modifier = Modifier.weight(1f),
                    colors = buttonColors(
                        backgroundColor = colorResource(id = R.color.main_color_bright)
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.login_text),
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }

            Spacer(modifier = Modifier.height(40.dp))


            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.registe_question_text),
                    color = Gray,
                    fontSize = 16.sp

                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.registe_text),
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.main_color),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}