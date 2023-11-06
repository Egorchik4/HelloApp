package com.example.helloapp.di

import android.content.Context
import com.example.helloapp.data.api.WeatherApi
import com.example.helloapp.data.datasource.RetrofitBuilder
import com.example.helloapp.data.datasource.network.WeatherDataSource
import com.example.helloapp.data.datasource.network.WeatherDataSourceImpl
import com.example.helloapp.data.repository.WeatherRepositoryImpl
import com.example.helloapp.domain.repository.WeatherRepository
import com.example.helloapp.domain.usecase.GetWeatherUseCase
import com.example.helloapp.domain.usecase.ShowSnackBarUseCase
import com.example.helloapp.domain.usecase.ValidationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

	@Provides
	@Singleton
	fun provideWeatherApi(): WeatherApi {
		return RetrofitBuilder().weatherApi
	}

	@Provides
	@Singleton
	fun provideWeatherDataSource(api: WeatherApi): WeatherDataSource {
		return WeatherDataSourceImpl(api)
	}

	@Provides
	@Singleton
	fun provideWeatherRepository(weatherDataSource: WeatherDataSource): WeatherRepository {
		return WeatherRepositoryImpl(weatherDataSource)
	}

	@Provides
	@Singleton
	fun provideWeatherUseCase(weatherRepository: WeatherRepository): GetWeatherUseCase {
		return GetWeatherUseCase(weatherRepository)
	}

	@Provides
	@Singleton
	fun provideValidationUseCase(): ValidationUseCase {
		return ValidationUseCase()
	}

	@Provides
	@Singleton
	fun provideSnackBarUseCase(@ApplicationContext context: Context): ShowSnackBarUseCase {
		return ShowSnackBarUseCase(context)
	}
}