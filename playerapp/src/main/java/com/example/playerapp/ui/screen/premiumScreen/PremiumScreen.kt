package com.example.playerapp.ui.screen.premiumScreen

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.R

@Composable
fun PremiumScreen(navController: NavHostController = rememberNavController()) {
    AndroidView(modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.fragment_premium, null, false)
            val imgBack = view.findViewById<ImageView>(R.id.imgBack)

            imgBack.setOnClickListener {
                navController.popBackStack()
            }

            view // return the view
        },
        update = { view ->
            val textView = view.findViewById<TextView>(R.id.tvTitle)
        }
    )
}

@Preview
@Composable
private fun PremiumScreenScreenPreview() {
    PremiumScreen()
}

