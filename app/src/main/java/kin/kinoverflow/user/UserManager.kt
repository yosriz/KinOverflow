package kin.kinoverflow.user

import android.content.Context
import io.reactivex.Single
import kin.kinoverflow.model.User
import kin.kinoverflow.network.StackOverflowApi


class UserManager(private val context: Context) {

    private var userId: Int
    private val api: StackOverflowApi = StackOverflowApi()
    private var cachedUser: Single<User>

    init {
        val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        userId = sharedPref.getInt("user_id", 3903847) //default is yosriz profile

        cachedUser = api.getUserDetails(userId)
                .cache()

        cachedUser.subscribe()
    }

    fun getUser(): Single<User> {
        return cachedUser
    }
}