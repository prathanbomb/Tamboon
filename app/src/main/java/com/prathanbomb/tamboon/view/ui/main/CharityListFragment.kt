package com.prathanbomb.tamboon.view.ui.main


import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prathanbomb.tamboon.R
import com.prathanbomb.tamboon.databinding.FragmentCharityListBinding
import com.prathanbomb.tamboon.service.model.Charity
import com.prathanbomb.tamboon.view.adapter.CharityAdapter
import com.prathanbomb.tamboon.view.callback.CharityClickCallback
import com.prathanbomb.tamboon.view.ui.payment.PaymentActivity
import com.prathanbomb.tamboon.viewmodel.CharityListViewModel

/**
 * A simple [Fragment] subclass.
 */
class CharityListFragment : LifecycleFragment() {

    private var charityAdapter: CharityAdapter? = null
    private var charities: List<Charity> = ArrayList()
    private var binding: FragmentCharityListBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charity_list, container, false)
        charityAdapter = CharityAdapter(charityClickCallback)
        binding!!.recycleview.adapter = charityAdapter
        binding!!.isLoading = true
        return binding!!.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(CharityListViewModel::class.java)
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: CharityListViewModel) {
        viewModel.charityListObservable().observe(this, Observer<List<Charity>> { charities ->
            if (charities != null) {
                binding!!.isLoading = false
                this.charities = charities
                charityAdapter!!.setCharityList(charities)
            }
        })
    }

    private val charityClickCallback = object : CharityClickCallback {
        override fun onClick(charity: Charity) {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra("data", charity)
            startActivity(intent)
        }
    }

}
