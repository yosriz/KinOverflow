package kin.kinoverflow.questions

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import kin.kinoverflow.R
import kin.kinoverflow.model.Question

class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.tv_date) lateinit var date: TextView
    @BindView(R.id.tv_tags) lateinit var tags: TextView
    @BindView(R.id.tv_title) lateinit var title: TextView
    @BindView(R.id.tv_votes_counter) lateinit var votesCounter: TextView
    @BindView(R.id.tv_kin) lateinit var kinCounter: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

    fun setQuestion(question: Question, kin: Long?) {
        date.text = question.creationDate.toString()
        tags.text = question.tags.reduce(operation = { tags, tag ->
            return@reduce "$tags,$tag"
        })
        title.text = question.title
        votesCounter.text = (question.upVoteCount - question.downVoteCount).toString()
        kin?.let { kinCounter.text = it.toString() }
    }
}