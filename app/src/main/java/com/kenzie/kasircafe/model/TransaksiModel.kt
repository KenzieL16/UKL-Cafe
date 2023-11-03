package com.kenzie.kasircafe.model

data class TransaksiModel(
    val idTransaksi: String = "",
    val menu: String = "",
    val tanggal: String = "",
    val selectedPayment: String = "",
    val selectedMeja: String ="",
    val harga: String = "",
    var isSelesai: Boolean = false,
    var isSelesaiButtonVisible: Boolean = true,
    var isChecked: Boolean = false
)