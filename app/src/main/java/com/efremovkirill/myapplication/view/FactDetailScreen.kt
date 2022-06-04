package com.efremovkirill.myapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.efremovkirill.myapplication.R
import com.efremovkirill.myapplication.Util
import com.efremovkirill.myapplication.data.FactModel

@Composable
fun FactDetailScreen(
    navController: NavController,
    fact: FactModel
) {

    val context = LocalContext.current

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.westernbg),
        contentDescription = "Western background",
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier,
                text = "Fact Collection",
                fontFamily = FontFamily(Font(R.font.bebas)),
                fontSize = 48.sp,
                color = Color.Black
            )
        }
        Box(
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        ) {
            Column {
                Text(
                    modifier = Modifier,
                    color = Color.LightGray,
                    text = "Fact ID: ${fact.id}",
                    fontFamily = FontFamily(Font(R.font.bebas)),
                    fontSize = 18.sp,
                )
                Text(
                    modifier = Modifier,
                    text = "\"${fact.value}\"",
                    fontFamily = FontFamily(Font(R.font.bebas)),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Justify,
                    color = Color.White
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Created at: ${Util.cutDate(fact.created_at)}",
                        fontFamily = FontFamily(Font(R.font.bebas)),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Updated at: ${Util.cutDate(fact.updated_at)}",
                        fontFamily = FontFamily(Font(R.font.bebas)),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                Row(
                    Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp).clickable {
                            Util.shareFact(context = context, factId = fact.id, factContent = fact.value)
                        },
                        painter = painterResource(R.drawable.share),
                        tint = Color.White,
                        contentDescription = "Share button"
                    )
                    Icon(
                        modifier = Modifier.size(48.dp).clickable {
                            Util.openUrl(context = context, url = "https://api.chucknorris.io/jokes/${fact.id}")
                        },
                        painter = painterResource(R.drawable.ic_link),
                        contentDescription = "Url button",
                        tint = Color.White
                    )
                }

                val clicked = remember { mutableStateOf(false) }
                if(clicked.value) {
                    navController.popBackStack()
                    clicked.value = false
                }
                ModButton(
                    text = "Back",
                    buttonColor = Color.Black,
                    shapeDp = 15.dp,
                    textColor = Color.White,
                    textSize = 20.sp,
                    clicked = clicked
                )
            }
        }
    }
}