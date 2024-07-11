package com.paranid5.cooking_corner.data.di.network

import com.paranid5.cooking_corner.data.category.CategoryApiImpl
import com.paranid5.cooking_corner.data.category.CategoryApiUrlBuilder
import com.paranid5.cooking_corner.data.di.BASE_URL_QUALIFIER
import com.paranid5.cooking_corner.data.di.CATEGORY_API
import com.paranid5.cooking_corner.domain.category.CategoryApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.multiton
import org.kodein.di.new

internal val categoryApiModule = DI.Module("categoryApiModule") {
    bind<CategoryApi>(tag = CATEGORY_API) with multiton { new(::CategoryApiImpl) }

    bind<CategoryApiUrlBuilder>() with multiton {
        CategoryApiUrlBuilder(baseUrl = instance(tag = BASE_URL_QUALIFIER))
    }
}