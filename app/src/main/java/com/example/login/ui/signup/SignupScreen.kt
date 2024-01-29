package com.example.login.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
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
import com.example.login.presentation.CheckboxComponent
import com.example.login.presentation.ClickableLoginComponent
import com.example.login.presentation.DividerTextComponent
import com.example.login.presentation.HeaderTextComponent
import com.example.login.presentation.PasswordFieldComponent
import com.example.login.presentation.TextComponent
import com.example.login.presentation.TextFieldComponent

@Composable
fun SignupScreen(
    navHostController: NavHostController
) {
    val viewModel = viewModel(modelClass = SignupViewModel::class.java)
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
                HeaderTextComponent(headerText = "Create an Account")
                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(labelText = "First Name", icon = Icons.Outlined.Person, value = state.fname){viewModel.onFirstNameChanged(it)}
                TextFieldComponent(labelText = "Last Name", icon = Icons.Outlined.Person, value = state.lname){viewModel.onLastNameChanged(it)}
                TextFieldComponent(labelText = "Email", icon = Icons.Outlined.Email, value = state.email, error = state.emailFormatValidity){viewModel.onEmailChanged(it)}
                PasswordFieldComponent(value = state.password, error = state.passwordFormatValidity){viewModel.onPasswordChanged(it)}
                CheckboxComponent(navHostController, state.isCheckboxClicked){viewModel.onCheckBoxChanged()}
                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(text = "Register", enabled = state.buttonRegister){viewModel.createUserInFirebase(navHostController)}
                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()
                ClickableLoginComponent(normalText = "Already have an Account? ", annotText = "Login", navHostController)
            }
        }
        if (state.signupProgress){
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun PrevSignUpScreen() {
    SignupScreen(rememberNavController())
}