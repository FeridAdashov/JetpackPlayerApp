package com.example.playerapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.data.Constants
import com.example.domain.entity.BaseEntity
import com.example.domain.entity.RequestResult
import com.example.playerapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun <T> sendRequest(
        call: suspend () -> RequestResult<T>,
        getData: (RequestResult<T>) -> Unit
    ): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            getData.invoke(call.invoke())
        }
    }

    fun getErrorMessage(context: Context, info: BaseEntity): String {
        return when (info.code) {
            Constants.SOCKET_EXCEPTION -> info.message
                ?: context.getString(R.string.socket_exception)

            Constants.SOCKET_TIMEOUT_EXCEPTION -> context.getString(R.string.timeout_exception)
            Constants.UNKNOWN_ERROR -> info.message ?: context.getString(R.string.unknown_error)
            Constants.CONNECTION_EXCEPTION -> context.getString(R.string.check_internet_connection)
            Constants.UNAUTHORIZED_EXCEPTION -> info.message
                ?: context.getString(R.string.unauthorizedRequest)

            Constants.WRONG_DATA_EXCEPTION -> context.getString(R.string.data_parse_error)
            Constants.SSL_EXCEPTION -> context.getString(R.string.ssl_exception)
            Constants.BAD_REQUEST -> info.message ?: context.getString(R.string.have_troubles)
            Constants.NOT_FOUND -> info.message ?: context.getString(R.string.data_not_found)
            else -> info.message ?: context.getString(R.string.have_troubles)
        }
    }
}