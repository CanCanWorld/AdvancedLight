package com.zrq.advancedlight.entity

/**
 * @Description:
 * @author zhangruiqian
 * @date 2023/5/19 10:54
 */
enum class MediaType {
    Photo, Video
}

data class Media(
    val path: String,
    val type: MediaType
)
