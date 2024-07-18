package com.kukufm.midhun.spacex.models

data class LaunchInfoModel(
    val crew: Any,
    var details: String = "",
    val flight_number: Int,
    val is_tentative: Boolean,
    var launch_date_local: String = "",
    val launch_date_unix: Int,
    var launch_date_utc: String = "",
    val launch_site: LaunchSite,
    val launch_success: Boolean,
    val launch_window: Int,
    var launch_year: String = "",
    val links: Links,
    val mission_id: List<String>,
    var mission_name: String = "",
    val rocket: Rocket,
    val ships: List<String>,
    val static_fire_date_unix: Int,
    var static_fire_date_utc: String = "",
    val tbd: Boolean,
    val telemetry: Telemetry,
    var tentative_max_precision: String = "",
    val timeline: Timeline,
    val upcoming: Boolean
)