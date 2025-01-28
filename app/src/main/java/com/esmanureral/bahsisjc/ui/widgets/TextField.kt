package com.esmanureral.bahsisjc.ui.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//içine yazı yazdıgımız component.


//faturagiriş
@Composable
fun BillInputTextFiels(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,//Giriş alanının tek satırlık mı yoksa çok satırlık mı olduğunu belirler.
    keyboardType: KeyboardType = KeyboardType.Number,//sadece sayı girdisi
    imeAction: ImeAction = ImeAction.Done,//Klavyedeki eylem düğmesinin ne yapacağını belirtir.
    onAction: KeyboardActions = KeyboardActions.Default//Klavyede bir eylem gerçekleştiğinde çağrılacak işlevleri belirler.
) {
    OutlinedTextField(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,//textfield a tıklandığındaki  arka plan rengi
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,//alt çizginin rengi.
            focusedLeadingIconColor = Color.Black,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = Color.Black
        ),
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = {
            Text(text = labelId)
        },
        leadingIcon = {//Giriş alanının sol tarafında bir simge (AttachMoney) gösterir.
            Icon(imageVector = Icons.Rounded.AttachMoney, contentDescription = "money ıcon")
        },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction

    )
}