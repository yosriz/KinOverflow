package kin.kinoverflow.network

import com.google.gson.annotations.SerializedName
import kin.kinoverflow.model.Question


data class StackOverflowQuery<T>(
        @SerializedName("items") val items: List<T> = listOf(),
        @SerializedName("has_more") val hasMore: Boolean = false,
        @SerializedName("quota_max") val quotaMax: Int = 0,
        @SerializedName("quota_remaining") val quotaRemaining: Int = 0
)