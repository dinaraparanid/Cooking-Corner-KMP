rootProject.name = "CookingCorner"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://androidx.dev/storage/compose-compiler/repository")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://androidx.dev/storage/compose-compiler/repository")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
}

include(":composeApp")

include(":core:common")
include(":core:ui")
include(":core:component")
include(":core:utils")

include(":domain:auth")
include(":domain:global_event")
include(":domain:snackbar")
include(":domain:recipe")
include(":domain:tag")
include(":domain:category")

include(":data")

include(":feature:splash")
include(":feature:auth")
include(":feature:main:splash")
include(":feature:main:root")
include(":feature:main:content")
include(":feature:main:home")
include(":feature:main:search")
include(":feature:main:profile")
include(":feature:main:recipe")
include(":feature:main:generate")
include(":feature:main:recipe_editor")
include(":feature:main:profile_editor")
