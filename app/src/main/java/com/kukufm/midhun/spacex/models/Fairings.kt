package com.kukufm.midhun.spacex.models

data class Fairings(
    val recovered: Boolean,
    val recovery_attempt: Boolean,
    val reused: Boolean,
    var ship: String = ""
)