package com.kukufm.midhun.spacex.models

data class Links(
    var article_link: String = "",
    val flickr_images: List<String>,
    var mission_patch: String = "",
    var mission_patch_small: String = "",
    var presskit: String = "",
    val reddit_campaign: Any,
    var reddit_launch: String = "",
    val reddit_media: Any,
    val reddit_recovery: Any,
    var video_link: String = "",
    var wikipedia: String = "",
    var youtube_id: String = ""
)