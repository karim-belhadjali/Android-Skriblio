package com.main.skriblio.ui.setup.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.main.skriblio.R
import com.main.skriblio.databinding.FragmentUsernameBinding
import com.main.skriblio.ui.setup.UsernameViewModel
import com.main.skriblio.util.Constants.MAX_USERNAME_LENGTH
import com.main.skriblio.util.Constants.MIN_USERNAME_LENGTH
import com.main.skriblio.util.hideKeyboard
import com.main.skriblio.util.navigateSafely
import com.main.skriblio.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class UsernameFragment : Fragment(R.layout.fragment_username) {

    private var _binding: FragmentUsernameBinding? = null
    private val binding: FragmentUsernameBinding
        get() = _binding!!

    private val viewModel: UsernameViewModel by viewModels()

    private lateinit var callbackManager: CallbackManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUsernameBinding.bind(view)

        callbackManager = CallbackManager.Factory.create();


        val loginButton = binding.loginButton
        loginButton.fragment = this

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
            }

            override fun onCancel() {
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })


        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    // App code
                    Timber.d(loginResult?.recentlyGrantedPermissions.toString())
                }

                override fun onCancel() {
                    Timber.d("canceled")
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })


        listenToEvents()

        binding.btnNext.setOnClickListener {
            viewModel.validateUsernameAndNavigateToSelectRoom(
                binding.etUsername.text.toString()
            )
            requireActivity().hideKeyboard(binding.root)
        }
    }

    private fun listenToEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.setupEvent.collect { event ->
                when (event) {
                    is UsernameViewModel.SetupEvent.NavigateToSelectRoomEvent -> {
                        findNavController().navigateSafely(
                            R.id.action_usernameFragment_to_selectRoomFragment,
                            args = Bundle().apply { putString("username", event.username) }
                        )
                    }
                    is UsernameViewModel.SetupEvent.InputEmptyError -> {
                        snackbar(R.string.error_field_empty)
                    }
                    is UsernameViewModel.SetupEvent.InputTooShortError -> {
                        snackbar(getString(R.string.error_username_too_short, MIN_USERNAME_LENGTH))
                    }
                    is UsernameViewModel.SetupEvent.InputTooLongError -> {
                        snackbar(getString(R.string.error_username_too_long, MAX_USERNAME_LENGTH))
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}