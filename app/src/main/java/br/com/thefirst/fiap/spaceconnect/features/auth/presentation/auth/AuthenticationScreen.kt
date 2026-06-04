package br.com.thefirst.fiap.spaceconnect.features.auth.presentation.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthenticationScreen(
    authViewModel: AuthenticationViewModel = koinViewModel(),
    onSignIn: (email: String, password: String) -> Unit,
    onCreateAccount: (name: String, email: String, password: String) -> Unit,
) {

    var isLoginMode by remember { mutableStateOf(true) }


    if (isLoginMode) {
        SignIn(
            onSignIn = onSignIn,
            onCreateAccount = { isLoginMode = false }
        )
    } else {
        SignUp(
            onBackClick = { isLoginMode = true },
            onCreateAccount = onCreateAccount
        )
    }
}

