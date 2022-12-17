package com.mobile.ewallet.feature.credit.detail.kur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.mobile.ewallet.base.BaseFragment
import com.mobile.ewallet.databinding.FragmentDetailKurFulfillmentBinding

class DetailKURFulfillmentFragment: BaseFragment<DetailKURViewModel>() {
    override val viewModelClass: Class<DetailKURViewModel> = DetailKURViewModel::class.java
    private var _binding: FragmentDetailKurFulfillmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(id: String, isKawin: Boolean = false) = DetailKURFulfillmentFragment().apply {
            arguments = bundleOf(
                "ID" to id,
                "IS_KAWIN" to isKawin
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailKurFulfillmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getBoolean("IS_KAWIN", false)?.let { viewModel.isKawin = it }
        arguments?.getString("ID")?.let { idPendanaan ->
            _binding?.let { _ ->
                observeViewModel()

                viewModel.loadFulfillment(idPendanaan)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.onFulfillmentLoaded.observe(viewLifecycleOwner) {
            _binding!!.etKewarganegaraan.setText(it.kewarganegaraanTEXT)
            _binding!!.etPhone.setText(it.telpHP)
            _binding!!.etFaxArea.setText(it.faxArea)
            _binding!!.etFax.setText(it.fax)

            _binding!!.etProfesi.setText(it.profesiTEXT)
            _binding!!.etJabatan.setText(it.jabatanTEXT)
            _binding!!.etBidangUsaha.setText(it.bidangUsahaTEXT)
            _binding!!.etBerdiriSejak.setText(it.berdiriSejak)
            _binding!!.etBekerjaSejak.setText(it.bekerjausahasejak)
            _binding!!.etTempatBekerja.setText(it.tempatBekerjaTEXT)

            _binding!!.etNamaPerusahaan.setText(it.namaPerusahaan)
            _binding!!.etAlamatKantor1.setText(it.alamatKantorLine1)
            _binding!!.etAlamatKantor2.setText(it.alamatKantorLine2)
            _binding!!.etAlamatKantor3.setText(it.alamatKantorLine3)
            _binding!!.etKecamatanKantor.setText(it.kecamatanKantor)
            _binding!!.etKelurahanKantor.setText(it.kelurahanKantor)
            _binding!!.etKodeposKantor.setText(it.kodePosKantorTEXT)
            _binding!!.etFaxAreaKantor.setText(it.noFaxArea)
            _binding!!.etFaxKantor.setText(it.noFax)
            _binding!!.etPhoneAreaKantor.setText(it.noTelpArea)
            _binding!!.etPhoneKantor.setText(it.noTelp)

            _binding!!.etEmergencyName.setText(it.namaEmergencyContact)
            _binding!!.etHubungan.setText(it.hubungan)

            if(viewModel.isKawin){
                _binding!!.viewPernikahanOptional.visibility = View.VISIBLE
                _binding!!.etProfesiPasangan.setText(it.profesiPasanganTEXT)
                _binding!!.etBidangUsahaPasangan.setText(it.bidangUsahaPasanganTEXT)
                _binding!!.etTempatBekerjaPasangan.setText(it.tempatBekerjaPasanganTEXT)
            }else{
                _binding!!.viewPernikahanOptional.visibility = View.GONE
            }

            _binding!!.etBekerjaTanggalMenikah.setText(it.mulaiBekerjaTanggalMenikah)
            _binding!!.etDatill.setText(it.lokasiDatiIIUsahaTEXT)

            _binding!!.etSumberDana.setText(it.sumberDanaTEXT)
            _binding!!.etKomoditas.setText(it.komoditasTEXT)
            _binding!!.etLuasLahan.setText(it.luasLahan)
            _binding!!.etJenisDebitur.setText(it.jenisDebiturTEXT)
            _binding!!.etNoSuratPermohonan.setText(it.noSurat)
            _binding!!.etNoIjinUsaha.setText(it.noIjinUsaha)
        }

        viewModel.warningMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }
}