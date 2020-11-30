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
import com.vjezba.data.networking.GithubRepositoryApi
import com.vjezba.data.networking.model.ApiNews
import com.vjezba.data.networking.model.mapToNewsDomain
import com.vjezba.domain.model.News
import com.vjezba.domain.repository.NewsRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import java.lang.Exception
import java.net.UnknownHostException
import java.util.concurrent.Flow

/**
 * RepositoryResponseApi module for handling data operations.
 */
class NewsRepositoryImpl  constructor(
    private val dbNews: NewsDatabase,
    private val service: GithubRepositoryApi,
    private val dbMapper: DbMapper?)
    : NewsRepository   {

    private var initDatabaseNewsList: MutableList<DBNews> = mutableListOf()

    // example, practice of rxjava2
    override fun getNews(): Flowable<News> {
        return try {
            val repositoryResult = service.searchGithubRepositoryWithFlowable() //.map { dbMapper!!.mapDomainNewsToDbNews(it) }!! //?: Flowable<RepositoryResponse(0, false, listOf<RepositoryDetailsResponse>())>

            //var newsFromRxjava2 = DBNews("", "", "", listOf())
            //val news = repositoryResult.doOnNext { newsFromRxjava2 = it.mapToNewsDomain() }

            repositoryResult.doOnNext {
                val test = dbMapper?.mapDomainNewsToDbNews(it) ?: listOf()
                dbNews.newsDao().insertAllNews(test)
            }

            val observableFromDB = dbNews.newsDao().getNewsRxJava2()

            //Observable.concatArrayEager(repositoryResult, observableFromDB)

            //Observable.concatArray(repositoryResult, observableFromDB)

            val testFlowable = repositoryResult.map { dbMapper?.mapApiNewsToDomainNews(it)!! }  ?: Flowable.just(News("","","", listOf()))

            return testFlowable
        }
        /*catch (e: UnknownHostException) {
            // if user does not have network connection, then display old data from room, but only once
            if( !initDatabaseNewsList.containsAll(dbNews.newsDao().getNews())  ) {
                val listDbArticles = dbNews.newsDao().getNews()
                val listArticles = dbMapper?.mapDBArticlesToArticles(listDbArticles)
                initDatabaseNewsList.addAll(listDbArticles)
                Log.d("Da li ce uci sim", "Da li ce uci sim. prikazujemo stare podatke od room, od nase baze podatakaa")
                val proba = dbNews.newsDao().getNewsRxJava2()
                //Result.Success(listArticles)
            }
            else {
                Log.d("Da li ce uci sim", "Da li ce uci sim. saljemo prazno listu")
                return Flowable.just(News("","","", listOf()))
            }
        }*/
        catch (e: Exception) {
            return Flowable.just(News("","","", listOf()))
        }
    }


}
