package com.mobile.ewallet.feature.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivitySplashscreenBinding
import timber.log.Timber

class SplashcreenActivity: BaseActivity<AuthViewModel>() {

    override val viewModelClass: Class<AuthViewModel> get() = AuthViewModel::class.java
    private lateinit var binding: ActivitySplashscreenBinding

    private lateinit var fragmentAdapter: ViewPagerFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.btnLogin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
        }

        binding.actionRegister.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadSplashScreen()
    }

    private fun observeViewModel(){
        viewModel.onSplashscreenLoaded.observe(this){
            fragmentAdapter = ViewPagerFragmentAdapter(this@SplashcreenActivity)
            it.forEach { data ->
                fragmentAdapter.addFragment(SplashFragment.newInstance(
                    title = data.title!!,
                    subtitle = data.message!!,
                    imageUrl = data.image!!
                ))
            }
            binding.viewpager.adapter = fragmentAdapter
            Timber.i("adapter size: ${fragmentAdapter.itemCount}")
        }

        viewModel.isLoading.observe(this){

        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private inner class ViewPagerFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val fragments = mutableListOf<Fragment>()
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]

        fun addFragment(f: Fragment){
            fragments.add(f)
        }

        /*@NonNull
        fun getItem(position: Int): Fragment {
            return arrayList[position]
        }

        fun addFragment(fragment: Fragment) {
            arrayList.add(fragment)
        }

        override fun getItemCount(): Int {
            return arrayList.size()
        }*/
    }

}