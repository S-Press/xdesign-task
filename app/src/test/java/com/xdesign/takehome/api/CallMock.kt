package com.xdesign.takehome.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallMock<T>(private val response: Response<T>) : Call<T> {
    companion object {
        @JvmStatic
        fun <T> mockSuccessResponse(body: T): CallMock<T> = CallMock(Response.success(body))

        @JvmStatic
        fun <T> mockErrorResponse(
            errorCode: Int,
            contentType: String,
            content: String
        ): CallMock<T> = CallMock(
            Response.error(
                errorCode,
                content.toResponseBody(contentType.toMediaTypeOrNull())
            )
        )
    }

    override fun execute(): Response<T> = response
    override fun enqueue(callback: Callback<T>?) {}
    override fun isExecuted() = false
    override fun clone(): Call<T> = this
    override fun isCanceled() = false
    override fun cancel() {}
    override fun request(): Request? = null
    override fun timeout() = Timeout()
}