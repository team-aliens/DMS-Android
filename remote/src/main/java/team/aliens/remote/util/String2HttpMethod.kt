package team.aliens.remote.util

import team.aliens.remote.common.HttpMethod

internal fun String.toHttpMethod(): HttpMethod = HttpMethod.valueOf(this)
