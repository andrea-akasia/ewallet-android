package com.mobile.ewallet.feature.moneysend

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.mobile.ewallet.R
import com.mobile.ewallet.base.BaseActivity
import com.mobile.ewallet.databinding.ActivitySendTypeBinding
import com.mobile.ewallet.feature.scantosendmoney.ScannerActivity
import com.mobile.ewallet.model.contact.Contact
import com.mobile.ewallet.util.Constant
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class MoneySendTypeActivity: BaseActivity<SendMoneyViewModel>() {

    override val viewModelClass: Class<SendMoneyViewModel> get() = SendMoneyViewModel::class.java
    private lateinit var binding: ActivitySendTypeBinding

    private var historyAdapter: HistoryAdapter? = null

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_CONTACTS
    )

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.RC_CONTACT_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Izin membaca kontak harus diberikan untuk dapat melanjutkan!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //read contacts
                fetchContacts()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        binding.topbar.actionBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.topbar.title.text = "Kirim Uang"

        binding.actionFromBank.setOnClickListener {
            startActivity(
                Intent(this@MoneySendTypeActivity, MoneySendBankActivity::class.java)
            )
        }

        binding.actionFromContact.setOnClickListener {
            if (allPermissionsGranted()) {
                //read contact
                fetchContacts()
            } else {
                ActivityCompat.requestPermissions(
                    this@MoneySendTypeActivity, REQUIRED_PERMISSIONS, Constant.RC_CONTACT_PERMISSIONS
                )
            }
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHistoryTransfer()
    }

    private fun observeViewModel(){
        viewModel.onEwalletUserLoaded.observe(this){
            if(it.isNotEmpty()){
                viewModel.localContact.forEach { lc ->
                    lc.numbers.forEach { num ->
                        it.findLast { cu -> cu.phone == num }?.let { found ->
                            viewModel.foundContacts.add(found)
                        }
                    }
                }
                Timber.i("total matching contacts: ${viewModel.foundContacts.size}")

                startActivity(
                    Intent(this@MoneySendTypeActivity, ContactListActivity::class.java)
                        .putExtra("CONTACTS", Gson().toJson(viewModel.foundContacts))
                )
            }else{
                Toast.makeText(this, "No Contact Found Using Ewallet App", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.onHistoryTransactionLoaded.observe(this){
            if(it.isNotEmpty()){
                binding.emptyView.visibility = View.GONE
                binding.rv.visibility = View.VISIBLE
                historyAdapter = HistoryAdapter(it)
                binding.rv.layoutManager = GridLayoutManager(this, 3)
                binding.rv.adapter = historyAdapter
            }else{
                binding.emptyView.visibility = View.VISIBLE
                binding.rv.visibility = View.GONE
            }
        }

        viewModel.warningMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchContacts() {
        lifecycleScope.launch {
            viewModel.localContact.clear()
            val contactsListAsync = async { getPhoneContacts() }
            val contactNumbersAsync = async { getContactNumbers() }

            val contacts = contactsListAsync.await()
            val contactNumbers = contactNumbersAsync.await()

            contacts.forEach {
                contactNumbers[it.id]?.let { numbers ->
                    it.numbers = numbers
                }
                viewModel.localContact.add(it)
            }
            Timber.i("total contact fetched: ${viewModel.localContact.size}")
            //val bw = viewModel.localContact.findLast { it.name == "Beloved Wife" }
            //bw?.let { Timber.i("sample data: ${it.numbers}") }
            if(viewModel.localContact.isNotEmpty()){
                viewModel.loadEwalletUser()
            }else{
                Toast.makeText(this@MoneySendTypeActivity, "0 contacts found", Toast.LENGTH_SHORT)
                    .show()
            }
            //Timber.i("contact matched with ewallet users:")
            //Timber.i("example contact index 1:\n${con}")

            //_contactsLiveData.postValue(contacts)
        }
    }

    private fun getPhoneContacts(): ArrayList<Contact> {
        val contactsList = ArrayList<Contact>()
        val contactsCursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        if (contactsCursor != null && contactsCursor.count > 0) {
            val idIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            while (contactsCursor.moveToNext()) {
                val id = contactsCursor.getString(idIndex)
                val name = contactsCursor.getString(nameIndex)
                if (name != null) {
                    contactsList.add(Contact(id, name))
                }
            }
            contactsCursor.close()
        }
        return contactsList
    }

    private fun getContactNumbers(): HashMap<String, ArrayList<String>> {
        val contactsNumberMap = HashMap<String, ArrayList<String>>()
        val phoneCursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (phoneCursor != null && phoneCursor.count > 0) {
            val contactIdIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (phoneCursor.moveToNext()) {
                val contactId = phoneCursor.getString(contactIdIndex)
                val number: String = phoneCursor.getString(numberIndex)
                //check if the map contains key or not, if not then create a new array list with number
                if (contactsNumberMap.containsKey(contactId)) {
                    contactsNumberMap[contactId]?.add(number)
                } else {
                    contactsNumberMap[contactId] = arrayListOf(number)
                }
            }
            //contact contains all the number of a particular contact
            phoneCursor.close()
        }
        return contactsNumberMap
    }
}