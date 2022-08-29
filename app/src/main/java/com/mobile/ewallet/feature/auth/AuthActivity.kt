package com.mobile.ewallet.feature.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mobile.ewallet.R
import com.mobile.ewallet.feature.auth.ui.theme.mainButtonBackground
import com.mobile.ewallet.feature.auth.ui.theme.primaryColor

class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout()
        }
    }

}

@Composable
fun MainLayout() {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = primaryColor)

    Box(
        modifier = Modifier
            .background(color = primaryColor)
            .padding(all = 18.dp)
            .fillMaxSize()
    ){
        
        Image(
            painter = painterResource(id = R.drawable.auth_image),
            contentScale = ContentScale.Fit,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(300.dp)
        )
        
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentHeight()
        ) {

            //text
            Text(
                text = "#SimpanUang Kamu Disini",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Mudah menyimpan uang hanya mulai dari",
                color = Color.White,
                fontSize = 14.sp
            )
            Row {
                Text(
                    text = "Rp10.000",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = " saja, yuk cobain langsung",
                    color = Color.White,
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            //button login
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = mainButtonBackground),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = "Masuk akun",
                    color = primaryColor
                )
            }

            //action register
            Text(
                text = "Mendaftarkan akun",
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        bottom = 10.dp
                    )
                    .fillMaxWidth()
            )



        }

    }
}

@Preview(
    device = Devices.PIXEL_2,
    showSystemUi = true,
    apiLevel = 30
)
@Composable
fun ComposablePreview() {
    MainLayout()
}