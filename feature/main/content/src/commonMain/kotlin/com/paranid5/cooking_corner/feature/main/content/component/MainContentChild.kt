package com.paranid5.cooking_corner.feature.main.content.component

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.feature.main.home.component.HomeComponent
import com.paranid5.cooking_corner.feature.main.profile.component.ProfileComponent
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent
import com.paranid5.cooking_corner.feature.main.search.component.SearchComponent

@Immutable
sealed interface MainContentChild {
    @Immutable
    class Search internal constructor(
        internal val component: SearchComponent
    ) : MainContentChild

    @Immutable
    class Home internal constructor(
        internal val component: HomeComponent
    ) : MainContentChild

    @Immutable
    class Profile internal constructor(
        internal val component: ProfileComponent
    ) : MainContentChild

    @Immutable
    class RecepieDetails internal constructor(
        internal val component: RecipeComponent
    ) : MainContentChild
}