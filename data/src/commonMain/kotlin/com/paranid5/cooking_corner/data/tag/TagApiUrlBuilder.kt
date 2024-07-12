package com.paranid5.cooking_corner.data.tag

internal class TagApiUrlBuilder(private val baseUrl: String) {
    fun buildGetAllUrl() = "$baseUrl/tags/get_all"

    fun buildCreateUrl() = "$baseUrl/tags/create"

    fun buildUpdateUrl() = "$baseUrl/tags/update"

    fun buildDeleteUrl() = "$baseUrl/tags/delete"
}