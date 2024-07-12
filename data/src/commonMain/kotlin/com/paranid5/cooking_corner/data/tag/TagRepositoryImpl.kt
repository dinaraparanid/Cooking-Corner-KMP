package com.paranid5.cooking_corner.data.tag

import com.paranid5.cooking_corner.domain.tag.TagApi
import com.paranid5.cooking_corner.domain.tag.TagRepository

internal class TagRepositoryImpl(tagApi: TagApi) :
    TagRepository,
    TagApi by tagApi