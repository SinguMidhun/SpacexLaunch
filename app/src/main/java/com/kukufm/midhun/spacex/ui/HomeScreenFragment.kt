package com.kukufm.midhun.spacex.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kukufm.midhun.spacex.MainActivity
import com.kukufm.midhun.spacex.R
import com.kukufm.midhun.spacex.adapter.LaunchDetailsAdapter
import com.kukufm.midhun.spacex.adapter.LaunchItemClickListner
import com.kukufm.midhun.spacex.databinding.FragmentHomeScreenBinding
import com.kukufm.midhun.spacex.models.LaunchInfoModel
import com.kukufm.midhun.spacex.repository.room.LocalDatabase
import com.kukufm.midhun.spacex.utils.NetworkStates
import com.kukufm.midhun.spacex.utils.SharedPreference
import com.kukufm.midhun.spacex.viewmodels.LaunchDetailsViewModel
import kotlinx.coroutines.delay

class HomeScreenFragment : Fragment(), LaunchItemClickListner, OnClickListener{

    val TAG = "HomeScreenFragment"
    private var _binding : FragmentHomeScreenBinding? = null
    val binding get() = _binding!!

    private lateinit var adapter: LaunchDetailsAdapter
    private lateinit var viewModel: LaunchDetailsViewModel
    private lateinit var localDatabase: LocalDatabase
    private lateinit var sharedPreferences: SharedPreferences
    private var isLocalApiCalled = false
    private val launchDetailsList = arrayListOf<LaunchInfoModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LaunchDetailsViewModel::class.java]
        adapter = LaunchDetailsAdapter(launchDetailsList,this,requireContext())
        localDatabase = LocalDatabase.getDatabase(requireContext())
        sharedPreferences = SharedPreference.getSharedPrefRepoInstance()

        //call api and set observer
        if(SharedPreference.isDataSavedToLocal()){
            // data is saved to room so fetch from room
            viewModel.getAllLaunchDetailsFromLocal(localDatabase)
        }else{
            // data is not saved to room, so call remote api
            viewModel.getAllLaucheDetails()
        }
        observeForLaunchDetails()

        //setting up recycler view and adapter
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHome.adapter = adapter

        binding.homeSearchBtn.setOnClickListener(this)
        binding.homeSearchEdittext.apply {
            addTextChangedListener {
                it?.let {
                    viewModel.launchDetailsByName.removeObservers(viewLifecycleOwner)
                    launchDetailsList.clear()
                    adapter.notifyDataSetChanged()
                    if(it.toString().isEmpty()){
                        binding.homeProgress.visibility = VISIBLE
                        if(SharedPreference.isDataSavedToLocal()){
                            viewModel.getAllLaunchDetailsFromLocal(localDatabase)
                        }else{
                            viewModel.getAllLaucheDetails()
                        }
                    }
                }
            }
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.home_search_btn ->{
                val searchName = binding.homeSearchEdittext.text.toString()
                if(searchName.isEmpty()){
                    Toast.makeText(requireContext(),"Please enter name to search",Toast.LENGTH_SHORT).show()
                }else{
                    if(SharedPreference.isDataSavedToLocal()){
                        viewModel.getLaunchDetailsByNameFromLocal(searchName,localDatabase)
                    }else{
                        viewModel.getLaunchDetailsByName(searchName)
                    }
                    observeForSearchedLaunchDetails()
                }
            }
        }
    }

    private fun observeForLaunchDetails() {
        viewModel.bulkLaunchDetails.observe(viewLifecycleOwner){
            binding.homeProgress.visibility = View.GONE
            when(it){
                is NetworkStates.Success ->{
                    if(it.data!=null){
                        launchDetailsList.clear()
                        launchDetailsList.addAll(it.data)
                        if(!isLocalApiCalled){
                            Log.d("MidhunSingu", "called")
                            if(!SharedPreference.isDataSavedToLocal()){
                                viewModel.addLaunchInfoModel(it.data,localDatabase)
                            }
                            isLocalApiCalled = true
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
                is NetworkStates.Error ->{
                    Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "observeForLaunchDetails: ${it.message}")
                }
                is NetworkStates.Loading ->{
                    binding.homeProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeForSearchedLaunchDetails() {
        viewModel.launchDetailsByName.observe(viewLifecycleOwner){
            binding.homeProgress.visibility = View.GONE
            when(it){
                is NetworkStates.Success ->{
                    if(it.data!=null){
                        launchDetailsList.clear()
                        launchDetailsList.addAll(it.data)
                        adapter.notifyDataSetChanged()
                    }
                }
                is NetworkStates.Error ->{
                    Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "observeForSearchedLaunchDetails: ${it.message}")
                }
                is NetworkStates.Loading ->{
                    binding.homeProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onItemClicker(position: Int) {
        val clickedItemModel = launchDetailsList.get(position)
        viewModel.updateSelectedLaunchDetailItem(clickedItemModel)
        findNavController().navigate(R.id.action_homeScreenFragment_to_launchDetailsFragment)
    }

}