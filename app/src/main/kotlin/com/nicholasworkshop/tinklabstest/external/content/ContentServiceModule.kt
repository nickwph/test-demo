package com.nicholasworkshop.tinklabstest.external.content

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://tinklabs-test.firebaseio.com/content/"

@Module
class ContentServiceModule {

    @Provides
    @Singleton
    internal fun contentService(): ContentService {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ContentService::class.java)
    }
}
