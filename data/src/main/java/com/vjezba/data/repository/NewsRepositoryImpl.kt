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

package com.vjezba.data.repository

import android.util.Log
import com.vjezba.data.database.NewsDatabase
import com.vjezba.data.database.mapper.DbMapper
import com.vjezba.data.database.model.DBNews
import com.vjezba.data.networking.ConnectivityUtil
import com.vjezba.data.networking.GithubRepositoryApi
import com.vjezba.data.networking.model.ApiNews
import com.vjezba.domain.model.Articles
import com.vjezba.domain.model.News
import com.vjezba.domain.repository.NewsRepository
import io.reactivex.Flowable

/**
 * RepositoryResponseApi module for handling data operations.
 */
class NewsRepositoryImpl constructor(
    private val dbNews: NewsDatabase,
    private val service: GithubRepositoryApi,
    private val dbMapper: DbMapper?,
    private val connectivityUtil: ConnectivityUtil
) : NewsRepository {

    // example, practice of rxjava2
    override suspend fun getNews(): Flowable<News> {
        if (connectivityUtil.isConnectedToInternet()) {
            val newsResult = service.searchGithubRepositoryWithFlowable()

            insertNewsIntoDB(newsResult)

            //Observable.concatArrayEager(newsResult, observableFromDB)

            val correctNewsResult = newsResult.map { dbMapper?.mapApiNewsToDomainNews(it)!! }

            return correctNewsResult
        } else {
            val listDbArticles = getArticlesFromDb()
            return Flowable.just(News("", "", "", listDbArticles))
        }
    }

    private fun insertNewsIntoDB(repositoryResult: Flowable<ApiNews>) {
        dbNews.newsDao().insertAllNews(
            dbMapper?.mapDomainNewsToDbNews(repositoryResult.blockingFirst()) ?: listOf()
        )
        Log.d(
            "da li ce uci unutra * ",
            "da li ce uci unutra, spremiti podatke u bazu podataka: " + toString() )

        /*repositoryResult.doOnNext {
            Log.d("da li ce uci unutra * ", "da li ce uci unutra, spremiti podatke u bazu podataka: " + it.toString())
            val test = dbMapper?.mapDomainNewsToDbNews(it) ?: listOf()
            dbNews.newsDao().insertAllNews(test)
        }*/
    }

    private suspend fun getArticlesFromDb(): List<Articles> {
        return dbNews.newsDao().getNews().map {
            dbMapper?.mapDBNewsListToNormalNewsList(it) ?: Articles(
                0,
                "",
                "",
                "",
                "",
                "",
                ""
            )
        }
    }


}
