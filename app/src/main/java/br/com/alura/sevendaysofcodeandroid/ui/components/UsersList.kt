package br.com.alura.sevendaysofcodeandroid.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.sevendaysofcodeandroid.model.User
import br.com.alura.sevendaysofcodeandroid.ui.sampledata.sampleUsers

@Composable
fun UsersList(
    title: String,
    users: List<User> = emptyList(),
    onUserClick: (user: User) -> Unit = {}
) {
    LazyColumn {
        item {
            Text(
                title,
                modifier = Modifier.padding(
                    8.dp
                ),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        items(users) { user ->
            UserItem(user, onClick = onUserClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UsersListPreview() {
    UsersList(title = "Seguindo", users = sampleUsers)
}