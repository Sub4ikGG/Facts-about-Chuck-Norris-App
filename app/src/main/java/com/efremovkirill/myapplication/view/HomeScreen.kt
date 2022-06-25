package com.efremovkirill.myapplication.view

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.efremovkirill.myapplication.viewmodel.FactViewModel
import com.efremovkirill.myapplication.viewmodel.HomeScreenViewModel
import com.efremovkirill.myapplication.R
import com.efremovkirill.myapplication.Util
import com.efremovkirill.myapplication.data.FactModel
import kotlinx.coroutines.*

private const val DELAY = 1000L

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    factViewModel: FactViewModel
) {
    val factLiveData = homeScreenViewModel.factLiveData.observeAsState()
    val fact = remember {
        mutableStateOf(
            factLiveData.value
        )
    }; fact.value = factLiveData.value

    val context = LocalContext.current
    val loading = remember { mutableStateOf(false) }
    val online = remember { mutableStateOf(true) }
    if (Build.VERSION.SDK_INT >= 23) online.value = Util.isOnline(LocalContext.current)

    /*Background*/
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.westernbg),
        contentDescription = "Western background",
        contentScale = ContentScale.Crop
    )

    /*ProgressIndicator*/
    if (loading.value) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp).padding(8.dp),
            color = Color.White
        )
    }

    /*Network dead*/
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (!online.value) {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
                    .clickable {
                        Toast.makeText(
                            context,
                            "No Internet-Connection\nFacts only from collection",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                painter = painterResource(R.drawable.ic_network_dead),
                contentDescription = "Network dead icon",
                tint = Color.White
            )
        }
    }

    /*
    * Main Content
    * */
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(R.drawable.chucknorris_logo),
                contentDescription = "Chuck Norris logo"
            )
        }
        Box(
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        ) {
            Column {
                Text(
                    modifier = Modifier,
                    color = Color.LightGray,
                    text = "Fact ID: ${fact.value?.id}",
                    fontFamily = FontFamily(Font(R.font.bebas)),
                    fontSize = 18.sp,
                )
                Text(
                    modifier = Modifier,
                    text = "\"${fact.value?.value}\"",
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
                        text = "Created at: ${fact.value?.created_at.let { it?.let { it1 -> Util.cutDate(it1) } }}",
                        fontFamily = FontFamily(Font(R.font.bebas)),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Updated at: ${fact.value?.updated_at?.let { Util.cutDate(it) }}",
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
                            Util.shareFact(
                                context = context,
                                factId = fact.value!!.id,
                                factContent = fact.value!!.value
                            )
                        },
                        painter = painterResource(R.drawable.share),
                        tint = Color.White,
                        contentDescription = "Share button"
                    )
                    Icon(
                        modifier = Modifier.size(48.dp).clickable {
                            Util.openUrl(context = context, url = "https://api.chucknorris.io/jokes/${fact.value?.id}")
                        },
                        painter = painterResource(R.drawable.ic_link),
                        contentDescription = "Like button",
                        tint = Color.White
                    )
                }

                val clicked = remember { mutableStateOf(false) }
                val scope = CoroutineScope(Dispatchers.IO + CoroutineName("Coroutine #1"))
                if (clicked.value && !loading.value) {
                    scope.launch {
                        if (online.value)
                            refreshFact(
                                homeScreenViewModel = homeScreenViewModel,
                                factViewModel = factViewModel,
                                state = clicked,
                                loading = loading
                            )
                        else {
                            var temp = factViewModel.getRandomFact()

                            if (temp != null)
                                homeScreenViewModel.invokeFact(temp)
                            else {
                                temp = FactModel(
                                    0,
                                    "r0b33dl1n7",
                                    "Once Chuck Norris robbed a collection..",
                                    "2020-01-05 13:42:18.823766",
                                    "2020-01-05 13:42:18.823766",
                                    "https://r0b33dl1n7"
                                )

                                if (!factViewModel.factAvailable("r0b33dl1n7")) {
                                    Log.d("Facts", "Fact r0b33dl1n7 not available, insert to db.")
                                    factViewModel.insertFact(temp)
                                }
                                homeScreenViewModel.invokeFact(temp)
                            }

                            Log.d("Test", "Get fact ${fact.value}")
                            clicked.value = false
                        }
                    }
                }
                ModButton(
                    modifier = Modifier.padding(bottom = 48.dp),
                    text = "Give me a new fact",
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

suspend fun refreshFact(
    homeScreenViewModel: HomeScreenViewModel,
    factViewModel: FactViewModel,
    state: MutableState<Boolean>,
    loading: MutableState<Boolean>
) = coroutineScope {
    loading.value = true; delay(DELAY)
    Log.d("Facts", "Refresh Fact")

    val result = async {
        homeScreenViewModel.getRandomFact()
    }
    result.await()?.let {
        if (!factViewModel.factAvailable(it.id)) {
            Log.d("Facts", "Fact ${it.id} not available, insert to db.")
            factViewModel.insertFact(it)
        } else {
            Log.d("Facts", "Fact ${it.id} available.")
        }
    }

    state.value = false
    loading.value = false
}
