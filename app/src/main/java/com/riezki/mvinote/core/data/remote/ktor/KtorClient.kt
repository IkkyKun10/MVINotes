package com.riezki.mvinote.core.data.remote.ktor

import android.content.Context
import com.riezki.mvinote.core.data.remote.dto.ImageListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * @author riezkymaisyar
 */

class KtorClient(
    private val context: Context
) {
    private val hostEngine = HttpClient(OkHttp) {

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                parameters.append("key", API_KEY)
            }
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun searchImage(query: String) = hostEngine.get {
        parameter("q", query)
        parameters {
            append("q", query)
        }
    }.body<ImageListDto?>()

    companion object {
        const val BASE_URL = "pixabay.com"
        const val API_KEY = "43875405-6c70a3c2a57ec40d3140e2032"
    }
}