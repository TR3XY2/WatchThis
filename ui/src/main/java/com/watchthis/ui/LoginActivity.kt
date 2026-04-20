package com.watchthis.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import android.widget.Toast

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val interactor by lazy { ServiceLocator.provideInteractor(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestId()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Якщо вже увійшов — пропускаємо логін
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            goToMain(account)
            return
        }

        findViewById<Button>(R.id.btnGoogleLogin).setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                goToMain(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Помилка входу: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMain(account: GoogleSignInAccount) {
        lifecycleScope.launch {
            try {
                // Get or create user in database
                val userId = interactor.getOrCreateUserByGoogleId(
                    account.id ?: "unknown",
                    account.email,
                    account.displayName
                )
                
                // Store user info in UserManager
                UserManager.setCurrentUser(userId, account.id ?: "unknown", account.email, account.displayName)
                
                // Navigate to MainActivity
                startActivity(Intent(this@LoginActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Помилка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 1001
    }
}