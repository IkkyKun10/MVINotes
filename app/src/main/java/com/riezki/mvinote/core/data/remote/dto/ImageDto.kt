package com.riezki.mvinote.core.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * @author riezkymaisyar
 */

@Serializable
data class ImageDto (
    val previewURL: String?
)

@Serializable
data class ImageListDto(
    val hits: List<ImageDto>?
)