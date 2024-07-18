package com.kukufm.midhun.spacex

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kukufm.midhun.spacex.R
import com.kukufm.midhun.spacex.databinding.ActivityMainBinding
import com.kukufm.midhun.spacex.utils.Network
import com.kukufm.midhun.spacex.utils.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //network change listner
        observeNetworkChange()

    }

    private fun observeNetworkChange(){
        GlobalScope.launch {
            Network.observeNetworkStatus().collect{
                when(it){
                    NetworkStatus.AVAILABLE -> {
                        // no need to do anything here
                        withContext(Dispatchers.Main){
                            binding.notifyOfflineView.visibility = View.GONE
                        }
                    }
                    NetworkStatus.LOSING, NetworkStatus.UNAVAILABLE -> {
                        withContext(Dispatchers.Main){
                            binding.notifyOfflineView.visibility = View.VISIBLE
                            Toast.makeText(this@MainActivity,"Losing Network",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}