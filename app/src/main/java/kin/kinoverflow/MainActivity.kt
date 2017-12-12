package kin.kinoverflow

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import kin.kinoverflow.network.AwardServiceMock
import kin.kinoverflow.profile.ProfileScreen
import kin.kinoverflow.questions.QuestionsScreen
import kin.sdk.core.KinClient
import kin.sdk.core.ServiceProvider
import kin.sdk.core.exception.EthereumClientException

private const val PASSPHRASE = "1234"
private const val ROPSTEN_TEST_NET_URL = "http://parity.rounds.video:8545"

class MainActivity : AppCompatActivity() {


    @BindView(R.id.bottom_navigation) lateinit var bottomNavigation: BottomNavigationView
    @BindView(R.id.screen_holder) lateinit var screenHolder: ViewGroup
    private lateinit var questionsScreen: QuestionsScreen
    private lateinit var profileScreen: ProfileScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var kinClient: KinClient? = initKinClient()

        questionsScreen = QuestionsScreen(this)
        profileScreen = ProfileScreen(this)
        profileScreen.kinClient = kinClient

        ButterKnife.bind(this)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    screenHolder.removeAllViews()
                    screenHolder.addView(profileScreen)
                }
                R.id.navigation_questions -> {
                    screenHolder.removeAllViews()
                    screenHolder.addView(questionsScreen)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initKinClient(): KinClient? {
        try {
            var kinClient = KinClient(this,
                    ServiceProvider(ROPSTEN_TEST_NET_URL, ServiceProvider.NETWORK_ID_ROPSTEN))

            if (!kinClient.hasAccount()) {
                var account = kinClient.createAccount(PASSPHRASE)
                AwardServiceMock().awardKin(account.publicAddress)
            }

            return kinClient

        } catch (e: EthereumClientException) {
            e.printStackTrace()
        }

        return null
    }
}
