package com.example.login.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.login.presentation.ButtonComponent
import com.example.login.presentation.ClickableLoginComponent
import com.example.login.presentation.DividerTextComponent
import com.example.login.presentation.HeaderTextComponent
import com.example.login.presentation.PasswordFieldComponent
import com.example.login.presentation.TextComponent
import com.example.login.presentation.TextFieldComponent
import com.example.login.presentation.UnderlinedTextComponent

@Composable
fun LoginScreen(navHostController: NavHostController) {
    val viewModel = viewModel(modelClass = LoginViewModel::class.java)
    val state = viewModel.state

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TextComponent(normalText = "Hey There")
                HeaderTextComponent(headerText = "Welcome Back")
                Spacer(modifier = Modifier.height(40.dp))

                TextFieldComponent(labelText = "Email", icon = Icons.Outlined.Email, value = state.email){viewModel.onEmailChanged(it)}
                PasswordFieldComponent(value = state.password){viewModel.onPasswordChanged(it)}
                Spacer(modifier = Modifier.height(10.dp))

                UnderlinedTextComponent(normalText = "Forgot your password?")
                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(text = "Login", enabled = state.buttonLogin){viewModel.logIn(navHostController)}
                DividerTextComponent()
                ClickableLoginComponent(normalText = "Don't have an Account yet? ", annotText = "Register", navHostController)
            }
        }
        if (state.logInProgress){
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevLoginScreen() {
    LoginScreen(rememberNavController())
}