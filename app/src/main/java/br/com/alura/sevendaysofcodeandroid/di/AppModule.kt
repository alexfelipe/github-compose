package br.com.alura.sevendaysofcodeandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import br.com.alura.sevendaysofcodeandroid.preferences.dataStore
import br.com.alura.sevendaysofcodeandroid.webclient.service.GitHubRepoService
import br.com.alura.sevendaysofcodeandroid.webclient.service.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideGitHubService(): GitHubService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .run {
                create(GitHubService::class.java)
            }
    }

    @Provides
    fun provideGitHubRepoService(): GitHubRepoService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .run {
                create(GitHubRepoService::class.java)
            }
    }

}

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.dataStore
    }

}