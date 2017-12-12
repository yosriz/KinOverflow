package kin.kinoverflow.profile

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import kin.kinoverflow.R
import kin.sdk.core.Balance
import kin.sdk.core.KinClient
import kin.sdk.core.Request
import kin.sdk.core.ResultCallback


class ProfileScreen @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @BindView(R.id.kin_balance) lateinit var kinBalance: TextView
    var kinClient: KinClient? = null
    private var request: Request<Balance>? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val view = FrameLayout.inflate(context, R.layout.profile, this)
        view?.let { ButterKnife.bind(this, it) }
        view.setOnClickListener { refreshBalanceText() }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (request!=null) {
            request?.cancel(true)
            request = null
        }
    }

    private fun refreshBalanceText(){
        if (kinClient != null){
            request = kinClient?.account?.balance
            request?.run(object : ResultCallback<Balance> {
                override fun onResult(balance: Balance) {
                    kinBalance.setText(balance.value(0) + " KIN")
                }

                override fun onError(e: Exception) {
                    e.printStackTrace()
                }
            })
        }
    }
}