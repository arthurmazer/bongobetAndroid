package com.mazer.bongobet.ui.login

import android.content.Context
import com.mazer.bongobet.domain.entities.User

sealed class LoginUiEvent {

    data class InitLoginGoogle(val context: Context) : LoginUiEvent()
    object InitLoginFacebook : LoginUiEvent()

    data class LoginGoogleSuccess(val user: User) : LoginUiEvent()

}
