package com.example.inekecake.Session

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SessionManager(val context: Context, sessionType: String) {
    private var userSession: SharedPreferences
    private var editor: SharedPreferences.Editor

    companion object{
        // Nama Session
        const val LOGIN_SESSION = "loginSession"
        const val REMEMBERME_SESSION = "rememberMeSession"

        // Isi
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val IS_REMEMBERME = "isRememberMe"
        const val KEY_FULLNAME = "fullname"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
        const val KEY_NOHP = "noHp"
        const val KEY_PASSWORD = "password"
    }

    init {
        userSession = context.getSharedPreferences(sessionType, MODE_PRIVATE)
        editor = userSession.edit()
    }

    // LOGIN SHAREDPREFERENCES

    fun createLogin(fullname: String, username: String, email: String, noHp: String, password: String) {
        clearLogin()
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putString(KEY_FULLNAME, fullname)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_NOHP, noHp)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        if (userSession.getBoolean(IS_LOGGED_IN, false)) {
            return true
        }
        return false
    }

    fun getDataFromLoginSession(): HashMap<String, String?> {
        val dataUser = HashMap<String, String?>()
        dataUser.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, ""))
        dataUser.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, ""))
        dataUser.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, ""))
        dataUser.put(KEY_NOHP, userSession.getString(KEY_NOHP, ""))
        dataUser.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, ""))

        return dataUser
    }

    fun clearLogin() {
        editor.clear()
    }

    // REMEMBER ME SHAREDPREFERENCES

    fun createRememberMe() {
        editor.clear()
        editor.putBoolean(IS_REMEMBERME, true)
        editor.apply()
    }

    fun clearRememberMe() {
        editor.clear()
        editor.putBoolean(IS_REMEMBERME, false)
        editor.apply()
    }

    fun isRememberedMe(): Boolean {

        val bool = userSession.getBoolean(IS_REMEMBERME, false)

        if (bool) {
            return true
        }
        return false
    }
}