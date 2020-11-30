/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vjezba.data.di

import com.vjezba.data.database.NewsDatabase
import com.vjezba.data.database.dao.LanguagesDao
import com.vjezba.data.database.mapper.DbMapper
import com.vjezba.data.networking.ConnectivityUtil
import com.vjezba.data.networking.GithubRepositoryApi
import com.vjezba.data.repository.NewsRepositoryImpl
import com.vjezba.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides

/**
 * Type converters to allow Room to reference complex data types.
 */
@Module
class RepositoryModule {

    @Provides
    fun provideAllNewsFromRestApiNetworkOrFromRoom(newsDatabase: NewsDatabase, githubRepositoryApi: GithubRepositoryApi, dbMapper : DbMapper, connectivityUtil: ConnectivityUtil) : NewsRepository {
        return NewsRepositoryImpl(newsDatabase, githubRepositoryApi, dbMapper, connectivityUtil)
    }

    /*@Provides
    fun provideAllLanguagesFromRestApiNetwork(newsDatabase: NewsDatabase, githubRepositoryApi: GithubRepositoryApi, dbMapper : DbMapper) : GithubRepository {
        return GithubRepositoryImpl(newsDatabase, githubRepositoryApi, dbMapper)
    }

    @Provides
    fun provideGetAllSavedLanguages(savedLanguageDao : SavedLanguagesDAO, dbMapper : DbMapper) : SavedLanguagesRepository {
        return SavedLanguagesRepositoryImpl(savedLanguageDao, dbMapper)
    }

    @Provides
    fun provideGetAllLanguages(languagesDao : LanguagesDao, dbMapper : DbMapper) : LanguagesRepository {
        return LanguagesRepositoryImpl(languagesDao, dbMapper)
    }*/
}
