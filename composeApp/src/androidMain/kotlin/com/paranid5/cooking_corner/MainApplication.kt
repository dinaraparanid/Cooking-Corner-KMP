package com.paranid5.cooking_corner

import android.app.Application
import com.paranid5.cooking_corner.di.initKodein
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule

class MainApplication : Application(), DIAware {
    override val di: DI = initKodein {
        import(androidXModule(this@MainApplication))
    }
}