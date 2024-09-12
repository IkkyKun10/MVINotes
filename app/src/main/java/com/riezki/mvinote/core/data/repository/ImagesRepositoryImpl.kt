package com.riezki.mvinote.core.data.repository

import com.riezki.mvinote.core.data.mapper.toImages
import com.riezki.mvinote.core.data.remote.ktor.KtorClient
import com.riezki.mvinote.core.domain.model.Images
import com.riezki.mvinote.core.domain.repository.ImagesRepository

/**
 * @author riezkymaisyar
 */

class ImagesRepositoryImpl(
    private val ktorClient: KtorClient
) : ImagesRepository {

    override suspend fun searchImages(query: String): Images? {
        return ktorClient.searchImage(query)?.toImages()
    }
}