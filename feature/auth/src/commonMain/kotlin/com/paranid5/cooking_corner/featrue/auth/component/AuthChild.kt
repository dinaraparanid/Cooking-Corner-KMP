package com.paranid5.cooking_corner.featrue.auth.component

import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInComponent
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpComponent

sealed interface AuthChild {
    class SignIn internal constructor(
        internal val component: SignInComponent
    ) : AuthChild

    class SignUp internal constructor(
        internal val component: SignUpComponent
    ) : AuthChild
}