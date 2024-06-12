package com.paranid5.cooking_corner.component.root

sealed interface RootChild {
    data object Main : RootChild
}