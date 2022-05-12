package br.com.alura.sevendaysofcodeandroid.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.sevendaysofcodeandroid.ui.theme.SevenDaysOfCodeAndroidTheme
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.AppViewModel
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.AuthenticationUiState
import br.com.alura.sevendaysofcodeandroid.ui.viewmodel.AuthenticationVM
import kotlinx.coroutines.launch

@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationVM,
    onEnterClick: (user: String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val uiState = viewModel.uiState
    Authentication(
        uiState,
        onUserChange = { login ->
            scope.launch {
                viewModel.setUser(login)
            }
        },
        onEnterClick = {
            scope.launch {
                viewModel.save(uiState.user)
                onEnterClick(uiState.user)
            }
        }
    )
}

@Composable
fun Authentication(
    uiState: AuthenticationUiState,
    onUserChange: (String) -> Unit = {},
    onEnterClick: () -> Unit = {}
) {
    SevenDaysOfCodeAndroidTheme(darkTheme = true) {
        Surface {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    ),
                horizontalAlignment = CenterHorizontally,
            ) {
                Text(
                    "DevHub",
                    Modifier
                        .padding(top = 120.dp, bottom = 60.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 64.sp
                )
                OutlinedTextField(
                    value = uiState.user,
                    onValueChange = onUserChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    label = {
                        Text("username")
                    },
                )
                Button(
                    onClick = onEnterClick,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Entrar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationPreview() {
    Authentication(uiState = AuthenticationUiState())
}
