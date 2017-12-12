package kin.kinoverflow.model

import com.google.gson.annotations.SerializedName


data class Answer(
        @SerializedName("owner") val owner: Owner = Owner(),
        @SerializedName("down_vote_count") val downVoteCount: Int = 0,
        @SerializedName("up_vote_count") val upVoteCount: Int = 0,
        @SerializedName("is_accepted") val isAccepted: Boolean = false,
        @SerializedName("score") val score: Int = 0,
        @SerializedName("last_activity_date") val lastActivityDate: Int = 0,
        @SerializedName("last_edit_date") val lastEditDate: Int = 0,
        @SerializedName("creation_date") val creationDate: Int = 0,
        @SerializedName("answer_id") val answerId: Int = 0,
        @SerializedName("question_id") val questionId: Int = 0,
        @SerializedName("link") val link: String = "",
        @SerializedName("title") val title: String = "",
        @SerializedName("body") val body: String = ""
)
