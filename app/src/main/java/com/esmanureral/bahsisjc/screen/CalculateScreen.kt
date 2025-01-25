package com.esmanureral.bahsisjc.screen

//HESAPLAMA İŞLEMLERİ

object CalculateScreen {

    fun calculateTotalTip(
        totalBill:Double,
        tipPercentage:Int //tip oranı
    ):Double{
        return if(totalBill>1 && totalBill.toString().isNotEmpty())
        {
            (totalBill*tipPercentage)/100
        }
        else
            0.0
    }
    fun calculateTotalPerson(
        totalBill:Double,
        splitBy:Int,//kaç kişiye bölünecek
        tipPercentage:Int
    ):Double{
        val bill= calculateTotalTip(
            totalBill=totalBill,
            tipPercentage=tipPercentage

        )+totalBill
        return (bill/splitBy)
    }
}