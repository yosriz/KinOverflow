package kin.kinoverflow.questions

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kin.kinoverflow.R
import kin.kinoverflow.model.Owner
import kin.kinoverflow.model.Question
import kin.kinoverflow.model.User
import kin.kinoverflow.network.KinOverflowDb
import kin.kinoverflow.network.StackOverflowApi
import kin.kinoverflow.user.UserManager
import kin.kinoverflow.utils.plusAssign
import java.util.*
import kotlin.collections.ArrayList

class QuestionsScreen @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, private val userManager: UserManager
) : FrameLayout(context, attrs, defStyleAttr) {

    @BindView(R.id.recycler) lateinit var recycler: RecyclerView
    @BindView(R.id.pull_to_refresh) lateinit var pullToRefresh: SwipeRefreshLayout

    private val stackOverflowApi: StackOverflowApi = StackOverflowApi()
    private val questionsAdapter: QuestionsAdapter = QuestionsAdapter()
    private var disposables: CompositeDisposable = CompositeDisposable()
    private lateinit var questions: List<Question>
    private lateinit var questionsObservable: Observable<List<Question>>

    init {
        val view = inflate(context, R.layout.fragment_questions, this)
        view?.let { ButterKnife.bind(this, it) }
        recycler.adapter = questionsAdapter
        recycler.layoutManager = LinearLayoutManager(context)
        questionsObservable = stackOverflowApi.getLastQuestions().toObservable()
                .cache()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()


        disposables = CompositeDisposable()
        disposables += RxSwipeRefreshLayout.refreshes(pullToRefresh)
                .startWith(Any())
                .switchMap {
                    Observable.zip(
                            questionsObservable,
                            KinOverflowDb.getKinPerQuestionMap().toObservable(),
                            BiFunction { list: List<Question>, map: Map<String, Long> -> Pair(list, map) }
                    )
                }
                .onErrorReturn { Pair(Collections.emptyList(), Collections.emptyMap()) }
                .doOnNext { Log.d("yossi", "got questions = ${it.first.size}") }
                .doOnError { it.printStackTrace() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { pullToRefresh.isRefreshing = true }
                .subscribe { pair ->
                    pullToRefresh.isRefreshing = false
                    if (pair.first.isEmpty()) {
                        Toast.makeText(context, "Cannot load questions!", Toast.LENGTH_SHORT).show()
                    }
                    questions = pair.first

                    Single.zip(
                            userManager.getUser(),
                            userManager.getOtherUser(),
                            BiFunction { user: User, otherUser: User ->
                                Pair(user, otherUser)
                            })
                            .subscribe { usersPair ->
                                val user = usersPair.first
                                val otherUser = usersPair.second
                                val list = ArrayList(questions)
                                list[1] = questions[1].copy(owner = Owner(reputation = user.reputation, acceptRate = user.acceptRate, displayName = user.displayName,
                                        link = user.link, profileImage = user.profileImage, userId = user.userId, userType = user.userType))
                                list[3] = questions[3].copy(owner = Owner(reputation = otherUser.reputation, acceptRate = otherUser.acceptRate, displayName = otherUser.displayName,
                                        link = otherUser.link, profileImage = otherUser.profileImage, userId = otherUser.userId, userType = otherUser.userType))

                                questionsAdapter.updateQuestions(Pair(list, pair.second))
                            }
                }
    }

    fun questionClickEvents(): Observable<Pair<Question, Boolean>> {
        return questionsAdapter.clickEvents()
                .map { question -> Pair(question, questions[2].questionId == question.questionId) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.clear()
    }

}
