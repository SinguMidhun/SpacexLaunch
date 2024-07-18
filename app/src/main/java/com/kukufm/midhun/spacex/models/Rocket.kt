package com.kukufm.midhun.spacex.models

data class Rocket(
    val fairings: Fairings,
    val first_stage: FirstStage,
    var rocket_id: String = "",
    var rocket_name: String = "",
    var rocket_type: String = "",
    val second_stage: SecondStage
)