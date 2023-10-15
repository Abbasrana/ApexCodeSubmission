package com.apex.codeassesment.di

import com.apex.codeassesment.data.remote.ApiAgent
import com.apex.codeassesment.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiAgent.BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesRemoteDataSource(apiAgent: ApiAgent) = RemoteDataSource(apiAgent)

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiAgent {
        return retrofit.create(ApiAgent::class.java)
    }
}