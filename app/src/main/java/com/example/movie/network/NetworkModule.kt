package com.example.movie.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_KEY = "c0e9332895177838a06cf5ad52bd3f6b" // Hardcoded API Key (not required in the request anymore)
    private const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMGU5MzMyODk1MTc3ODM4YTA2Y2Y1YWQ1MmJkM2Y2YiIsIm5iZiI6MTcwMjUzNDQwMi44ODYwMDAyLCJzdWIiOiI2NTdhOWQwMjdhM2M1MjAwYWQxYTU0YTAiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.Ij_d_jop73MDIsYMYoNwfWKqvpJJ_RUj7fKVuTGtEkE" // Hardcoded Access Token
    private const val BASE_URL = "https://api.themoviedb.org/3/" // Base URL

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log both request and response bodies
        }

        // Interceptor to add Authorization Bearer token statically
        val authInterceptor = Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer $ACCESS_TOKEN") // Use the fixed access token in the Authorization header
                .build()
            chain.proceed(newRequest)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)  // Log request/response
            .addInterceptor(authInterceptor)     // Add Authorization header with access token
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MyApiService =
        retrofit.create(MyApiService::class.java)
}
