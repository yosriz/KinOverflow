package kin.kinoverflow.network

import com.google.gson.annotations.SerializedName


data class Question(
        @SerializedName("tags") val tags: List<String> = listOf(),
        @SerializedName("owner") val owner: Owner = Owner(),
        @SerializedName("is_answered") val isAnswered: Boolean = false,
        @SerializedName("view_count") val viewCount: Int = 0,
        @SerializedName("favorite_count") val favoriteCount: Int = 0,
        @SerializedName("down_vote_count") val downVoteCount: Int = 0,
        @SerializedName("up_vote_count") val upVoteCount: Int = 0,
        @SerializedName("answer_count") val answerCount: Int = 0,
        @SerializedName("score") val score: Int = 0,
        @SerializedName("last_activity_date") val lastActivityDate: Int = 0,
        @SerializedName("creation_date") val creationDate: Int = 0,
        @SerializedName("last_edit_date") val lastEditDate: Int = 0,
        @SerializedName("question_id") val questionId: Int = 0,
        @SerializedName("link") val link: String = "",
        @SerializedName("title") val title: String = "",
        @SerializedName("body") val body: String = ""
)