package com.example.localdbcachingtest.data.common.exception

import okio.IOException

class NoInternetException: IOException() {
    override val message: String
        get() = "Вы оффлайн"
}