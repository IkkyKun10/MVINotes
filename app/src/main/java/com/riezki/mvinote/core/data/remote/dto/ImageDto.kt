package com.riezki.mvinote.core.data.remote.dto

/**
 * @author riezkymaisyar
 */

data class ImageDto (
    val previewURL: String?
)

data class ImageListDto(
    val hits: List<ImageDto>?
)