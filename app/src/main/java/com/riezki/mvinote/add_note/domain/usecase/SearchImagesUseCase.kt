package com.riezki.mvinote.add_note.domain.usecase

import com.riezki.mvinote.add_note.utils.ErrorType
import com.riezki.mvinote.add_note.utils.Resource
import com.riezki.mvinote.core.domain.model.Images
import com.riezki.mvinote.core.domain.repository.ImagesRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException

/**
 * @author riezkymaisyar
 */

class SearchImagesUseCase(
    private val imagesRepository: ImagesRepository,
) {
    suspend operator fun invoke(
        query: String,
    ): Flow<Resource<Images>> {
        return flow {
            emit(Resource.Loading())
            if (query.isEmpty()) {
                emit(Resource.Error(null, "Query cannot be empty", null))
                return@flow
            }

            val images = try {
                imagesRepository.searchImages(query)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        ErrorType.IO_EXCEPTION,
                        "Couldn't reach server, check your internet connection",
                        null
                    )
                )
                return@flow
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.CLIENT_EXCEPTION, e.message, null)
                )
                return@flow
            } catch (e: ServerResponseException) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.SERVER_EXCEPTION, e.message, null)
                )
                return@flow
            } catch (e: SerializationException) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.SERIALIZATION_EXCEPTION, e.message.toString(), null)
                )
                return@flow
            } catch (e: HttpRequestTimeoutException) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.TIMEOUT_EXCEPTION, e.message.toString(), null)
                )
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        ErrorType.UNKNOWN_EXCEPTION,
                        "Unknown error occurred",
                        null
                    )
                )
                return@flow
            }

            images?.let {
                emit(Resource.Success(it))
            }
        }
    }
}