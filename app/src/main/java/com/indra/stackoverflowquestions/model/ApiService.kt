package com.indra.stackoverflowquestions.model

import com.indra.stackoverflowquestions.data.APIResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("questions")
    suspend fun getQuestions(
        @Query("page") page: Int,
        @Query("pagesize") pagesize: Int,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("site") site: String
    ): APIResponse

}