package com.example.login.data.mapper

import com.example.login.data.local.news.NewsEntity
import com.example.login.data.remote.response.NewsDto
import com.example.login.domain.model.News

fun NewsDto.toNewsEntity(nextPage: String, fromSearch: Boolean = false): NewsEntity{
    return NewsEntity(
        article_id = article_id ?: "-1",
        category = try {
            category?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception){
            "-1,-2"
        },
        content = content ?: "",
        country = try {
            country?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception){
            "-1,-2"
        },
        description = description ?: "",
        image_url = image_url ?: "",
        link = link ?: "",
        pubDate = pubDate ?: "",
        source_id = source_id ?: "",
        title = title ?: "",
        nextPage = nextPage,
        bookmark = false,
        fromSearch = fromSearch
    )
}

fun NewsEntity.toNews(): News{
    return News(
        article_id = article_id,
        category = try {
            category.split(",")
        } catch (e: Exception){
            listOf("-1","-2")
        },
        content = content,
        country = try {
            country.split(",")
        } catch (e: Exception){
            listOf("-1","-2")
        },
        description = description,
        image_url = image_url,
        link = link,
        pubDate = pubDate,
        source_id = source_id,
        title = title,
        nextPage = nextPage,
        bookmark = bookmark,
        fromSearch = fromSearch
    )
}

fun News.toNewsEntity(): NewsEntity {
    return NewsEntity(
        article_id = article_id,
        category = category.joinToString(","),
        content = content,
        country = country.joinToString(","),
        description = description,
        image_url = image_url,
        link = link,
        pubDate = pubDate,
        source_id = source_id,
        title = title,
        nextPage = nextPage,
        bookmark = bookmark,
        fromSearch = fromSearch
    )
}