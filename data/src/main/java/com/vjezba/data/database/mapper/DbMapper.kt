package com.vjezba.data.database.mapper

import com.vjezba.data.database.model.DBNews
import com.vjezba.data.networking.model.ApiNews
import com.vjezba.domain.model.Articles
import io.reactivex.Flowable


interface DbMapper {


    fun mapApiNewsToDomainNews(apiNews: ApiNews): com.vjezba.domain.model.News


    fun mapDomainNewsToDbNews(newsList: ApiNews): List<DBNews>


    fun mapDBArticlesToArticles(articlesList: List<DBNews>): List<Articles>

}