package com.paranid5.cooking_corner.domain.category

import com.paranid5.cooking_corner.core.common.ApiResultWithCode

interface CategoryApi {
    suspend fun getAll(): ApiResultWithCode<List<String>>
}