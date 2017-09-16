package com.prathanbomb.tamboon.view.ui.receipt

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.prathanbomb.tamboon.R
import com.prathanbomb.tamboon.databinding.ActivityReceiptBinding
import com.prathanbomb.tamboon.service.model.Receipt

class ReceiptActivity : AppCompatActivity(), LifecycleRegistryOwner, View.OnClickListener {

    private val mRegistry = LifecycleRegistry(this)
    private lateinit var binding: ActivityReceiptBinding
    private lateinit var receipt: Receipt

    override fun getLifecycle(): LifecycleRegistry {
        return mRegistry
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_receipt)
        receipt = intent.getParcelableExtra("data")
        binding.receipt = receipt
        binding.buttonClose.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.buttonClose -> {
                finish()
            }
        }
    }

}
