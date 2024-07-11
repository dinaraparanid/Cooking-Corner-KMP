package com.paranid5.cooking_corner.data.di.network

import org.kodein.di.DI

internal val networkModule = DI.Module("networkModule") {
    importAll(
        authApiModule,
        recipeApiModule,
        categoryApiModule,
        ktorClientModule,
    )
}