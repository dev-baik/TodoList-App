package com.example.todolist_app.presentation.register.ui

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist_app.R
import com.example.todolist_app.databinding.FragmentStartUserBinding
import com.example.todolist_app.presentation.base.BaseFragment
import com.example.todolist_app.presentation.utils.applyGrayscale
import com.example.todolist_app.presentation.utils.loadRoundImage

class StartUserFragment : BaseFragment<FragmentStartUserBinding>(R.layout.fragment_start_user) {

    private val args: StartUserFragmentArgs by navArgs()

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()
        setImageUri()
        initAfterBinding()
        setClickListener()
    }

    private fun setClickListener() = with(binding) {
        btnStart.setOnClickListener {
            navigateToTodoList()
        }
    }

    private fun navigateToTodoList() {
        findNavController().navigate(
            StartUserFragmentDirections.actionStartUserFragmentToTodoListFragment(
                userImage = args.userImage
            )
        )
    }

    private fun initAfterBinding() {
        binding.tvUserName.text = args.userName
        binding.tvUserBirthday.text = args.userBirthday
    }

    private fun setImageUri() {
        binding.ivUserImage.loadRoundImage(args.userImage)
        binding.ivUserImage.applyGrayscale()
    }
}
