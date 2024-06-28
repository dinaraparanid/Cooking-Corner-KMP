package com.paranid5.cooking_corner.feature.main.search.di

import com.paranid5.cooking_corner.feature.main.search.component.SearchComponent
import com.paranid5.cooking_corner.feature.main.search.component.SearchComponentImpl
import com.paranid5.cooking_corner.feature.main.search.component.SearchStoreProvider
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val searchModule = DI.Module("searchModule") {
    bind<SearchComponent.Factory>() with multiton { new(SearchComponentImpl::Factory) }
    bind<SearchStoreProvider.Factory>() with multiton { new(SearchStoreProvider::Factory) }
}