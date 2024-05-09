package com.mazer.bongobet.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.ActivityLoginBinding
import com.mazer.bongobet.domain.entities.User
import com.mazer.bongobet.ui.common.IntentExtras
import com.mazer.bongobet.ui.MainActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupView()
        handleObservers()
    }

    private fun handleObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collectLatest {
                    handleLoginUiState(it)
                }
            }
        }
    }

    private fun handleLoginUiState(state: LoginUiState) {
        when (state){
            LoginUiState.Initial -> {}
            LoginUiState.LoadingFacebook -> {}
            LoginUiState.LoadingGoogle -> {

            }
            is LoginUiState.LoginError -> {}
            is LoginUiState.LoginSuccess -> {
                goNextScreen(state.user.displayName, state.user.email, state.user.profileUri)
            }
        }
    }

    private fun setupView() {
        binding.btnLoginGoogle.root.setOnClickListener {
            setupGoogleLogin(true)
        }
        binding.btnLoginFacebook.root.setOnClickListener {

        }
    }

    private fun setupGoogleLogin(filterAuthorized: Boolean){
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(filterAuthorized)
            .setServerClientId(getString(R.string.gmail_web_client_id))
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(this)
        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@LoginActivity,
                )
                handleSignIn(result)

            } catch (ex: Exception) {
                if (filterAuthorized)
                    setupGoogleLogin(false)
                else
                    Log.d("err", ex.message?:"err")

                //TODO(MOSTRA ERRO GOOGLE PLAY)

            }
        }

    }

    private fun goNextScreen(displayName: String?, email: String, uriProfile: String?){
        val intent = Intent(this, MainActivity::class.java)
        if (displayName != null) {
            intent.putExtra(IntentExtras.EXTRA_NAME, displayName)
        }
        intent.putExtra(IntentExtras.EXTRA_EMAIL, email)
        if (uriProfile != null){
            intent.putExtra(IntentExtras.EXTRA_EMAIL_URI, uriProfile)
        }
        startActivity(intent)
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            is PublicKeyCredential -> {
                val responseJson = credential.authenticationResponseJson
            }

            is PasswordCredential -> {
                val username = credential.id
                val password = credential.password
            }

            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val displayName = googleIdTokenCredential.displayName
                        val email = googleIdTokenCredential.id
                        val profilePictureUi = googleIdTokenCredential.profilePictureUri
                        //
                        viewModel.handleEvent(LoginUiEvent.LoginGoogleSuccess(
                            User(
                                displayName ?: "",
                                email,
                                profilePictureUi?.toString() ?: ""
                            )
                        ))
                    } catch (e: GoogleIdTokenParsingException) {
                        //TODO Catch any unrecognized custom credential type here.
                    }
                } else {
                    //TODO Catch any unrecognized custom credential type here.
                }
            }

            else -> {
                //TODO Catch any unrecognized credential type here.
            }
        }
    }

}