package com.englishlearningwithngram.utils

import android.content.Context
import android.content.SharedPreferences
import com.englishlearningwithngram.model.User

class SharedPrefManager(context: Context) {

    val sharedPref: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun saveString(idUser: String, email: String, nama: String, username: String, created: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(IDUSER, idUser)
        editor.putString(EMAIL, email)
        editor.putString(NAMA, nama)
        editor.putString(USERNAME, username)
        editor.putString(CREATED, created)
        editor.apply()
    }

    fun saveDataUser(user: User?) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(IDUSER, user?.id_user)
        editor.putString(EMAIL, user?.email)
        editor.putString(NAMA, user?.nama)
        editor.putString(USERNAME, user?.username)

    }

    fun getValueUser(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putInt(KEY_NAME, value)

        editor.apply()
    }

    fun saveStatusLogin(status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(STATUS_LOGIN, status)
        editor.apply()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean(STATUS_LOGIN, false)
    }

    fun logout() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(EMAIL)
        editor.remove(NAMA)
        editor.remove(USERNAME)
        editor.remove(IDUSER)
        editor.remove(CREATED)
        editor.remove(STATUS_LOGIN)
        editor.apply()
    }

    companion object {
        const val PREFS_NAME = "englishNgram"
        const val IDUSER = "id_user"
        const val NAMA = "nama"
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val EMAIL = "email"
        const val CREATED = "dibuat_tanggal"
        const val STATUS_LOGIN = "status_login"
        val IS_LOGIN = Pair("is_login", false)
    }
}
