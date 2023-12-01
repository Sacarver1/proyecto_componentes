package com.example.partyapp.ui.theme.Modelo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.partyapp.R
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "integración de firebase completa")
        analytics.logEvent("InitScreen", bundle)
        FirebaseApp.initializeApp(this)
        setup()
        //val bundle = intent.extras
        //val email= bundle?.getString("email")
        //val password = bundle?.getString("password")


    }

    private fun setup(){
        title="inicio"
        val emailEditText = findViewById<EditText>(R.id.emailRegEditText)
        val contraEditText = findViewById<EditText>(R.id.contraRegEditText)

        val crearButton = findViewById<Button>(R.id.crearButton)

        crearButton.setOnClickListener(){

            val email = emailEditText.text.toString()
            val password = contraEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){

                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        //setContentView(R.layout.activity_pantalla_inicio)
                        Log.w("FirebaseAuth", "createUserWithEmailAndPassword succesfull: ${it.exception}", it.exception)
                    }else{
                        Log.w("FirebaseAuth", "createUserWithEmailAndPassword failed: ${it.exception}", it.exception)
                        showAlert()

                    }
                }
            }
            //FirebaseAuth.getInstance().signOut()
            //onBackPressed()

        }



    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("aceptar",null)
        val dialog: AlertDialog =builder.create()
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