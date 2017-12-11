package kin.kinoverflow.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface StackOverflowApiService {

    @GET("search")
    fun getQuestions(@Query("order") order: String = "desc", @Query("pagesize") pagesize: Int = 20,
                     @Query("sort") sort: String = "activity", @Query("tagged") tags: String,
                     @Query("site") site: String = "stackoverflow", @Query("filter") filter:
                     String = "withbody")

    @GET("questions/{id}/answers")
    fun getAnswers(@Path("id") questionId: String, @Query("order") order: String = "desc",
                   @Query("pagesize") pagesize: Int = 20, @Query("sort") sort: String = "activity",
                   @Query("tagged") tags: String, @Query("site") site: String = "stackoverflow",
                   @Query("filter") filter: String = "withbody")

    @GET("users/{id}/answers")
    fun getUser(@Path("id") id: String, @Query("site") site: String = "stackoverflow")
}