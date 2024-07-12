package com.paranid5.cooking_corner.data.di.network

import com.paranid5.cooking_corner.data.di.BASE_URL_QUALIFIER
import com.paranid5.cooking_corner.data.di.TAG_API
import com.paranid5.cooking_corner.data.tag.TagApiImpl
import com.paranid5.cooking_corner.data.tag.TagApiUrlBuilder
import com.paranid5.cooking_corner.domain.tag.TagApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.multiton
import org.kodein.di.new

internal val tagApiModule = DI.Module("tagApiModule") {
    bind<TagApi>(tag = TAG_API) with multiton { new(::TagApiImpl) }

    bind<TagApiUrlBuilder>() with multiton {
        TagApiUrlBuilder(baseUrl = instance(tag = BASE_URL_QUALIFIER))
    }
}