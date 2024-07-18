package com.kukufm.midhun.spacex.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kukufm.midhun.spacex.R
import com.kukufm.midhun.spacex.databinding.FragmentLaunchDetailsBinding
import com.kukufm.midhun.spacex.models.LaunchInfoModel
import com.kukufm.midhun.spacex.models.room.FavTable
import com.kukufm.midhun.spacex.repository.room.LocalDatabase
import com.kukufm.midhun.spacex.utils.NetworkStates
import com.kukufm.midhun.spacex.viewmodels.LaunchDetailsViewModel
import com.squareup.picasso.Picasso

class LaunchDetailsFragment : Fragment() {

    private var _binding : FragmentLaunchDetailsBinding? = null
    val binding get() = _binding!!

    private lateinit var viewModel: LaunchDetailsViewModel
    private lateinit var selectedItemModel : LaunchInfoModel
    private lateinit var localDatabase: LocalDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LaunchDetailsViewModel::class.java]
        selectedItemModel = viewModel.selectedLaunchDetailItem!!
        localDatabase = LocalDatabase.getDatabase(requireContext())

        //check if it is liked already
        viewModel.checkIfAlreadyLiked(selectedItemModel.flight_number,localDatabase)
        observeFavStatusChange()

        Picasso.get()
            .load(selectedItemModel.links?.mission_patch)
            .error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.detailMissionPatch)

        binding.apply {
            detailsLaunchYear.text = selectedItemModel.launch_year?:""
            detailsMissionName.text = selectedItemModel.mission_name?:""
            detailsRocketName.text = selectedItemModel.rocket?.rocket_name?:""
            rocketId.text = selectedItemModel.rocket?.rocket_id?:""
            rocketName.text = selectedItemModel.rocket?.rocket_name?:""
            rocketType.text = selectedItemModel.rocket?.rocket_type?:""
            launchYear.text = selectedItemModel.launch_year?:""
            launchDateUtc.text = selectedItemModel.launch_date_utc?:""
            launchDateLocal.text = selectedItemModel.launch_date_local?:""
            details.text = selectedItemModel.details?:""
            coreSerial.text = selectedItemModel.rocket?.first_stage!!.cores[0].core_serial?:""
            flight.text = selectedItemModel.rocket?.first_stage!!.cores[0].flight.toString()?:""
            block.text = selectedItemModel.rocket?.first_stage!!.cores[0].block.toString()?:""
            landingType.text = selectedItemModel.rocket?.first_stage!!.cores[0].landing_type?:""
            landingVehicle.text = selectedItemModel.rocket?.first_stage!!.cores[0].landing_vehicle?:""
            gridfins.text = selectedItemModel.rocket?.first_stage!!.cores[0].gridfins.toString()?:""
            legs.text = selectedItemModel.rocket?.first_stage!!.cores[0].legs.toString()?:""
            reused.text = selectedItemModel.rocket?.first_stage!!.cores[0].reused.toString()?:""
            payloadId.text = selectedItemModel.rocket?.second_stage!!.payloads[0].payload_id?:""
            nationality.text = selectedItemModel.rocket?.second_stage!!.payloads[0].nationality?:""
            manufacturer.text = selectedItemModel.rocket?.second_stage!!.payloads[0].manufacturer?:""
            nationality.text = selectedItemModel.rocket?.second_stage!!.payloads[0].nationality?:""
            payloadType.text = selectedItemModel.rocket?.second_stage!!.payloads[0].payload_type?:""
            payloadMassKg.text = selectedItemModel.rocket?.second_stage!!.payloads[0].payload_mass_kg.toString()?:""
            payloadMassLbs.text = selectedItemModel.rocket?.second_stage!!.payloads[0].payload_mass_lbs.toString()?:""
            orbit.text = selectedItemModel.rocket?.second_stage!!.payloads[0].orbit?:""
            siteId.text = selectedItemModel.launch_site?.site_id?:""
            siteName.text = selectedItemModel.launch_site?.site_name?:""
            siteNameLong.text = selectedItemModel.launch_site?.site_name_long?:""
            missionPatchSmall.text = selectedItemModel.links?.mission_patch_small?:""
            articleLink.text = selectedItemModel.links?.article_link?:""
            wikipedia.text = selectedItemModel.links?.wikipedia?:""
            videoLink.text = selectedItemModel.links?.video_link?:""
        }

        binding.favToggleBtn.setOnClickListener{
            if(binding.favToggleBtn.isChecked){
                viewModel.addToFavourites(FavTable(selectedItemModel.flight_number),localDatabase)
            }else{
                viewModel.removeFromFavourites(FavTable(selectedItemModel.flight_number),localDatabase)
            }
        }

    }

    private fun observeFavStatusChange() {
        viewModel.likedStatus.observe(viewLifecycleOwner){
            when(it){
                is NetworkStates.Success -> {
                    if(it.data!=null){
                        binding.favToggleBtn.isChecked = it.data
                    }else{
                        Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkStates.Error -> {
                    Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
                }
                is NetworkStates.Loading -> {

                }
            }
        }
    }
}