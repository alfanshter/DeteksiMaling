package com.aan.deteksimaling.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil.compose.rememberImagePainter
import com.aan.deteksimaling.R
import com.aan.deteksimaling.data.MalingModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat

@Composable
fun MalingScreen(navController: NavController, useFakeData: Boolean =false) {
    val items = fetchMaling(useFakeData)

    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        Box(
            modifier = androidx.compose.ui.Modifier
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
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 20.sp,
                        text = "RIWAYAT MALING",
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
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 50.dp, bottom = 50.dp)
            ) {
//                item {  }
                items(items) { item ->
                    ItemCard(item = item)
                }
            }

        }


    }

}

@Composable
fun ItemCard(item: MalingModel) {
    // Format input
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val date = inputFormat.parse(item.created_at)

    // Format output yang diinginkan
    val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    val formattedDate = outputFormat.format(date)

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(item.foto),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = formattedDate,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun MalingScreenPreview() {
    MalingScreen(navController = rememberNavController(),useFakeData = true)
}

val fakeMalingData = listOf(
    MalingModel(foto = "https://via.placeholder.com/150", created_at = "2024-06-21 21:28:05"),
    MalingModel(foto = "https://via.placeholder.com/150", created_at = "2024-06-20 14:15:30"),
    MalingModel(foto = "https://via.placeholder.com/150", created_at = "2024-06-19 09:00:00")
)


@Composable
fun fetchMaling(fakeData: Boolean = false): List<MalingModel> {

    if (fakeData){
        return fakeMalingData
    }
    val database = FirebaseDatabase.getInstance()
    val itemsRef = database.getReference("riwayat")
    var items by remember { mutableStateOf(listOf<MalingModel>()) }

    val itemListener = object : ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {
            val newItems = mutableListOf<MalingModel>()
            for (snapshot in p0.children) {
                val item = snapshot.getValue(MalingModel::class.java)
                if (item != null) {
                    newItems.add(item)
                }
            }

            items = newItems
        }

        override fun onCancelled(p0: DatabaseError) {

        }


    }

    itemsRef.addValueEventListener(itemListener)

    return items
}