package com.example.auth_data.util

import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.auth_domain.exception.ForbiddenException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ConflictException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.NeedLoginException
import com.example.auth_domain.exception.NoInternetException
import com.example.auth_domain.exception.TimeoutException
import com.example.auth_domain.exception.UnknownException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HttpHandler<T> {

    private lateinit var httpRequest: suspend () -> T
    private var onBadRequest: (message: String) -> Throwable = { BadRequestException() }
    private var onUnauthorized: (message: String) -> Throwable = { UnauthorizedException() }
    private var onForbidden: (message: String) -> Throwable = { ForbiddenException() }
    private var onNotFound: (message: String) -> Throwable = { NotFoundException() }
    private var onConflict: (message: String) -> Throwable = { ConflictException() }
    private var onServerError: (code: Int) -> Throwable = { ServerException() }
    private var onOtherHttpStatusCode: (code: Int, message: String) -> Throwable =
        { _, _ -> UnknownException() }

    fun httpRequest(httpRequest: suspend () -> T) =
        this.apply { this.httpRequest = httpRequest }

    fun onBadRequest(onBadRequest: (message: String) -> Throwable) =
        this.apply { this.onBadRequest = onBadRequest }

    fun onUnauthorized(onUnauthorized: (message: String) -> Throwable) =
        this.apply { this.onUnauthorized = onUnauthorized }

    fun onForbidden(onForbidden: (message: String) -> Throwable) =
        this.apply { this.onForbidden = onForbidden }

    fun onNotFound(onNotFound: (message: String) -> Throwable) =
        this.apply { this.onNotFound = onNotFound }

    fun onConflict(onConflict: (message: String) -> Throwable) =
        this.apply { this.onConflict = onConflict }

    fun onServerError(onServerError: (code: Int) -> Throwable) =
        this.apply { this.onServerError = onServerError }

    fun onOtherHttpStatusCode(onOtherHttpStatusCode: (code: Int, message: String) -> Throwable) =
        this.apply { this.onOtherHttpStatusCode = onOtherHttpStatusCode }

    suspend fun sendRequest(): T =
        try {
            httpRequest()
        } catch (e: HttpException) {
            val code = e.code()
            val message = e.message()
            throw when (code) {
                400 -> onBadRequest(message)
                401 -> onUnauthorized(message)
                403 -> onForbidden(message)
                404 -> onNotFound(message)
                409 -> onConflict(message)
                500, 501, 502, 503 -> onServerError(code)
                else -> onOtherHttpStatusCode(code, message)
            }
        } catch (e: UnknownHostException) {
            throw NoInternetException()
        } catch (e: SocketTimeoutException) {
            throw TimeoutException()
        } catch (e: NeedLoginException) {
            throw e
        } catch (e: Throwable) {
            throw UnknownException()
        }
}