package br.com.alura.sevendaysofcodeandroid.ui.components

import br.com.alura.sevendaysofcodeandroid.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.sevendaysofcodeandroid.model.User
import br.com.alura.sevendaysofcodeandroid.ui.sampledata.sampleUsers
import coil.compose.AsyncImage

@Composable
fun UserItem(
    user: User,
    onClick: (user: User) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = { onClick(user) })
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.avatar,
                contentDescription = "Profile pic",
                placeholder = painterResource(
                    id = R.drawable.default_profile_avatar
                ),
                modifier = Modifier
                    .width(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(user.login, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FollowingItemPreview() {
    UserItem(user = sampleUsers.first(),
        onClick = {})
}