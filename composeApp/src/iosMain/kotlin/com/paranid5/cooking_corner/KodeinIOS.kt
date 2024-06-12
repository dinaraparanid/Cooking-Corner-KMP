package com.paranid5.cooking_corner

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import com.paranid5.cooking_corner.component.root.RootComponent
import com.paranid5.cooking_corner.di.initKodein
import org.kodein.di.instance

object KodeinIOS {
    fun initializeWithRootComponent(): RootComponent {
        val kodein = initKodein()
        val rootComponentFactory: RootComponent.Factory by kodein.instance()
        return rootComponentFactory.create(DefaultComponentContext(ApplicationLifecycle()))
    }
}