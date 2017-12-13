package kin.kinoverflow

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {


    @BindView(R.id.edit_user_id) lateinit var userId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.btn_save)
    fun saveSettings() {
        val sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE)
        sharedPref.edit()
                .putInt("user_id", userId.text.toString().toInt())
                .apply()
    }

}
