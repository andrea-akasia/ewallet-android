package com.mobile.ewallet.feature.moneysend

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivityListContactBinding
import com.mobile.ewallet.feature.pay.PayInputActivity
import com.mobile.ewallet.model.api.sendmoney.bycontact.ContactUser
import timber.log.Timber

class ContactListActivity: BaseActivity<SendMoneyViewModel>(), ContactAdapter.ContactListener {

    override val viewModelClass: Class<SendMoneyViewModel> get() = SendMoneyViewModel::class.java
    private lateinit var binding: ActivityListContactBinding

    private var contactAdapter: ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Pilih Kontak"

        intent.getStringExtra("CONTACTS")?.let {
            val contacts = Gson().fromJson(it, Array<ContactUser>::class.java).toMutableList()
            contactAdapter = ContactAdapter(contacts)
            contactAdapter?.listener = this@ContactListActivity
            binding.rv.layoutManager = LinearLayoutManager(this@ContactListActivity)
            binding.rv.adapter = contactAdapter
        }
    }

    override fun onContactSelected(data: ContactUser) {
        startActivity(
            Intent(this, PayInputActivity::class.java)
                .putExtra("DATA", Gson().toJson(data))
                .putExtra("ACTION", "CONTACT")
        )
    }
}