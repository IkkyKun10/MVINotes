package com.riezki.mvinote.core.domain.repository

import com.riezki.mvinote.core.domain.model.Images

/**
 * @author riezkymaisyar
 */

interface ImagesRepository {
    suspend fun searchImages(query: String): Images?
}