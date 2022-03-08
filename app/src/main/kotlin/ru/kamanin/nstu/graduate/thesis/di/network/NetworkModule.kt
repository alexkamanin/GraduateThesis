package ru.kamanin.nstu.graduate.thesis.di.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.kamanin.nstu.graduate.thesis.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
		HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}

	@Provides
	@Singleton
	@NotAuthorized
	fun provideNotAuthorizedRetrofit(@NotAuthorized client: OkHttpClient): Retrofit =
		Retrofit
			.Builder()
			.baseUrl(BuildConfig.BACKEND_ENDPOINT)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.client(client)
			.build()

	@Provides
	@Singleton
	@Authorized
	fun provideAuthorizedRetrofit(@Authorized client: OkHttpClient): Retrofit =
		Retrofit
			.Builder()
			.baseUrl(BuildConfig.BACKEND_ENDPOINT)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.client(client)
			.build()

	private val moshi = Moshi
		.Builder()
		.add(KotlinJsonAdapterFactory())
		.build()
}