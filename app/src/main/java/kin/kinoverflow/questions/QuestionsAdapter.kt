package kin.kinoverflow.questions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kin.kinoverflow.R
import kin.kinoverflow.model.Question


class QuestionsAdapter : RecyclerView.Adapter<QuestionViewHolder>() {

    private var questions: ArrayList<Question> = ArrayList()

    fun updateQuestions(questions: List<Question>) {
        this.questions.addAll(questions)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.question_view_holder, parent, false)
        )
    }

    override fun onBindViewHolder(vh: QuestionViewHolder, position: Int) {
        vh.setQuestion(questions[position])
    }
}

