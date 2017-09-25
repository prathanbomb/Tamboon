package com.prathanbomb.tamboon.view.ui.payment

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import co.omise.android.models.Token
import co.omise.android.ui.CreditCardActivity
import com.prathanbomb.tamboon.R
import com.prathanbomb.tamboon.databinding.ActivityPaymentBinding
import com.prathanbomb.tamboon.service.model.Charity
import com.prathanbomb.tamboon.service.model.Receipt
import com.prathanbomb.tamboon.service.model.Request
import com.prathanbomb.tamboon.service.model.Result
import com.prathanbomb.tamboon.view.ui.receipt.ReceiptActivity
import com.prathanbomb.tamboon.viewmodel.DonationViewModel
import java.lang.StringBuilder


class PaymentActivity : AppCompatActivity(), LifecycleRegistryOwner, View.OnClickListener {

    private val mRegistry = LifecycleRegistry(this)
    private lateinit var token: Token
    lateinit var charity: Charity

    override fun getLifecycle(): LifecycleRegistry {
        return mRegistry
    }

    private val OMISE_PKEY = "pkey_test_59aesrkny78osxkdgbz"
    private val REQUEST_CC = 100
    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        charity = intent.getParcelableExtra("data")
        binding.charity = charity
        binding.buttonDonate.setOnClickListener(this)
        binding.editTextAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().matches("^(\\d*\\.\\d{2})?\$".toRegex())) {
                    val input = "" + s.toString().replace("[^\\d]".toRegex(), "")
                    val amountBuilder = StringBuilder(input)
                    while (amountBuilder.length > 3 && amountBuilder[0] == '0') {
                        amountBuilder.deleteCharAt(0)
                    }
                    while (amountBuilder.length < 3) {
                        amountBuilder.insert(0, '0')
                    }
                    amountBuilder.insert(amountBuilder.length - 2, '.')
                    binding.editTextAmount.setText(amountBuilder.toString())
                    Selection.setSelection(binding.editTextAmount.text, amountBuilder.toString().length)
                }
            }
        })
    }

    override fun onClick(view: View) {
        when (view) {
            binding.buttonDonate -> {
                if (validateNameAndAmount()) {
                    val intent = Intent(this, CreditCardActivity::class.java)
                    intent.putExtra(CreditCardActivity.EXTRA_PKEY, OMISE_PKEY)
                    startActivityForResult(intent, REQUEST_CC)
                } else {
                    Toast.makeText(this, "Please ﬁll out name and amount", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateNameAndAmount(): Boolean {
        return !(binding.editTextName.text.toString().isEmpty() or binding.editTextAmount.text.toString().replace("([^\\d]|[0])".toRegex(), "").isEmpty())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CC -> {
                if (resultCode == CreditCardActivity.RESULT_CANCEL) {
                    Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show()
                    return
                }
                token = data!!.getParcelableExtra(CreditCardActivity.EXTRA_TOKEN_OBJECT)
                val viewModel = ViewModelProviders.of(this).get(DonationViewModel::class.java)
                observeViewModel(viewModel)
                binding.isLoading = true
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun observeViewModel(viewModel: DonationViewModel) {
        viewModel.makeDonationObservable(
                Request(binding.editTextName.text.toString(),
                        token.id,
                        binding.editTextAmount.text.toString().replace("[^\\d]".toRegex(), "").toInt()
                )
        ).observe(this, Observer<Result> { result ->
            binding.isLoading = false
            if (result != null) {
                if (result.success!!) {
                    val intent = Intent(this, ReceiptActivity::class.java)
                    intent.putExtra("data", Receipt(charity, binding.editTextName.text.toString(), binding.editTextAmount.text.toString()))
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
