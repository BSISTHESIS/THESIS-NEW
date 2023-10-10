package com.joey.kotlinandroidbeginning.API

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @Headers(
        "Content-Type: application/json; charset=UTF-8",
        "Accept: application/json; charset=UTF-8"
    )
    @POST("API_COFFEE")
    fun getData(
        @Body body: Map<String, String>
    ): Observable<ResponseModels.DefaultResponse>?


    @Headers(
        "Content-Type: application/json; charset=UTF-8",
        "Accept: application/json; charset=UTF-8"
    )
    @POST("API_COFFEE")
    fun requestToServer(
        @Body body: Map<String, String>
    ): Observable<ResponseModels.UserResponse>?

    @Headers(
        "Content-Type: application/json; charset=UTF-8",
        "Accept: application/json; charset=UTF-8"
    )
    @POST("API_COFFEE")
    fun requestAppVersion(
        @Body body: Map<String, String>
    ): Observable<ResponseModels.AppVersionCode>?


}