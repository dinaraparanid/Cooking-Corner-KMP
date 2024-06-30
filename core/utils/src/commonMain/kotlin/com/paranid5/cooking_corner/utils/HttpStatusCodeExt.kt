package com.paranid5.cooking_corner.utils

import com.paranid5.cooking_corner.core.common.HttpStatusCode
import io.ktor.client.statement.HttpResponse

fun HttpResponse.toAppStatusCode() = HttpStatusCode(status.value)