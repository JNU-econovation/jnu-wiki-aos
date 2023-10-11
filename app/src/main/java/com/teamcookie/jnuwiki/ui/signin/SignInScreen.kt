package com.teamcookie.jnuwiki.ui.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun SignInScreen(navController: NavHostController, signInViewModel: SignInViewModel = viewModel()) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var repeatVisible by rememberSaveable { mutableStateOf(false) }
    val signInUiState by signInViewModel.uiState.collectAsState()

    if (signInUiState.isSignIn) {
        navController.navigate(Const.LOGIN) {
            popUpTo(Const.LOGIN){
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

        LaunchedEffect(signInUiState) {
            if (signInUiState.message != "") {
                snackbarHostState.showSnackbar(signInUiState.message)
                signInViewModel.resetMessage()
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "뒤로가기",
                    tint = Color.Gray,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Row {
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "회원가입",
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.main_color)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "반가워요! 회원가입 후 10일이 지나면\n글 작성이 가능해요 :)",
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
                        text = "이메일",
                        color = colorResource(id = R.color.main_color_bright),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = signInUiState.email,
                        onValueChange = {
                            signInViewModel.updateField(email = it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text(
                                text = "전남대학교 이메일을 입력해주세요.",
                                color = colorResource(id = R.color.placeholder_color),
                                fontSize = 14.sp
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = colorResource(id = R.color.main_color_bright),
                            focusedIndicatorColor = colorResource(id = R.color.main_color_bright),
                            backgroundColor = Color.White
                        ),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.main_color)
                            ),
                            onClick = {
                                signInViewModel.checkEmail()
                            },
                            shape = RoundedCornerShape(5.dp),
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp)
                        ) {
                            Text(
                                text = "중복 확인",
                                fontSize = 16.sp,
                                color = Color.White,
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "닉네임",
                        color = colorResource(id = R.color.main_color_bright),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = signInUiState.nickname,
                        onValueChange = {
                            signInViewModel.updateField(nickname = it)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = colorResource(id = R.color.main_color_bright),
                            focusedIndicatorColor = colorResource(id = R.color.main_color_bright),
                            backgroundColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text(
                                text = "닉네임을 입력하세요",
                                color = colorResource(id = R.color.placeholder_color),
                                fontSize = 14.sp
                            )
                        },
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.main_color)
                            ),
                            onClick = {
                                signInViewModel.checkNickname()
                            },
                            shape = RoundedCornerShape(5.dp),
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp)
                        ) {
                            Text(
                                text = "중복 확인",
                                fontSize = 16.sp,
                                color = Color.White,
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "비밀번호",
                        color = colorResource(id = R.color.main_color_bright),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = signInUiState.pw,
                        onValueChange = {
                            signInViewModel.updateField(pw = it)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = colorResource(id = R.color.main_color_bright),
                            focusedIndicatorColor = colorResource(id = R.color.main_color_bright),
                            backgroundColor = Color.White
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


                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "비밀번호 재입력",
                        color = colorResource(id = R.color.main_color_bright),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = signInUiState.repeat,
                        onValueChange = {
                            signInViewModel.updateField(repeat = it)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = colorResource(id = R.color.main_color_bright),
                            focusedIndicatorColor = colorResource(id = R.color.main_color_bright),
                            backgroundColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text(
                                text = "비밀번호를 한번 더 입력해주세요.",
                                color = colorResource(id = R.color.placeholder_color),
                                fontSize = 14.sp
                            )
                        },
                        visualTransformation = if (repeatVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (repeatVisible)
                                R.drawable.visible_icon
                            else R.drawable.invisible_icon

                            val description = if (repeatVisible) "비밀번호 숨기기" else "비밀번호 표시"


                            Icon(
                                painter = painterResource(id = image),
                                description,
                                modifier = Modifier.clickable {
                                    repeatVisible = !repeatVisible
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
                        signInViewModel.submit()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.main_color_bright)
                    ),
                ) {
                    Text(
                        text = "회원가입",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}