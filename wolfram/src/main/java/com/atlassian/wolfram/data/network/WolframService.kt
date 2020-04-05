package com.atlassian.wolfram.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

suspend fun nthPrime(n: Int): Int? {
    val retrofit = Retrofit.Builder()
            .baseUrl("https://api.wolframalpha.com")
            .addConverterFactory(
                    MoshiConverterFactory.create(
                            Moshi.Builder()
                                    .add(KotlinJsonAdapterFactory())
                                    .build()
                    )
            )
            .build()
    val wolframService = retrofit.create(WolframAlphaApiInterface::class.java)

    val result = wolframService.wolframQuery("prime+($n)")

    return result.queryResult.pods
            .firstOrNull(Pod::primary)
            ?.subPods
            ?.first()
            ?.plainText
            ?.toIntOrNull()
}