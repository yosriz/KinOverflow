package kin.kinoverflow.network

import com.google.gson.annotations.SerializedName

data class Owner(
        @SerializedName("reputation") val reputation: Int = 0,
        @SerializedName("user_id") val userId: Int = 0,
        @SerializedName("user_type") val userType: String = "",
        @SerializedName("accept_rate") val acceptRate: Int = 0,
        @SerializedName("profile_image") val profileImage: String = "",
        @SerializedName("display_name") val displayName: String = "",
        @SerializedName("link") val link: String = ""
)