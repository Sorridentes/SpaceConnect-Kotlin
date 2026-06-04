package br.com.thefirst.fiap.spaceconnect.features.auth.presentation.auth

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import br.com.thefirst.fiap.spaceconnect.common.UiState
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel = koinViewModel(),
    onNavigateToHome: (currentUser: User) -> Unit,
) {
    val createUserState by viewModel.createUserState.collectAsState()
    val signInState by viewModel.signInState.collectAsState()
    val currentUser by viewModel.currentUserState.collectAsState()

    var isLoginMode by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            val user = User(
                id = currentUser!!.id,
                name = currentUser!!.name,
                email = currentUser!!.email
            )
            onNavigateToHome(user)
        }
    }

    LaunchedEffect(signInState) {
        when (signInState) {
            is UiState.Success -> {
                Toast.makeText(
                    context,
                    "Login realizado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UiState.Error -> {
                Toast.makeText(
                    context,
                    (signInState as UiState.Error).message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            else -> {}
        }
    }

    LaunchedEffect(createUserState) {
        when (createUserState) {
            is UiState.Success -> {
                Toast.makeText(
                    context,
                    "Usuário criado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UiState.Error -> {
                Toast.makeText(
                    context,
                    (createUserState as UiState.Error).message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            else -> {}
        }
    }


    if (isLoginMode) {
        SignIn(
            onSignIn = {email, password ->
                viewModel.signIn(email, password)
            },
            onCreateAccount = { isLoginMode = false }
        )
    } else {
        SignUp(
            onBackClick = { isLoginMode = true },
            onCreateAccount = {name, email, password ->
                viewModel.createUser(name, email, password)

            }
        )
    }
}

