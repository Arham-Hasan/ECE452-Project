package com.example.tracktor.common.functions

fun String.isValidEmail(): Boolean{
    return this.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean{
    return this.length > 7 && this.isNotBlank()
}

fun String.passwordsMatch(repeated:String): Boolean{
    return this == repeated
}