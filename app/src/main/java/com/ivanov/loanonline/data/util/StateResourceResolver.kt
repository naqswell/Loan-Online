package com.ivanov.loanonline.data.util

import android.content.Context
import android.util.Log
import com.ivanov.loanonline.R
import com.ivanov.loanonline.data.model.remote.State

object StateResourceResolver {
    operator fun invoke(context: Context, state: State): String {
        return when (state.status) {
            State.APPROVED.status -> context.getString(R.string.status_approved)
            State.REGISTERED.status -> context.getString(R.string.status_registered)
            State.REJECTED.status -> context.getString(R.string.status_rejected)
            else -> {
                Log.e("StateResourceResolver", "bad state")
                ""
            }
        }
    }
}