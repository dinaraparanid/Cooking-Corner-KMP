package com.paranid5.cooking_corner.featrue.auth.component

import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInComponent

sealed interface AuthChild {
    class SignIn internal constructor(
        internal val component: SignInComponent
    ) : AuthChild

    class SignUp internal constructor() : AuthChild
}