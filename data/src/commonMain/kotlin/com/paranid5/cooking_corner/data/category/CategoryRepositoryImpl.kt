package com.paranid5.cooking_corner.data.category

import com.paranid5.cooking_corner.domain.category.CategoryApi
import com.paranid5.cooking_corner.domain.category.CategoryRepository

internal class CategoryRepositoryImpl(categoryApi: CategoryApi) :
    CategoryRepository,
    CategoryApi by categoryApi