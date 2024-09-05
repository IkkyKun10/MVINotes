package com.riezki.mvinote.core.data.mapper

import com.riezki.mvinote.core.data.remote.dto.ImageListDto
import com.riezki.mvinote.core.domain.model.Images

/**
 * @author riezkymaisyar
 */

fun ImageListDto.toImages() : Images {
    return Images(
        images = hits?.map { it.previewURL ?: "" } ?: emptyList()
    )

}