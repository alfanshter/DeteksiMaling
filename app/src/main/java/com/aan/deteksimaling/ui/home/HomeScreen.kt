package com.aan.deteksimaling.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aan.deteksimaling.R
import com.aan.deteksimaling.data.MalingModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    var alarm = fetchAlarm()

    Scaffold { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(156.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.imgheaderberanda),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Deteksi Maling",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Card that overlaps the previous Box
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    modifier = Modifier
                        .size(width = 340.dp, height = 84.dp)
                        .align(Alignment.TopCenter) // Align the Card at the top center of the parent Box
                        .offset(y = 100.dp) // Offset the Card to overlap with the Box (adjust as needed)
                        .zIndex(1f) // Ensure the Card is drawn on top
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Alarm ",
                            textAlign = TextAlign.Center,
                            color = Color.DarkGray,
                        )
                        Text(
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 20.sp,
                            text = if (alarm == 1) "BUNYI" else "MATI",
                            textAlign = TextAlign.Center,
                        )
                    }

                }

            }


            // Card that overlaps the previous Box
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                elevation = CardDefaults.elevatedCardElevation(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .offset(y = 40.dp) // Offset the Card to overlap with the Box (adjust as needed)
                    .zIndex(1f) // Ensure the Card is drawn on top
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 50.dp, bottom = 50.dp)
                ) {

                    Button(
                        onClick = { navController.navigate("cctv") },
                        Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFFEA4E4E)
                        ),
                        shape = ShapeDefaults.Large

                    ) {
                        Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                            Image(
                                painter = painterResource(id = R.drawable.imgcamera),
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "Monitoring Toko ", color = Color.White, fontSize = 15.sp)


                        }

                    }

                    Spacer(modifier = Modifier.height(40.dp))
                    Button(
                        onClick = { navController.navigate("maling")},
                        Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFFEA4E4E)
                        ),
                        shape = ShapeDefaults.Large

                    ) {
                        Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                            Image(
                                painter = painterResource(id = R.drawable.btnburger),
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "Data Maling", color = Color.White, fontSize = 15.sp)


                        }

                    }

                }

            }


        }
    }

}

@Composable
fun fetchAlarm() : Int {
    val database = FirebaseDatabase.getInstance()
    val itemsRef = database.getReference("maling")
    var alarm by remember { mutableStateOf(0) }

    val listener = object  : ValueEventListener{
        override fun onDataChange(p0: DataSnapshot) {
            val data = p0.child("buzzer").value.toString().toInt()
            alarm = data
        }

        override fun onCancelled(p0: DatabaseError) {

        }

    }

    itemsRef.addValueEventListener(listener)
    return alarm
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())

}