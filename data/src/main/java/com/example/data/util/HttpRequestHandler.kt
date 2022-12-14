
package com.example.data.util

import com.example.domain.exception.BadRequestException
import com.example.domain.exception.ConflictException
import com.example.domain.exception.ForbiddenException
import com.example.domain.exception.NeedLoginException
import com.example.domain.exception.NoInternetException
import com.example.domain.exception.NotFoundException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TimeoutException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.domain.exception.UnknownException
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> sendHttpRequest(
    httpRequest: suspend () -> T,
    onBadRequest: (message: String) -> Throwable = { BadRequestException() },
    onUnauthorized: (message: String) -> Throwable = { UnauthorizedException() },
    onForbidden: (message: String) -> Throwable = { ForbiddenException() },
    onNotFound: (message: String) -> Throwable = { NotFoundException() },
    onConflict: (message: String) -> Throwable = { ConflictException() },
    onTooMany: (message: String) -> Throwable = { TooManyRequestException() },
    onServerError: (code: Int) -> Throwable = { ServerException() },
    onOtherHttpStatusCode: (code: Int, message: String) -> Throwable = { _, _ -> UnknownException() }
): T =
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
            429 -> onTooMany(message)
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
