package kin.kinoverflow.network

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kin.kinoverflow.model.Answer
import kin.kinoverflow.model.Question
import kin.kinoverflow.model.User
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val STACKOVERFLOW_API = "https://api.stackexchange.com/2.2/"

class StackOveflowApi {
    private val stackOverflowService: StackOverflowApiService

    init {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(STACKOVERFLOW_API)
                .build()
        stackOverflowService = retrofit.create(StackOverflowApiService::class.java)
    }


    fun getLastQuestions(): Single<List<Question>> {
        return stackOverflowService.getQuestions(tags = "android")
                .subscribeOn(Schedulers.io())
    }

    fun getAnswers(questionId: Int): Single<List<Answer>> {
        return stackOverflowService.getAnswers(questionId)
                .subscribeOn(Schedulers.io())
    }

    fun getUserDetails(userId: Int): Single<User> {
        return stackOverflowService.getUser(userId)
                .subscribeOn(Schedulers.io())
    }

}