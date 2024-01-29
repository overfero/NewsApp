package com.example.login.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.login.util.Screen
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    var state by mutableStateOf(LoginState())

    fun onEmailChanged(newValue: String){
        state = state.copy(email = newValue)
        onButtonEnabled()
    }

    fun onPasswordChanged(newValue: String){
        state = state.copy(password = newValue)
        onButtonEnabled()
    }

    private fun onButtonEnabled(){
        val enabled = state.email.isNotEmpty() && state.password.isNotEmpty()
        state = state.copy(buttonLogin = enabled)
    }

    fun logIn(navHostController: NavHostController){
        state = state.copy(logInProgress = true)
        val email = state.email
        val password = state.password

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    state = state.copy(logInProgress = false)
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Home.route)
                }
            }
            .addOnFailureListener {
                state = state.copy(logInProgress = false)
            }
    }
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val buttonLogin: Boolean = false,
    val logInProgress: Boolean = false
)