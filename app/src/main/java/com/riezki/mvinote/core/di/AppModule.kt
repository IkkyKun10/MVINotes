package com.riezki.mvinote.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.riezki.mvinote.add_note.domain.usecase.SearchImagesUseCase
import com.riezki.mvinote.add_note.domain.usecase.UpsertNoteUseCase
import com.riezki.mvinote.core.data.local.NoteDb
import com.riezki.mvinote.core.data.remote.ktor.KtorClient
import com.riezki.mvinote.core.data.repository.ImagesRepositoryImpl
import com.riezki.mvinote.core.data.repository.NoteRepositoryImpl
import com.riezki.mvinote.core.domain.repository.ImagesRepository
import com.riezki.mvinote.core.domain.repository.NoteRepository
import com.riezki.mvinote.note_list.domain.usecase.DeleteNotes
import com.riezki.mvinote.note_list.domain.usecase.GetAllNotes
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
    fun provideNoteDb(context: Application): NoteDb {
        return Room.databaseBuilder(
            context,
            NoteDb::class.java,
            "note.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDb): NoteRepository {
        return NoteRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideImagesRepository(ktorClient: KtorClient): ImagesRepository {
        return ImagesRepositoryImpl(ktorClient)
    }

    @Provides
    @Singleton
    fun provideKtorClient(@ApplicationContext context: Context): KtorClient {
        return KtorClient(context)
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(repository: NoteRepository): GetAllNotes {
        return GetAllNotes(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteNotesUseCase(repository: NoteRepository): DeleteNotes {
        return DeleteNotes(repository)
    }

    @Provides
    @Singleton
    fun provideUpsertNoteUseCase(repository: NoteRepository): UpsertNoteUseCase {
        return UpsertNoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchImagesUseCase(imagesRepository: ImagesRepository): SearchImagesUseCase {
        return SearchImagesUseCase(imagesRepository)
    }

}