package com.example.login.ui.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.login.util.Screen
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignupViewModel: ViewModel() {
    var state by mutableStateOf(SignupState())

    fun onFirstNameChanged(newValue: String){
        state = state.copy(fname = newValue)
        onButtonEnabled()
    }

    fun onLastNameChanged(newValue: String){
        state = state.copy(lname = newValue)
        onButtonEnabled()
    }

    fun onEmailChanged(newValue: String){
        state = state.copy(email = newValue)
        onEmailValidityChanged()
        onButtonEnabled()
    }

    fun onPasswordChanged(newValue: String){
        state = state.copy(password = newValue)
        onPasswordValidityChanged()
        onButtonEnabled()
    }

    private fun onEmailValidityChanged(){
        val emailRegex = """^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"""

        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(state.email)
        state = state.copy(emailFormatValidity =  matcher.matches())
    }

    private fun onPasswordValidityChanged(){
        val passwordRegex = """(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d.]{8,}"""

        val pattern = Pattern.compile(passwordRegex)
        val matcher = pattern.matcher(state.password)
        state = state.copy(passwordFormatValidity =  matcher.matches())
    }

    private fun onButtonEnabled(){
        val enabled = state.fname.isNotEmpty() && state.lname.isNotEmpty()
                && state.emailFormatValidity && state.passwordFormatValidity && state.isCheckboxClicked
        state = state.copy(buttonRegister = enabled)
    }

    fun onCheckBoxChanged(){
        state = state.copy(isCheckboxClicked = !state.isCheckboxClicked)
        onButtonEnabled()
    }

    fun createUserInFirebase(navHostController: NavHostController){
        state = state.copy(signupProgress = true)
        val email = state.email
        val password = state.password

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    state = state.copy(signupProgress = false)
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Login.route)
                    Log.d("state", "Sukses")
                }
            }
            .addOnFailureListener {
                state = state.copy(signupProgress = false)
                Log.d("state", "Gagal = ${it.message}")
            }
    }
}

data class SignupState(
    val fname: String = "",
    val lname: String = "",
    val email: String = "",
    val password: String = "",
    val emailFormatValidity: Boolean = true,
    val passwordFormatValidity: Boolean = true,
    val buttonRegister: Boolean = false,
    val isCheckboxClicked: Boolean = false,
    val signupProgress: Boolean = false
)