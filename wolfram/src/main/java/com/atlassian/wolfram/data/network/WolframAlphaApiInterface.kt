package com.atlassian.wolfram.data.network

import com.squareup.moshi.Json
import retrofit2.http.GET
import retrofit2.http.Query

interface WolframAlphaApiInterface {
    // TODO use suspend https://issuetracker.google.com/issues/143468771
    @GET("/v2/query?format=plaintext&output=JSON&appid=GKX4PH-K2P98U8L4E&")
    suspend fun wolframQuery(@Query("input") query: String): Result
}


/// GKX4PH-K2P98U8L4E
data class Result(@Json(name = "queryresult") val queryResult: QueryResult)
data class QueryResult(val pods: List<Pod>)
data class Pod(
        @Json(name = "primary") val primary: Boolean = false,
        @Json(name = "subpods") val subPods: List<SubPod>
)

data class SubPod(@Json(name = "plaintext") val plainText: String)