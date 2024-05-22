package com.mazer.bongobet.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.mazer.bongobet.R
import com.mazer.bongobet.databinding.ActivityMainBinding
import com.mazer.bongobet.domain.entities.User
import com.mazer.bongobet.domain.entities.Wallet
import com.mazer.bongobet.ui.login.LoginActivity
import com.mazer.bongobet.ui.wallet.WalletActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleObservers()
        setupView()
    }


    private fun handleMainUiState(state: MainUiState) {
        when(state){
            is MainUiState.Initial -> {}
            is MainUiState.UserNotLoggedIn -> {
                goToLoginScreen()
            }
            is MainUiState.UserLoggedIn -> {
                setupUserData(state.user)
            }
            is MainUiState.WalletLoadedSuccess -> {
                setupWallet(state.wallet)
                binding.toolbar.loadingWallet.visibility = View.GONE
            }
            is MainUiState.WalletLoadedError -> {
                binding.toolbar.loadingWallet.visibility = View.GONE
            }
        }
    }

    private fun setupWallet(wallet: Wallet) {
        binding.toolbar.tvSaldo.visibility = View.VISIBLE
        binding.toolbar.tvSaldo.text = getString(R.string.wallet_quantity, "%.2f".format(wallet.balance))
    }

    private fun handleObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainState.collectLatest {
                    handleMainUiState(it)
                }
            }
        }
    }

    private fun goToLoginScreen() {
        val it = Intent(this, LoginActivity::class.java)
        startActivity(it)
    }

    private fun goToWalletScreen() {
        val it = Intent(this, WalletActivity::class.java)
        startActivity(it)
    }

    private fun setupView() {
        binding.toolbar.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.layoutWallet.setOnClickListener {
            goToWalletScreen()
        }
    }

    private fun setupUserData(user: User) {
        Glide.with(binding.root.context).load(user.profileUri.toUri()).into(binding.toolbar.ivUserImg)
    }

}