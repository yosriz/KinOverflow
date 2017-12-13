package kin.kinoverflow.transaction

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.Single
import io.reactivex.SingleEmitter
import kin.kinoverflow.PASSPHRASE
import kin.kinoverflow.R
import kin.sdk.core.KinClient
import kin.sdk.core.ResultCallback
import kin.sdk.core.TransactionId
import java.lang.Exception
import java.math.BigDecimal


class TransactionDialog(private val context: Context, private val kinClient: KinClient) {

    @BindView(R.id.number_picker) lateinit var numberPicker: NumberPicker
    @BindView(R.id.progress_bar) lateinit var progressBar: ProgressBar

    interface TransactionResult
    class TransactionSucceed(val kin: Long) : TransactionResult
    class TransactionFailed(val ex: Exception) : TransactionResult
    class TransactionCancelled : TransactionResult

    fun showTransactionDialog(address: String): Single<TransactionResult> {
        return Single.create { emitter: SingleEmitter<TransactionResult> ->

            val view = LayoutInflater.from(context)
                    .inflate(R.layout.transaction_dialog, null, false)
            ButterKnife.bind(this, view)
            numberPicker.maxValue = 100
            numberPicker.minValue = 1

            val dialog = AlertDialog.Builder(context)
                    .setView(view)
                    .setPositiveButton("Pay", { dialogInterface, id ->
                        val kin = numberPicker.value
                        progressBar.visibility = View.VISIBLE
                        numberPicker.visibility = View.GONE
                        kinClient.account.sendTransaction(address, PASSPHRASE, BigDecimal.valueOf(kin.toLong()))
                                .run(object : ResultCallback<TransactionId> {
                                    override fun onResult(txId: TransactionId) {
                                        emitter.onSuccess(TransactionSucceed(kin.toLong()))
                                        dialogInterface.dismiss()
                                    }

                                    override fun onError(ex: Exception) {
                                        ex.printStackTrace()
                                        emitter.onSuccess(TransactionFailed(ex))
                                        dialogInterface.dismiss()
                                    }
                                })
                    })
                    .setNegativeButton("Cancel", { dialogInterface, id ->
                        emitter.onSuccess(TransactionCancelled())
                        dialogInterface.dismiss()
                    })
                    .create()
            dialog.show()
        }
    }

    fun useExample() {
        kinClient?.let { it1 ->
            val dialog = TransactionDialog(context, it1)
            kinClient?.account?.let {
                dialog.showTransactionDialog(it.publicAddress)
                        .subscribe { status ->
                            when (status) {
                                is TransactionDialog.TransactionCancelled ->
                                    Toast.makeText(context, "Transaction Cancelled", Toast.LENGTH_SHORT).show()
                                is TransactionDialog.TransactionFailed ->
                                    Toast.makeText(context, "Transaction FAILED", Toast.LENGTH_SHORT).show()
                                is TransactionDialog.TransactionSucceed ->
                                    Toast.makeText(context, "Transaction SUCCEED, with ${status.kin} Kin", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }

    }

}