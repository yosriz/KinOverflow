package kin.kinoverflow.post

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kin.kinoverflow.R
import kin.kinoverflow.model.Answer
import kin.kinoverflow.questions.ViewHolderClickListener


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

    private val subject = PublishSubject.create<Answer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = AnswerViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.answer_view_holder, parent, false)
        )
        view.listener = object : ViewHolderClickListener {
            override fun onClick(adapterPosition: Int) {
                subject.onNext(answers[adapterPosition])
            }
        }
        return view
    }

    override fun onBindViewHolder(vh: AnswerViewHolder, position: Int) {
        val question = answers[position]
        Log.d("yossi", "index = $position answerId = ${question.answerId}")
        val kin = kinMap[question.answerId.toString()]

        vh.setAnswer(question, kin)
    }

    fun answerClickEvents(): Observable<Answer> {
        return subject.hide()
    }
}