package br.com.alura.sevendaysofcodeandroid.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.sevendaysofcodeandroid.extensions.toBrazilianDateTime
import br.com.alura.sevendaysofcodeandroid.model.GitHubRepo
import br.com.alura.sevendaysofcodeandroid.ui.sampledata.sampleRepositories
import br.com.alura.sevendaysofcodeandroid.ui.theme.DefaultDarkBg

@Composable
fun RepositoryItem(repo: GitHubRepo) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                repo.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DefaultDarkBg)
                    .padding(8.dp),
                fontSize = 28.sp,
                color = Color.White
            )
            AnimatedVisibility(visible = repo.description.isNotBlank()) {
                Text(
                    repo.description,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 8.dp,
                            end = 8.dp
                        )
                        .fillMaxWidth(),
                    fontSize = 16.sp
                )
            }
            Text(
                "atualizado em: ${repo.updatedAt.toBrazilianDateTime()}",
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp
                )
            )
            Box(
                Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
            ) {
                if (repo.stargazersCount.isNotBlank() || repo.forksCount.isNotBlank()) {
                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        if (iconVisible(repo.stargazersCount)) {
                            Row(
                                Modifier.padding(end = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Star,
                                    contentDescription = "stargazed count"
                                )
                                Text(repo.stargazersCount)
                            }
                        }
                        if (iconVisible(repo.forksCount)) {
                            Row(
                                Modifier.padding(end = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Group,
                                    contentDescription = "stargazed count"
                                )
                                Text(repo.forksCount)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun iconVisible(value: String) =
    value.isNotBlank() && value != "0"


@Composable
fun RepositoriesList(repos: List<GitHubRepo>) {
    LazyColumn {
        items(repos) { repo ->
            RepositoryItem(repo = repo)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoriesListPreview() {
    RepositoriesList(repos = sampleRepositories)
}

@Preview(showBackground = true)
@Composable
private fun RepositoryItemPreview() {
    RepositoryItem(sampleRepositories.first())
}
