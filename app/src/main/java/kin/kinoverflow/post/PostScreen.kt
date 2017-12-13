package kin.kinoverflow.post

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.squareup.picasso.Picasso
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import kin.kinoverflow.R
import kin.kinoverflow.model.Answer
import kin.kinoverflow.model.Question
import kin.kinoverflow.network.KinOverflowDb
import kin.kinoverflow.network.StackOverflowApi


class PostScreen @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, private val question: Question = Question()
) : FrameLayout(context, attrs, defStyleAttr) {

    @BindView(R.id.recycler_answers) lateinit var recycler: RecyclerView
    @BindView(R.id.tv_question_body) lateinit var questionBody: TextView
    @BindView(R.id.tv_title) lateinit var questiontitle: TextView
    @BindView(R.id.tv_votes) lateinit var questionVotes: TextView
    @BindView(R.id.tv_tags) lateinit var questionTags: TextView
    @BindView(R.id.tv_asked_date) lateinit var questionDate: TextView
    @BindView(R.id.profile_image) lateinit var questionProfileImage: ImageView
    @BindView(R.id.tv_user_name) lateinit var questionProfileName: TextView
    @BindView(R.id.tv_badges_count) lateinit var questionProfileBadgesCount: TextView
    @BindView(R.id.tv_kin) lateinit var questionKin: TextView

    private val stackOverflowApi: StackOverflowApi = StackOverflowApi()
    private val answersAdapter: AnswersAdapter = AnswersAdapter()
    private var disposables: CompositeDisposable = CompositeDisposable()

    init {
        val view = inflate(context, R.layout.post_screen, this)
        view?.let { ButterKnife.bind(this, it) }
        recycler.adapter = answersAdapter
        recycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        disposables = CompositeDisposable()
        Single.zip(
                stackOverflowApi.getAnswers(question.questionId),
                KinOverflowDb.getKinPerQuestionMap().first(HashMap()),
                KinOverflowDb.getKinPerAnswerMap().first(HashMap()),
                Function3 { answers: List<Answer>, questionsMap: Map<String, Long>, answersMap: Map<String, Long> ->
                    Triple(answers, questionsMap, answersMap)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { populateQuestion() }
                .subscribe { triple ->
                    answersAdapter.updateAnswers(triple.first, triple.third)
                    val kin = triple.second[question.questionId.toString()]
                    kin?.let { questionKin.text = it.toString() }
                }
    }

    private fun populateQuestion() {
        questiontitle.text = question.title
        questionBody.text = question.body
        questionVotes.text = (question.upVoteCount - question.downVoteCount).toString()
        questionTags.text = question.tags.reduce(operation = { tags, tag ->
            return@reduce "$tags, $tag"
        })
        questionDate.text = question.creationDate.toString()
        questionProfileName.text = question.owner.displayName
        if (!question.owner.link.isEmpty()) {
            Picasso.with(context)
                    .load(question.owner.profileImage)
                    .into(questionProfileImage)
        }
    }

    @OnClick(R.id.sponsor)
    fun onSponsorClick(){
        //KinOverflowDb.
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.clear()
    }

}