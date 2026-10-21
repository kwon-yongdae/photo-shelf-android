package com.yongdae.photoshelf.core.data.datasorce.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoStatisticsDto(
    @SerialName("id") val id: String,
    @SerialName("downloads") val downloads: StatisticsMetricDto,
    @SerialName("views") val views: StatisticsMetricDto,
)

@Serializable
data class StatisticsMetricDto(
    @SerialName("total") val total: Long,
    @SerialName("historical") val historical: StatisticsHistoryDto,
)

@Serializable
data class StatisticsHistoryDto(
    @SerialName("change") val change: Long,
    @SerialName("resolution") val resolution: String,
    @SerialName("quantity") val quantity: Int,
    @SerialName("values") val values: List<StatisticsValueDto>,
)

@Serializable
data class StatisticsValueDto(
    @SerialName("date") val date: String,
    @SerialName("value") val value: Long,
)