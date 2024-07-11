package com.paranid5.cooking_corner.data.category

internal class CategoryApiUrlBuilder(private val baseUrl: String) {
    fun buildGetAllUrl() = "$baseUrl/categories/get_all"
}