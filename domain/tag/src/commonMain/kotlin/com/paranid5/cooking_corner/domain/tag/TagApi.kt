package com.paranid5.cooking_corner.domain.tag

import com.paranid5.cooking_corner.core.common.ApiResultWithCode

interface TagApi {
    suspend fun getAll(): ApiResultWithCode<List<String>>

    suspend fun create(name: String): ApiResultWithCode<Unit>

    suspend fun update(oldName: String, newName: String): ApiResultWithCode<Unit>

    suspend fun delete(name: String): ApiResultWithCode<Unit>
}
