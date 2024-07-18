package com.kukufm.midhun.spacex.models

data class SecondStage(
    val block: Int,
    val payloads: List<Payload>
)