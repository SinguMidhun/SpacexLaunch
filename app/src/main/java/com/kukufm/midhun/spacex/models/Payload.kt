package com.kukufm.midhun.spacex.models

data class Payload(
    val customers: List<String>,
    var manufacturer: String = "",
    var nationality: String = "",
    val norad_id: List<Int>,
    var orbit: String = "",
    val orbit_params: OrbitParams,
    var payload_id: String = "",
    var payload_mass_kg: Float = 0f,
    var payload_mass_lbs: Float = 0f,
    var payload_type: String = "",
    val reused: Boolean
)