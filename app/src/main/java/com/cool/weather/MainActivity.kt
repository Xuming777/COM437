package com.cool.weather

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cool.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBusiness = Business()

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initView()
    }

    private fun initView() {
        viewBinding.searchButton.setOnClickListener(View.OnClickListener {
            val townStr = viewBinding.townEt.editText?.text.toString()
            val stateStr = viewBinding.stateEt.editText?.text.toString()
            //get weather info
            if (!TextUtils.isEmpty(townStr) && !TextUtils.isEmpty(stateStr)) {
                mBusiness.getWeatherInfo(
                    this,
                    townStr,
                    stateStr,
                    "US",
                    object : IBeanCallback<WeatherBean> {
                        override fun onSuccess(t: WeatherBean?) {
                            println("WeatherBean: $t")
                            // show WeatherBean info
                            runOnUiThread {
                                viewBinding.weatherTemperature.text = "${t?.temp}℉"
                                viewBinding.weatherDescription.text =
                                    "Description: ${t?.description}"
//
                            }
                        }

                        override fun onError(error: String?) {
                            ToastUtils.showToast(error)
                        }

                    })
            }
            //get Ambee
            if (!TextUtils.isEmpty(townStr)) {
                mBusiness.getAmbeeInfo(this, townStr, object : IBeanCallback<AmbeeBean> {
                    override fun onSuccess(t: AmbeeBean?) {
                        runOnUiThread {
                            println("AmbeeBean: $t")
                            // show AmbeeBean info
                            viewBinding.ambeePollutant.text =
                                "Pollutant: ${t?.pollutant}"
                            viewBinding.ambeeConcentration.text =
                                "Concentration: ${t?.concentration}"
                            viewBinding.ambeeCategory.text = "Category: ${t?.category}"
                            viewBinding.ambeeAQI.text = "AQI: ${t?.aqi}"
                        }
                    }

                    override fun onError(error: String?) {
                        ToastUtils.showToast(error)
                    }

                })
            }
        })
        viewBinding.settingButton.setOnClickListener(View.OnClickListener { //                goto SettingsActivity
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        })

    }

    private var lastClickTime: Long = 0
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val curTime = System.currentTimeMillis()
            if (curTime - lastClickTime > 2000) {
                ToastUtils.showToast("Press again to quit the app!")
                lastClickTime = curTime
            } else {
                finish()
            }
            true
        } else {
            super.onKeyUp(keyCode, event)
        }
    }
}