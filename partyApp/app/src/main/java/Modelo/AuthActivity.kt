package com.example.partyapp.ui.theme.Modelo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.partyapp.R
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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
        FirebaseApp.initializeApp(this)

        setup()

    }

    private fun setup() {
        title = "Autenticación"

        val signUpButton = findViewById<Button>(R.id.singUpButton) // o R.id.signUpButton, según tu XML
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val contraEditText = findViewById<EditText>(R.id.contraEditText)
        val crearButtonWelcome = findViewById<Button>(R.id.crearButtonWelcome)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = contraEditText.text.toString()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        setContentView(R.layout.activity_pantalla_inicio)
                      //  showHome(email, ProviderType.BASIC)
                    } else {

                        if (task.exception is FirebaseAuthUserCollisionException) {

                            showAlert()
                        } else {
                            //showAlert("Error al crear el usuario")
                        }
                    }
                }
        }




        crearButtonWelcome.setOnClickListener {

            val registro = Intent(this,HomeActivity::class.java)
            startActivity(registro)
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