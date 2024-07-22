package com.example.composesample.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.composesample.ui.theme.ComposeSampleTheme

@Composable
fun NetworkErrorView(onClick: () -> Unit) {
    Dialog(onDismissRequest = { onClick() }) {
        Card(
            modifier = Modifier
                .width(350.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(2.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = Color.Red
                )
                Text(
                    text = "Ups, parece que tenemos problemas de conexion.",
                    textAlign = TextAlign.Center
                )
                Button(onClick = { onClick() }) {
                    Text(text = "Intentar de nuevo")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    ComposeSampleTheme {
        NetworkErrorView {}
    }
}