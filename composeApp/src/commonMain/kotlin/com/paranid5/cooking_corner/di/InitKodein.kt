package com.paranid5.cooking_corner.di

import org.kodein.di.DI

fun initKodein(init: DI.MainBuilder.() -> Unit = {}) = DI {
    init()
    import(appModule)
}