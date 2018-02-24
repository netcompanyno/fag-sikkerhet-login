package com.netcompany.coe.login

interface UserBean {
    var user : String?
    fun isLoggedIn(): Boolean = false
    fun isWebContext(): Boolean = false
}
