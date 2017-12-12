package kin.kinoverflow.questions

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import kin.kinoverflow.R
import kin.kinoverflow.model.Question


class QuestionsAdapter : RecyclerView.Adapter<QuestionViewHolder>() {

    private val questions: ArrayList<Question> = ArrayList()
    private val kinMap: HashMap<String, Long> = HashMap()

    fun updateQuestions(pair: Pair<List<Question>, Map<String, Long>>) {
        questions.clear()
        questions.addAll(pair.first)
        kinMap.clear()
        kinMap.putAll(pair.second)
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
        val question = questions[position]
        Log.d("yossi", "index = $position questionId = ${question.questionId}")
        val kin = kinMap[question.questionId.toString()]

        vh.setQuestion(question, kin)
    }
}

