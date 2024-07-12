package com.paranid5.cooking_corner.data.tag

import arrow.core.Either
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.data.auth.withAuth
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.tag.TagApi
import com.paranid5.cooking_corner.domain.tag.dto.TagRequest
import com.paranid5.cooking_corner.domain.tag.dto.UpdateTagRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.withContext

internal class TagApiImpl(
    private val ktorClient: HttpClient,
    private val urlBuilder: TagApiUrlBuilder,
    private val authRepository: AuthRepository,
) : TagApi {
    override suspend fun getAll(): ApiResultWithCode<List<String>> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.get(urlBuilder.buildGetAllUrl()) {
                    bearerAuth(accessToken)
                }
            }
        }
    }

    override suspend fun create(name: String): ApiResultWithCode<Unit> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.post(urlBuilder.buildCreateUrl()) {
                    bearerAuth(accessToken)
                    contentType(ContentType.Application.Json)
                    setBody(TagRequest(name = name))
                }
            }
        }
    }

    override suspend fun update(oldName: String, newName: String): ApiResultWithCode<Unit> =
        Either.catch {
            authRepository.withAuth { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.put(urlBuilder.buildUpdateUrl()) {
                        bearerAuth(accessToken)
                        contentType(ContentType.Application.Json)
                        setBody(UpdateTagRequest(oldName = oldName, newName = newName))
                    }
                }
            }
        }

    override suspend fun delete(name: String): ApiResultWithCode<Unit> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.delete(urlBuilder.buildDeleteUrl()) {
                    bearerAuth(accessToken)
                    contentType(ContentType.Application.Json)
                    setBody(TagRequest(name = name))
                }
            }
        }
    }
}
