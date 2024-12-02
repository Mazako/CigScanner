package pl.mazak.cigscanner.data.api

import kotlinx.serialization.Serializable

@Serializable
data class NbpCourseResponse(
    val rates: List<Rates>
)

@Serializable
data class Rates(
    val mid: Double
)