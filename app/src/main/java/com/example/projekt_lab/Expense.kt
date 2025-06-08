package com.example.projekt_lab

import android.os.Parcel
import android.os.Parcelable

data class Expense(
    val category: String,
    val amount: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeDouble(amount)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense = Expense(parcel)
        override fun newArray(size: Int): Array<Expense?> = arrayOfNulls(size)
    }
}