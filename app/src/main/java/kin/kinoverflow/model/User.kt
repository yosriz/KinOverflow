package kin.kinoverflow.model

import com.google.gson.annotations.SerializedName


data class User(
        @SerializedName("badge_counts") val badgeCounts: BadgeCounts = BadgeCounts(),
        @SerializedName("view_count") val viewCount: Int = 0,
        @SerializedName("down_vote_count") val downVoteCount: Int = 0,
        @SerializedName("up_vote_count") val upVoteCount: Int = 0,
        @SerializedName("answer_count") val answerCount: Int = 0,
        @SerializedName("question_count") val questionCount: Int = 0,
        @SerializedName("account_id") val accountId: Int = 0,
        @SerializedName("is_employee") val isEmployee: Boolean = false,
        @SerializedName("last_modified_date") val lastModifiedDate: Int = 0,
        @SerializedName("last_access_date") val lastAccessDate: Int = 0,
        @SerializedName("age") val age: Int = 0,
        @SerializedName("reputation_change_year") val reputationChangeYear: Int = 0,
        @SerializedName("reputation_change_quarter") val reputationChangeQuarter: Int = 0,
        @SerializedName("reputation_change_month") val reputationChangeMonth: Int = 0,
        @SerializedName("reputation_change_week") val reputationChangeWeek: Int = 0,
        @SerializedName("reputation_change_day") val reputationChangeDay: Int = 0,
        @SerializedName("reputation") val reputation: Int = 0,
        @SerializedName("creation_date") val creationDate: Int = 0,
        @SerializedName("user_type") val userType: String = "",
        @SerializedName("user_id") val userId: Int = 0,
        @SerializedName("accept_rate") val acceptRate: Int = 0,
        @SerializedName("about_me") val aboutMe: String = "",
        @SerializedName("location") val location: String = "",
        @SerializedName("website_url") val websiteUrl: String = "",
        @SerializedName("link") val link: String = "",
        @SerializedName("profile_image") val profileImage: String = "",
        @SerializedName("display_name") val displayName: String = ""
)


data class BadgeCounts(
        @SerializedName("bronze") val bronze: Int = 0,
        @SerializedName("silver") val silver: Int = 0,
        @SerializedName("gold") val gold: Int = 0
)
