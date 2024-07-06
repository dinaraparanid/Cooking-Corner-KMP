package com.paranid5.cooking_corner

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.component.root.RootComponent
import com.paranid5.cooking_corner.di.initKodein
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.featrue.auth.component.AuthComponent
import com.paranid5.cooking_corner.feature.main.root.component.MainRootComponent
import com.paranid5.cooking_corner.feature.splash.component.SplashScreenComponent
import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instanceOrNull
import kotlin.test.Test
import kotlin.test.assertNotNull

class DITest : DIAware {
    override val di: DI = initKodein()

    @Test
    fun dataModuleTest() {
        assertNotNull(instanceOrNull<HttpClient>())
        assertNotNull(instanceOrNull<AuthRepository>())
        assertNotNull(instanceOrNull<RecipeRepository>())
    }

    @Test
    fun storeFactoryModuleTest() {
        assertNotNull(instanceOrNull<StoreFactory>())
    }

    @Test
    fun splashScreenModuleTest() {
        assertNotNull(instanceOrNull<SplashScreenComponent.Factory>())
    }

    @Test
    fun authModuleTest() {
        assertNotNull(instanceOrNull<AuthComponent.Factory>())
    }

    @Test
    fun mainModuleTest() {
        assertNotNull(instanceOrNull<MainRootComponent.Factory>())
    }

    @Test
    fun appModuleTest() {
        assertNotNull(instanceOrNull<RootComponent.Factory>())
    }
}