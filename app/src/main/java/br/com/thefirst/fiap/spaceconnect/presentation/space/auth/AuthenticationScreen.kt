package br.com.thefirst.fiap.spaceconnect.presentation.space.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.thefirst.fiap.spaceconnect.R
import br.com.thefirst.fiap.spaceconnect.presentation.common.Logo
import br.com.thefirst.fiap.spaceconnect.presentation.common.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Logo()

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Bem-vindo de volta",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Explore o universo através das lentes da ciência.",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                leadingIcon = {
                    Icon(Icons.Default.Email, null)
                },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                leadingIcon = {
                    Icon(Icons.Default.Lock, null)
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(R.drawable.olho_bloqueado),
                        contentDescription = null
                    )
                },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.signIn(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0B3D91)
                ),
                enabled = signInState != UiState.Loading
            ) {
                if (signInState is UiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                     )
                } else {
                    Text("Entrar", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Text("Não tem uma conta? ")
                TextButton(
                    onClick = {}
                ){
                    Text(text = "Cadastre-se",
                        color = Color(0xFF0D47A1),
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }

}