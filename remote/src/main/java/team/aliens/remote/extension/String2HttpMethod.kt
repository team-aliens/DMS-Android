package team.aliens.remote.extension

import team.aliens.remote.common.HttpMethod

internal fun String.toHttpMethod(): HttpMethod = HttpMethod.valueOf(this)
