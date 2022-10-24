package com.mobile.ewallet.feature.credit.detail.kum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.mobile.ewallet.base.BaseFragment
import com.mobile.ewallet.databinding.FragmentDetailKumPrescreeningBinding

class DetailKUMPrescreeningFragment: BaseFragment<DetailKUMViewModel>() {
    override val viewModelClass: Class<DetailKUMViewModel> = DetailKUMViewModel::class.java
    private var _binding: FragmentDetailKumPrescreeningBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(id: String) = DetailKUMPrescreeningFragment().apply {
            arguments = bundleOf(
                "ID" to id
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailKumPrescreeningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("ID")?.let { idPendanaan ->
            _binding?.let { _ ->
                observeViewModel()

                viewModel.loadPrescreening(idPendanaan)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.onPrescreeningLoaded.observe(viewLifecycleOwner) {
            _binding!!.etPelaporanName.setText(it.namaPelaporan)
            _binding!!.etNomorKk.setText(it.nomorKartuKeluarga)
            _binding!!.etTempatLahir.setText(it.tempatLahir)
            _binding!!.etJenisKelamin.setText(it.jenisKelaminTEXT)
            _binding!!.etTanggalLahir.setText(it.tanggalLahir)
            _binding!!.etPendidikan.setText(it.pendidikanTerakhirTEXT)
            _binding!!.etMotherName.setText(it.namaGadisIbuKandung)
            _binding!!.etPhoneArea.setText(it.telpygdptdihubungiArea)
            _binding!!.etPhone.setText(it.telpygdptdihubungi)

            _binding!!.etNomorKtp.setText(it.nomorKTP)
            _binding!!.etKtpName.setText(it.namaSesuaiKTP)
            _binding!!.etKtpAddress.setText(it.alamat)
            _binding!!.etKtpKota.setText(it.kotaKTP)
            _binding!!.etKtpKecamatan.setText(it.kecamatanKTP)
            _binding!!.etKtpKelurahan.setText(it.kelurahanKTP)
            _binding!!.etKtpKodepos.setText(it.kodePosKTPTEXT)

            _binding!!.etHomeAddress.setText(it.alamatRumah)
            _binding!!.etHomeKota.setText(it.kotaAlamatRumah)
            _binding!!.etHomeKecamatan.setText(it.kecamatanRumah)
            _binding!!.etHomeKelurahan.setText(it.kelurahanRumah)
            _binding!!.etHomeKodepos.setText(it.kodePosRumahTEXT)
            _binding!!.etDatill.setText(it.lokasiDatiIIRumahTEXT)
            _binding!!.etStatusRumah.setText(it.statusRumahTEXT)
            _binding!!.etHomeTanggalMenempati.setText(it.mulaiMenempati)

            _binding!!.etStatusPernikahan.setText(it.statusPernikahanTEXT)
            if(it.statusPernikahan == "1"){ _binding!!.viewPernikahanOptional.visibility = View.VISIBLE }
            _binding!!.etNamaPasangan.setText(it.namaSuamiIstri)
            _binding!!.etTanggalLahirPasangan.setText(it.tanggalLahirPasangan)
            _binding!!.etKtpPasangan.setText(it.nomorKTPPasangan)

            _binding!!.etJenisKredit.setText(it.jenisKreditTEXT)
            _binding!!.etLimit.setText(it.limitAwalYangDiminta)
            _binding!!.etJangkaWaktu.setText("${it.jangkaWaktu} Bulan")
            _binding!!.etNpwp.setText(it.nPWP)
        }

        viewModel.warningMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }

}