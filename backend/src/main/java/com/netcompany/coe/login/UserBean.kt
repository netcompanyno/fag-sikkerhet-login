package com.netcompany.coe.login

interface UserBean {
    var username : String?
    fun isLoggedIn(): Boolean = false
    fun isWebContext(): Boolean = false
}
