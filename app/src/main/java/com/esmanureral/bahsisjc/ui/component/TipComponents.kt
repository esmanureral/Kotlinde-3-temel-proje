package com.esmanureral.bahsisjc.ui.component

import android.health.connect.datatypes.units.Percentage
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.Person
import com.esmanureral.bahsisjc.screen.CalculateScreen
import com.esmanureral.bahsisjc.ui.widgets.BillInputTextFiels
import com.esmanureral.bahsisjc.ui.widgets.RoundIconButton

//uygulama ekranı,kişi başına toplam gelir

@Preview
@Composable

fun MainPage(
    modifier: Modifier = Modifier
) {
    val totalPerPersonState = remember { mutableDoubleStateOf(0.0) } // Kişi başına toplam tutarı saklamak için bir durum.
    TotalPerPersonCard(totalPerPersonState = totalPerPersonState.value) // Kart bileşenini çağırır ve tutarı gösterir.
    BillTipCard(totalPerPersonState = totalPerPersonState) // Fatura ve bahşiş hesaplama kartını çağırır.
}


// Bu fonksiyon, kişi başına toplam tutarı bir kart içinde gösterir.
@Composable
private fun TotalPerPersonCard(
    totalPerPersonState: Double = 0.0
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(CornerSize(20.dp))),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val formatTotalPerPerson = "%.2f".format(totalPerPersonState) // Tutar formatlanır.
            Text(
                text = "total per person",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$$formatTotalPerPerson", // Hesaplanan tutar dolar olarak gösterilir.
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun BillTipCard(
    modifier: Modifier = Modifier,
    totalPerPersonState: MutableDoubleState
) {
    val keyboardController = LocalSoftwareKeyboardController.current // Klavye kontrolü.
    val totalBillState = remember { mutableStateOf("") } // Toplam fatura tutarı için durum.
    val validState = remember(totalBillState.value) { totalBillState.value.trim().isNotEmpty() } // Geçerlilik kontrolü.
    val splitByState = remember { mutableIntStateOf(1) } // Kişi sayısı.
    val sliderPositionState = remember { mutableFloatStateOf(0f) } // Bahşiş yüzdesi.
    val tipPercentage = (sliderPositionState.floatValue * 100).toInt() // Bahşiş yüzdesini hesaplar.
    val tipAmountState = remember { mutableDoubleStateOf(0.0) } // Bahşiş miktarı.

    Surface(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = modifier
                .padding(6.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Fatura giriş alanı.
            BillInputTextFiels(
                valueState = totalBillState,
                labelId = "enter bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    totalPerPersonState.doubleValue = CalculateScreen.calculateTotalPerson(
                        totalBillState.value.toDouble(),
                        splitByState.intValue,
                        tipPercentage
                    ) // Kişi başına toplam hesaplanır.
                    keyboardController?.hide()
                }
            )
            BillSplitRow(
                validState = validState,
                splitByState = splitByState,
                totalPerPersonState = totalPerPersonState,
                totalBillState = totalBillState,
                tipPercentage = tipPercentage
            )
            TipContent(
                sliderPositionState = sliderPositionState,
                validState = validState,
                totalBillState = totalBillState,
                tipPercentage = tipPercentage,
                tiAmountState = tipAmountState,
                totalPerPersonState = totalPerPersonState,
                splitByState = splitByState

            )
        }
    }
}
@Composable
fun BillSplitRow(
    modifier: Modifier = Modifier,
    validState: Boolean,
    splitByState: MutableIntState,
    totalPerPersonState: MutableDoubleState,
    totalBillState: MutableState<String>,
    tipPercentage: Int
) {
    val range = IntRange(start = 1, endInclusive = 100) // Geçerli kişi sayısı aralığı.

    if (true) {
        Row(
            modifier = modifier.padding(3.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "split",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(120.dp))
            Row(
                modifier = modifier.padding(horizontal = 3.dp),
                horizontalArrangement = Arrangement.End
            ) {
                RoundIconButton(
                    imageVector = Icons.Default.Remove,
                    onClick = {
                        splitByState.intValue = if (splitByState.intValue > 1) splitByState.intValue - 1 else 1
                        totalPerPersonState.doubleValue = CalculateScreen.calculateTotalPerson(
                            totalBillState.value.toDouble(),
                            splitByState.intValue,
                            tipPercentage
                        ) // Kişi başına düşen toplam yeniden hesaplanır.
                    }
                )
                Text(text="${splitByState.intValue}",
                    style = TextStyle(
                        color=Color.Black,
                        fontSize = 18.sp
                    ),
                    modifier = modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 20.dp, end = 20.dp)
                )
                RoundIconButton(imageVector = Icons.Default.Add,
                    onClick = {
                        if(splitByState.intValue<range.last){
                            splitByState.intValue+=1
                            totalPerPersonState.doubleValue = CalculateScreen.calculateTotalPerson(
                                totalBillState.value.toDouble(),
                                splitByState.intValue,
                                tipPercentage
                            ) // Kişi başına düşen toplam yeniden hesaplanır.

                        }

                })
            }
        }
    }
}

@Composable
fun TipContent(
    sliderPositionState:MutableFloatState,
    validState: Boolean,
    totalBillState: MutableState<String>,
    tipPercentage: Int,
    tiAmountState: MutableDoubleState,
    totalPerPersonState: MutableDoubleState,
    splitByState: MutableIntState

    ) {
    if(validState){
        TipRow(tipAmountState = tiAmountState)
        TipBar(
            totalBillState = totalBillState,
            sliderPositionState = sliderPositionState,
            tipPercentage = tipPercentage,
            tipAmountState = tiAmountState,
            totalPerPersonState = totalPerPersonState,
            splitByState = splitByState
        )


    }
    
}

@Composable
fun TipRow(modifier: Modifier = Modifier,
           tipAmountState: MutableDoubleState)
{
    Row(
        modifier=modifier.padding(horizontal = 3.dp, vertical = 12.dp)
    )
    {
Text(text="tip",
    style = TextStyle(
        color=Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    modifier = modifier.align(Alignment.CenterVertically)
)
        Spacer(modifier=Modifier.width(180.dp))
       Text(text="$${tipAmountState.doubleValue}",
           style = TextStyle(
               color = Color.Black,
               fontSize = 18.sp
           ),
           modifier = modifier.align(Alignment.CenterVertically)
       )
    }
}

@Composable
fun TipBar(modifier: Modifier = Modifier,
           totalBillState: MutableState<String>,
           sliderPositionState: MutableFloatState,
           tipPercentage: Int,
           tipAmountState: MutableDoubleState,
           totalPerPersonState: MutableDoubleState,
           splitByState: MutableIntState
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text="$tipPercentage%")
        Slider(
            modifier = modifier.padding(horizontal = 15.dp),
            value=sliderPositionState.floatValue, onValueChange = {
            sliderPositionState.floatValue=it
        },
            onValueChangeFinished ={
                tipAmountState.doubleValue=CalculateScreen.calculateTotalTip(totalBillState.value.toDouble(),tipPercentage)
                    totalPerPersonState.doubleValue=CalculateScreen.calculateTotalPerson(
                        totalBillState.value.toDouble(),
                        splitByState.intValue,
                        tipPercentage


                    )

            } )
    }
}