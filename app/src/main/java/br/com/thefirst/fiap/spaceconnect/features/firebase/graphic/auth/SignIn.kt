package br.com.thefirst.fiap.spaceconnect.features.firebase.graphic.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.thefirst.fiap.spaceconnect.R
import br.com.thefirst.fiap.spaceconnect.ui.theme.SpaceConnectTheme

@Composable
fun SignIn(
    onSignIn: (email: String, password: String) -> Unit,
    onCreateAccount: () -> Unit,
) {

    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

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
                value = emailInput,
                onValueChange = { emailInput = it },
                leadingIcon = {
                    Icon(Icons.Default.Email, null)
                },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                leadingIcon = {
                    Icon(Icons.Default.Lock, null)
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(R.drawable.olho_bloqueado),
                        contentDescription = null
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onSignIn(emailInput, passwordInput) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0B3D91)
                ),
            ) {
                Text("Entrar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Não tem uma conta? ")
                TextButton(
                    onClick = onCreateAccount,
                    modifier = Modifier.padding(0.dp)

                ) {
                    Text(
                        text = "Cadastre-se",
                        color = Color(0xFF0D47A1),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    SpaceConnectTheme {
        SignIn(
            onSignIn = { _, _ -> },
            onCreateAccount = {}
        )
    }
}
