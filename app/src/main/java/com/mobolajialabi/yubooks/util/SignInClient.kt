package com.mobolajialabi.yubooks.util

import com.google.android.gms.auth.api.signin.GoogleSignInClient

interface SignInClient {
    fun signInStarted(client: GoogleSignInClient)
}