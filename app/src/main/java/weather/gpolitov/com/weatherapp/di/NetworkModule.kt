package weather.gpolitov.com.weatherapp.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import weather.gpolitov.com.weatherapp.BuildConfig
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun prodiveMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    @Singleton
    fun provideAppInterceptor(): AppInterceptor = AppInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, appInterceptor: AppInterceptor): OkHttpClient = OkHttpClient.Builder().addInterceptor(appInterceptor).addInterceptor(httpLoggingInterceptor).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        moshiConverterFactory: MoshiConverterFactory,
                        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()

}