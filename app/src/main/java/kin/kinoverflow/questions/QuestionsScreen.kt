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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kin.kinoverflow.R
import kin.kinoverflow.network.StackOverflowApi
import kin.kinoverflow.utils.plusAssign
import java.util.*

class QuestionsScreen @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    @BindView(R.id.recycler) lateinit var recycler: RecyclerView
    @BindView(R.id.pull_to_refresh) lateinit var pullToRefresh: SwipeRefreshLayout

    private val stackOverflowApi: StackOverflowApi = StackOverflowApi()
    private val questionsAdapter: QuestionsAdapter = QuestionsAdapter()
    private val disposables: CompositeDisposable = CompositeDisposable()


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val view = inflate(context, R.layout.fragment_questions, this)
        view?.let { ButterKnife.bind(this, it) }
        recycler.adapter = questionsAdapter
        recycler.layoutManager = LinearLayoutManager(context)

        disposables += RxSwipeRefreshLayout.refreshes(pullToRefresh)
                .startWith(Any())
                .switchMapSingle { stackOverflowApi.getLastQuestions() }
                .doOnNext { Log.d("yossi", "got questions = $it.size()") }
                .doOnError { it.printStackTrace() }
                .onErrorReturn { Collections.emptyList() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { pullToRefresh.isRefreshing = true }
                .subscribe { questions ->
                    pullToRefresh.isRefreshing = false
                    if (questions.isEmpty()) {
                        Toast.makeText(context, "Cannot load questions!", Toast.LENGTH_SHORT).show()
                    }
                    questionsAdapter.updateQuestions(questions)
                }

    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.clear()
    }

}
