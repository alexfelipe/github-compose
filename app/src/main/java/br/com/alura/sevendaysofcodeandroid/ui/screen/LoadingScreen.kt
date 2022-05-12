package br.com.alura.sevendaysofcodeandroid.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.sevendaysofcodeandroid.ui.theme.DefaultDarkBg

@Preview(showBackground = true)
@Composable
fun LoadingScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = DefaultDarkBg)) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center)
        )
    }
}