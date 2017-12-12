package kin.kinoverflow.network

import io.reactivex.Single
import kin.kinoverflow.model.Answer
import kin.kinoverflow.model.Question
import kin.kinoverflow.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface StackOverflowApiService {

    @GET("search")
    fun getQuestions(@Query("order") order: String = "desc", @Query("pagesize") pagesize: Int = 20,
                     @Query("sort") sort: String = "activity", @Query("tagged") tags: String,
                     @Query("site") site: String = "stackoverflow", @Query("filter") filter:
                     String = "withbody"): Single<List<Question>>

    @GET("questions/{id}/answers")
    fun getAnswers(@Path("id") questionId: Int, @Query("order") order: String = "desc",
                   @Query("pagesize") pagesize: Int = 20, @Query("sort") sort: String = "activity",
                   @Query("site") site: String = "stackoverflow",
                   @Query("filter") filter: String = "withbody"): Single<List<Answer>>

    @GET("users/{id}/answers")
    fun getUser(@Path("id") id: Int, @Query("site") site: String = "stackoverflow"): Single<User>
}