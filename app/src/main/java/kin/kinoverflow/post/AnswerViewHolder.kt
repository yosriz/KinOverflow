package kin.kinoverflow.post

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.squareup.picasso.Picasso
import kin.kinoverflow.R
import kin.kinoverflow.model.Answer
import kin.kinoverflow.model.Question
import kin.kinoverflow.questions.ViewHolderClickListener

class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.tv_answer) lateinit var answerBody: TextView
    @BindView(R.id.accepted_answer) lateinit var acceptedAnswer: ImageView
    @BindView(R.id.tv_votes) lateinit var votesCounter: TextView
    @BindView(R.id.tv_kin) lateinit var kinCounter: TextView

    @BindView(R.id.tv_asked_date) lateinit var date: TextView
    @BindView(R.id.profile_image) lateinit var profileImage: ImageView
    @BindView(R.id.tv_user_name) lateinit var profileName: TextView
    @BindView(R.id.tv_badges_count) lateinit var profileBadgesCount: TextView

    var listener: ViewHolderClickListener? = null

    init {
        ButterKnife.bind(this, itemView)
    }

    fun setAnswer(answer: Answer, kin: Long?) {
        date.text = answer.creationDate.toString()
        answerBody.text = answer.body
        votesCounter.text = (answer.upVoteCount - answer.downVoteCount).toString()
        kin?.let { kinCounter.text = it.toString() }
        profileName.text = answer.owner.displayName
        if (!answer.owner.link.isEmpty()) {
            Picasso.with(itemView.context)
                    .load(answer.owner.profileImage)
                    .into(profileImage)
        }
    }

    @OnClick(R.id.sponsor)
    fun onSponsorClick(){
        listener?.onClick(adapterPosition)
    }
}