package project.achsan.quotesapps.data.remote

import project.achsan.quotesapps.BuildConfig
import project.achsan.quotesapps.models.Login
import project.achsan.quotesapps.models.Message
import project.achsan.quotesapps.models.QuoteResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("registration_number") nim: String,
        @Field("password") password: String
    ): Call<Login>
    @GET("${BuildConfig.API_VERSION}myquotes")
    fun getMyQuotes(
        @Header("Authorization") token:String?
    ): Call<QuoteResponse>
    @GET("${BuildConfig.API_VERSION}class_quotes")
    fun getClassQuotes(
        @Header("Authorization") token:String?
    ): Call<QuoteResponse>
    @GET("${BuildConfig.API_VERSION}quotes")
    fun getAllQuotes(
        @Header("Authorization") token:String?
    ): Call<QuoteResponse>
    @FormUrlEncoded
    @POST("${BuildConfig.API_VERSION}quotes")
    fun addQuote(
        @Header("Authorization") token:String,
        @Field("name") name: String,
        @Field("description") description: String
    ): Call<Message>
    @FormUrlEncoded
    @PUT("${BuildConfig.API_VERSION}quotes/{quote_id}")
    fun updateQuote(
        @Header("Authorization") token:String,
        @Path("quote_id") quote_id: String,
        @Field("name") title: String,
        @Field("description") description: String
    ): Call<Message>
    @DELETE("${BuildConfig.API_VERSION}quotes/{quote_id}")
    fun deleteQuote(
        @Header("Authorization") token:String,
        @Path("quote_id") quote_id: String
    ): Call<Message>
}