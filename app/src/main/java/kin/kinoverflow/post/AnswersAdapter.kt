package kin.kinoverflow.post

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import kin.kinoverflow.R
import kin.kinoverflow.model.Answer


class AnswersAdapter : RecyclerView.Adapter<AnswerViewHolder>() {

    private val answers: ArrayList<Answer> = ArrayList()
    private val kinMap: HashMap<String, Long> = HashMap()

    fun updateAnswers(answersList: List<Answer>, answersMap: Map<String, Long>) {
        answers.clear()
        answers.addAll(answersList)
        kinMap.clear()
        kinMap.putAll(answersMap)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return answers.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        return AnswerViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.answer_view_holder, parent, false)
        )
    }

    override fun onBindViewHolder(vh: AnswerViewHolder, position: Int) {
        val question = answers[position]
        Log.d("yossi", "index = $position answerId = ${question.answerId}")
        val kin = kinMap[question.questionId.toString()]

        vh.setAnswer(question, kin)
    }
}