package com.example.partyapp.ui.theme.Modelo
import Modelo.HomeActivity
import Modelo.ProviderType
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.partyapp.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import java.security.Provider

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_PartyApp)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "integración de firebase completa")
        analytics.logEvent("InitScreen", bundle)

        setup()

    }

    private fun setup() {
        title = "Autenticación"

        val signUpButton = findViewById<Button>(R.id.singUpButton) // o R.id.signUpButton, según tu XML
        val emailEditText = findViewById<EditText>(R.id.emailRegEditText)
        val contraEditText = findViewById<EditText>(R.id.contraRegEditText)
        val crearButton = findViewById<Button>(R.id.crearButton)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = contraEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                 FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                     if(it.isSuccessful){
                        showHome(it.result?.user?.email?:"", ProviderType.BASIC)
                     }else{
                        showAlert()
                     }
                 }
            }
        }


        crearButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = contraEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email?:"", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("aceptar",null)
        val dialog: AlertDialog=builder.create()
        dialog.show()
    }

    private fun showHome (email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("contraseña", provider.name)
        }
        startActivity(homeIntent)
    }

}