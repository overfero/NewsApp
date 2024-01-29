package com.example.login.ui.term

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.login.presentation.HeaderTextComponent

@Composable
fun TermsAndConditionScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        HeaderTextComponent(headerText = "Terms and Condition")
    }
}

@Composable
fun PrivacyPolicyScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        HeaderTextComponent(headerText = "Privacy Policy")
    }
}