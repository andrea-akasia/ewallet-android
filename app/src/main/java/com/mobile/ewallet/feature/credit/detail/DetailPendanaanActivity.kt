package com.mobile.ewallet.feature.credit.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityDetailPendanaanReqBinding
import com.mobile.ewallet.feature.credit.CreditViewModel
import com.mobile.ewallet.feature.credit.detail.kum.DetailKUMDocumentsFragment
import com.mobile.ewallet.feature.credit.detail.kum.DetailKUMFulfillmentFragment
import com.mobile.ewallet.feature.credit.detail.kum.DetailKUMPrescreeningFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DetailPendanaanActivity: BaseActivity<CreditViewModel>(), HasAndroidInjector {
    override val viewModelClass: Class<CreditViewModel> = CreditViewModel::class.java
    private lateinit var binding: ActivityDetailPendanaanReqBinding

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPendanaanReqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Detail Pendanaan"

        val type = intent.getStringExtra("TYPE")
        val id = intent.getStringExtra("ID")
        if(type == "KUM"){
            val fragmentAdapter = PendanaanFragmentAdapter(this@DetailPendanaanActivity)
            fragmentAdapter.addFragment(DetailKUMPrescreeningFragment())
            fragmentAdapter.addFragment(DetailKUMFulfillmentFragment.newInstance(id!!))
            fragmentAdapter.addFragment(DetailKUMDocumentsFragment.newInstance(id))
            binding.viewpager.adapter = fragmentAdapter

            TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, pos ->
                when (pos) {
                    0 -> { tab.text = "Prescreening" }
                    1 -> { tab.text = "Fulfillment" }
                    else -> { tab.text = "Documents" }
                }
            }.attach()
        }

    }

    private inner class PendanaanFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val fragments = mutableListOf<Fragment>()
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]

        fun addFragment(f: Fragment){
            fragments.add(f)
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}