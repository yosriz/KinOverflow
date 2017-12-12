package kin.kinoverflow

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import kin.kinoverflow.questions.QuestionsScreen

class MainActivity : AppCompatActivity() {

    @BindView(R.id.bottom_navigation) lateinit var bottomNavigation: BottomNavigationView
    @BindView(R.id.screen_holder) lateinit var screenHolder: ViewGroup
    private lateinit var questionsScreen: QuestionsScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionsScreen = QuestionsScreen(this)
        ButterKnife.bind(this)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    screenHolder.removeAllViews()
                    //screenHolder.addView(questionsScreen)
                }
                R.id.navigation_questions -> {
                    screenHolder.removeAllViews()
                    screenHolder.addView(questionsScreen)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
