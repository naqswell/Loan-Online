package com.ivanov.loanonline.presentation.utils

import android.content.res.Resources
import com.ivanov.loanonline.R
import retrofit2.HttpException
import java.io.IOException
import java.lang.RuntimeException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class NetworkErrorStringProvider @Inject constructor(
    private val resources: Resources
) {
    private fun getString(id: Int) = resources.getString(id)

    operator fun invoke(t: Throwable) =
        when (t) {
            is IOException -> getString(R.string.error_io_exception)
            is HttpException -> when (t.code()) {
                401 -> getString(R.string.error_auth)
                else -> getString(R.string.error_403)
            }
            is RuntimeException -> getString(R.string.error_io_exception)
            is TimeoutException -> getString(R.string.error_timeout_exception)
            else -> getString(R.string.error_403)

        }
}