package com.efremovkirill.myapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.efremovkirill.myapplication.viewmodel.FactViewModel
import com.efremovkirill.myapplication.R
import com.efremovkirill.myapplication.data.FactModel
import com.efremovkirill.myapplication.navigation.NavScreen
import com.efremovkirill.myapplication.navigation.navigate
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FactsScreen(
    navController: NavController,
    factViewModel: FactViewModel
) {
    val facts: List<FactModel>? = factViewModel.factData.observeAsState().value
    val scope = CoroutineScope(Dispatchers.IO + CoroutineName("Facts Scope"))

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.westernbg),
        contentDescription = "Western background",
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 64.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = if (facts?.isEmpty() == true) "Fact Collection is empty" else "Fact Collection",
                fontFamily = FontFamily(Font(R.font.bebas)),
                fontSize = 48.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            if (facts?.isEmpty() == false)
                Icon(
                    modifier = Modifier.size(48.dp).clickable {
                        scope.launch {
                            factViewModel.dropTable()
                        }
                    },
                    painter = painterResource(R.drawable.ic_baseline_delete_24),
                    contentDescription = "Drop collection button"
                )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(facts.orEmpty()) { fact ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable {
                        navController.navigate(NavScreen.FactDetail.route, bundleOf("FACT_KEY" to fact))
                    },
                    shape = RoundedCornerShape(3.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Column {
                            Text(
                                modifier = Modifier,
                                text = "Fact ID: ${fact.id}",
                                fontFamily = FontFamily(Font(R.font.bebas)),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Text(
                                modifier = Modifier,
                                text = "\"${fact.value}\"",
                                fontFamily = FontFamily(Font(R.font.bebas)),
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Justify,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }

    }
}
