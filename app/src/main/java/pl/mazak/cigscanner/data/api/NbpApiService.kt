package pl.mazak.cigscanner.data.api

import retrofit2.http.GET

interface NbpApiService {
    @GET("exchangerates/rates/a/czk?format=json")
    suspend fun getCzechCrownCourse(): NbpCourseResponse

    @GET("exchangerates/rates/a/eur?format=json")
    suspend fun getEuroCourse(): NbpCourseResponse
}