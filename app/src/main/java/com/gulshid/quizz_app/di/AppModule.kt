package com.gulshid.quizz_app.di

import android.content.Context
import androidx.room.Room
import com.gulshid.quizz_app.data.local.QuizDatabase
import com.gulshid.quizz_app.data.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuizDatabase(@ApplicationContext context: Context): QuizDatabase {
        return Room.databaseBuilder(
            context,
            QuizDatabase::class.java,
            QuizDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideQuizRepository(database: QuizDatabase): QuizRepository {
        return QuizRepository(database)
    }
}
