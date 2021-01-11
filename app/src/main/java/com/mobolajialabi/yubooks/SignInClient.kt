package com.mobolajialabi.yubooks

import com.google.android.gms.auth.api.signin.GoogleSignInClient

interface SignInClient {
    fun signInStarted(client: GoogleSignInClient)
}