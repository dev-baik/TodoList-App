package com.example.todolist_app.presentation.register.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist_app.R
import com.example.todolist_app.databinding.FragmentRegisterUserBinding
import com.example.todolist_app.presentation.base.BaseFragment
import com.example.todolist_app.presentation.register.ui.CameraActivity.Companion.IMAGE_URI
import com.example.todolist_app.presentation.register.viewmodel.RegisterUserViewModel
import com.example.todolist_app.presentation.utils.DatePickerDialogUtil
import com.example.todolist_app.presentation.utils.DateUtil
import com.example.todolist_app.presentation.utils.loadRoundImage
import com.example.todolist_app.presentation.utils.setAlphabeticInputFilter
import com.example.todolist_app.presentation.utils.showToast
import java.time.LocalDate

class RegisterUserFragment : BaseFragment<FragmentRegisterUserBinding>(R.layout.fragment_register_user) {

    private val viewModel: RegisterUserViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                requestLauncher.launch(Intent(requireContext(), CameraActivity::class.java))
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    showPermissionRationalDialog()
                } else {
                    showPermissionSettingDialog()
                }
            }
        }

    private val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra(IMAGE_URI)?.let { imageUri ->
                viewModel.setImageUri(imageUri)
            }
        }
    }

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()
        binding.view = this@RegisterUserFragment
        binding.viewModel = this@RegisterUserFragment.viewModel
        binding.etUserName.setAlphabeticInputFilter()
        setViewModelObserve()
        viewModel.initDoneEvent()
    }

    private fun setViewModelObserve() = with(viewModel) {
        doneEvent.observe(viewLifecycleOwner) {
            if (it.second.isNotEmpty()) {
                requireContext().showToast(it.second)
                if (it.first) {
                    navigateToStartUser()
                }
            }
        }

        imageUri.observe(viewLifecycleOwner) {
            binding.bgCamera.loadRoundImage(it)
            binding.ivCamera.isVisible = it.isEmpty()
        }
    }

    private fun navigateToStartUser() {
        findNavController().navigate(
            RegisterUserFragmentDirections.actionRegisterUserFragmentToStartUserFragment(
                userImage = viewModel.imageUri.value.toString(),
                userName = binding.etUserName.text.toString(),
                userBirthday = binding.btnUserBirthday.text.toString(),
            )
        )
    }

    fun showDatePickerDialog() {
        DatePickerDialogUtil.showDatePickerDialog(
            context = requireContext(),
            initialDate = LocalDate.of(viewModel.year, viewModel.month, viewModel.day),
            onDateSelected = { selectedDate ->
                updateViewModelWithSelectedDate(selectedDate)
            }
        )
    }

    private fun updateViewModelWithSelectedDate(
        selectedDate: LocalDate
    ) = with(viewModel) {
        year = selectedDate.year
        month = selectedDate.monthValue
        day = selectedDate.dayOfMonth
        setUserBirthday(formatDateToString())
    }

    private fun formatDateToString(): String {
        return DateUtil.convertPrintRegisterDateString(
            LocalDate.of(viewModel.year, viewModel.month, viewModel.day)
        )
    }

    fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                requestLauncher.launch(Intent(requireContext(), CameraActivity::class.java))
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireContext() as Activity,
                Manifest.permission.CAMERA
            ) -> {
                showPermissionRationalDialog()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showPermissionRationalDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("카메라 권한을 켜주셔야지 앱을 정상적으로 사용할 수 있습니다.")
            .setPositiveButton("권한 허용하기") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .show()
    }

    private fun showPermissionSettingDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("카메라 권한을 켜주셔야지 앱을 정상적으로 사용할 수 있습니다. 앱 설정 화면으로 진입하셔서 권한을 켜주세요.")
            .setPositiveButton("권한 변경하러 가기") { _, _ ->
                navigateToAppSetting()
            }
            .setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .show()
    }

    private fun navigateToAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", requireContext().packageName, null)
        }
        startActivity(intent)
    }
}
