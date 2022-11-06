package com.plutoisnotaplanet.exoplayerstreamingapp.application.exceptions

import java.io.IOException

sealed class ChannelExceptions: IOException() {
    data class ChannelNotFoundException(override val message: String? = "Channel not found"): ChannelExceptions()
    data class ChannelAlreadyExistsException(override val message: String? = "Channel already exists"): ChannelExceptions()
    data class EmptyChannelListException(override val message: String? = "Results not found"): ChannelExceptions()
}