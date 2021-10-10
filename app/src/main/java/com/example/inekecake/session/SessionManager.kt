package com.example.inekecake.session

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.inekecake.model.CartModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SessionManager(val context: Context, sessionType: String) {
    private var userSession: SharedPreferences
    private var editor: SharedPreferences.Editor

    companion object{
        // Nama Session
        const val LOGIN_SESSION = "loginSession"
        const val REMEMBERME_SESSION = "rememberMeSession"
        const val CART_SESSION = "cartSession"

        // Isi
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val IS_REMEMBERME = "isRememberMe"
        const val KEY_FIRSTNAME = "firstname"
        const val KEY_LASTNAME = "lastname"
        const val KEY_EMAIL = "email"
        const val KEY_NOHP = "noHp"
        const val KEY_PASSWORD = "password"
        const val KEY_ALAMAT = "alamat"
        const val KEY_KOTA = "kota"
        const val KEY_KODEPOS = "kodepos"
        const val KEY_TGL_REGISTER = "tgl_register"
        const val KEY_DATA_CART = "data_cart"
    }

    init {
        userSession = context.getSharedPreferences(sessionType, MODE_PRIVATE)
        editor = userSession.edit()
    }

    // LOGIN SHAREDPREFERENCES

    fun createLogin(
        firstname: String,
        lastname: String,
        email: String,
        noHp: String,
        password: String,
        alamat: String,
        kota: String,
        kodepos: String,
        tgl_register: String
    ) {

        clearLogin()
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putString(KEY_FIRSTNAME, firstname)
        editor.putString(KEY_LASTNAME, lastname)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_NOHP, noHp)
        editor.putString(KEY_PASSWORD, password)
        editor.putString(KEY_ALAMAT, alamat)
        editor.putString(KEY_KOTA, kota)
        editor.putString(KEY_KODEPOS, kodepos)
        editor.putString(KEY_TGL_REGISTER, tgl_register)
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
        dataUser.put(KEY_FIRSTNAME, userSession.getString(KEY_FIRSTNAME, ""))
        dataUser.put(KEY_LASTNAME, userSession.getString(KEY_LASTNAME, ""))
        dataUser.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, ""))
        dataUser.put(KEY_NOHP, userSession.getString(KEY_NOHP, ""))
        dataUser.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, ""))
        dataUser.put(KEY_ALAMAT, userSession.getString(KEY_ALAMAT, ""))
        dataUser.put(KEY_KOTA, userSession.getString(KEY_KOTA, ""))
        dataUser.put(KEY_KODEPOS, userSession.getString(KEY_KODEPOS, ""))
        dataUser.put(KEY_TGL_REGISTER, userSession.getString(KEY_TGL_REGISTER, ""))

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
        editor.apply()
    }

    fun isRememberedMe(): Boolean {

        val bool = userSession.getBoolean(IS_REMEMBERME, false)

        if (bool) {
            return true
        }
        return false
    }


    // CART SHAREDPREFERENCES

    fun createCartSession(dataCart: ArrayList<CartModel>) {
        editor.clear()
        val gson = Gson()
        val json = gson.toJson(dataCart)
        editor.putString(KEY_DATA_CART, json)
        editor.apply()
    }

    fun getCartSession(): ArrayList<CartModel> {
        val gson = Gson()
        val json = userSession.getString(KEY_DATA_CART, "")
        val type = object : TypeToken<ArrayList<CartModel>>(){}.type
        return gson.fromJson(json, type)
    }

}