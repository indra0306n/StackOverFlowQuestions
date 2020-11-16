package com.indra.stackoverflowquestions.model

import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getQuestions(pageNumber: Int, pageSize: Int, order: String, sort: String, site: String)
            = apiService.getQuestions(pageNumber, pageSize, order, sort, site)
}