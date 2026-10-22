package com.yongdae.photoshelf.core.data.di

import com.yongdae.photoshelf.core.data.BuildConfig
import com.yongdae.photoshelf.core.data.datasorce.remote.service.PhotosService
import com.yongdae.photoshelf.core.data.datasorce.remote.service.retrofit.RetrofitPhotosService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = BuildConfig.UNSPLASH_BASE_URL

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .header("Authorization", "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
                .build()

            chain.proceed(request)
        }
        .build()

    @Provides
    @Singleton
    fun providesPhotosService(
        networkJson: Json,
        okhttpCallFactory: Call.Factory,
    ): PhotosService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory(okhttpCallFactory)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitPhotosService::class.java)
    }
}