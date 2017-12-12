package kin.kinoverflow.network

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kin.kinoverflow.model.Answer
import kin.kinoverflow.model.Question
import kin.kinoverflow.model.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor


private const val STACKOVERFLOW_API = "https://api.stackexchange.com/2.2/"

class StackOverflowApi {
    private val stackOverflowService: StackOverflowApiService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(STACKOVERFLOW_API)
                //.client(client)
                .build()
        stackOverflowService = retrofit.create(StackOverflowApiService::class.java)
    }


    fun getLastQuestions(): Single<List<Question>> {
        return stackOverflowService.getQuestions(tags = "android")
                .map { it.items }
                .subscribeOn(Schedulers.io())

    }

    fun getAnswers(questionId: Int): Single<List<Answer>> {
        return stackOverflowService.getAnswers(questionId)
                .map { it.items }
                .subscribeOn(Schedulers.io())
    }

    fun getUserDetails(userId: Int): Single<User> {
        return stackOverflowService.getUser(userId)
                .map {
                    if (it.items.isNotEmpty()) it.items[0] else User()
                }
                .subscribeOn(Schedulers.io())
    }

}