package com.kukufm.midhun.spacex.models

data class Core(
    val block: Int,
    var core_serial: String = "",
    val flight: Int,
    val gridfins: Boolean,
    val land_success: Boolean,
    val landing_intent: Boolean,
    var landing_type: String = "",
    var landing_vehicle: String = "",
    val legs: Boolean,
    val reused: Boolean
)