package com.mkapps.ecard

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.github.ybq.android.spinkit.SpinKitView
import com.mkapps.ecard.managers.ServiceManager
import com.mkapps.ecard.model.LoginData
import com.mkapps.ecard.model.LoginResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var spinKitView : SpinKitView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        spinKitView = findViewById(R.id.spin_kit)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        getAccessToken()
    }
    public fun showLoading(showLoading: Boolean) {
        runOnUiThread {
            spinKitView.visibility = View.GONE
            if (showLoading) {
                spinKitView.visibility = View.VISIBLE
            }
        }

    }
    fun getAccessToken(){
        val webservice = ServiceManager.getInstance()
        showLoading(true)
        webservice.webservice.getUser(LoginData("user1","123456a@")).enqueue(object:
            Callback<LoginResponseModel> {
            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                showLoading(false)
                if (response.isSuccessful && response.body()?.data != null) {
                    Log.d(MainActivity::class.java.simpleName, "login -> ${response?.body()?.data}")
                    response?.body()?.data?.token?.let {
                        val pref = getSharedPreferences("ECard", MODE_PRIVATE)
                        pref.edit().putString("token","Bearer ${it}").commit()
                    }

                } else {
                    MaterialDialog(this@MainActivity).show {
                        title(R.string.error)
                        message(text = response.message())
                        positiveButton (text = "OK",click = {

                        })

                        lifecycleOwner(this@MainActivity)

                    }
                }
            }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                showLoading(false)
                MaterialDialog(this@MainActivity).show {
                    title(R.string.error)
                    message(text = t.localizedMessage)
                    positiveButton (text = "OK",click = {

                    })

                    lifecycleOwner(this@MainActivity)

                }
            }
        })
    }
}