package br.com.thefirst.fiap.spaceconnect.presentation.space.auth

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import br.com.thefirst.fiap.spaceconnect.domain.model.User
import br.com.thefirst.fiap.spaceconnect.presentation.common.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }

    val createUserState by viewModel.createUserState.collectAsState()
    val signInState by viewModel.signInState.collectAsState()
    val signOutState by viewModel.signOutState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(createUserState) {
        if (createUserState is UiState.Success) {
            name = ""
            email = ""
            password = ""
        } else if (createUserState is UiState.Error) {
            Toast.makeText(context, (createUserState as UiState.Error).message, Toast.LENGTH_LONG)
                .show()
        }
    }

    LaunchedEffect(signInState) {
        if (signInState is UiState.Success) {
            Toast.makeText(context, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
            email = ""
            password = ""

        }
    }

    if (isLoginMode) {
        SignIn(
            onSignIn = { email, password ->
                viewModel.signIn(email, password)
                onNavigateToHome()
            },
            enabled = signInState != UiState.Loading
        )
    } else {
        SignUp(
            onBackClick = { isLoginMode = true },
            onCreateAccount = { name, email, password ->
                viewModel.createUser(name, email, password)
                onNavigateToHome()
            }
        )
    }
}

