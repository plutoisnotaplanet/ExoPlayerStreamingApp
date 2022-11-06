package com.plutoisnotaplanet.exoplayerstreamingapp.application.exceptions

import java.io.IOException

sealed class ApiException: IOException() {
    data class HasNotInternetConnection(override val message: String? = "Has not internet connection"): ApiException()
    data class BadRequest(override val message: String? = "Bad request") : ApiException()
    data class UnauthorizedException(override val message: String? = "Unauthorized exception") : ApiException()
    data class TooManyRequests(override val message: String? = "Too many requests") : ApiException()
    data class InternalServerError(override val message: String? = "Internal server error") : ApiException()
}