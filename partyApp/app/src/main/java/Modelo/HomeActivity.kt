package Modelo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.partyapp.R
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bundle = intent.extras
        val email= bundle?.getString("email")
        val contra = bundle?.getString("contraseña")
        setup(email?:"",contra?:"")
    }

    private fun setup(email: String, provider: String){
        title="inicio"
        val emailEditText = findViewById<EditText>(R.id.emailRegEditText)
        val contraEditText = findViewById<EditText>(R.id.contraRegEditText)

        val crearButton = findViewById<Button>(R.id.crearButton)

        val email = emailEditText.text.toString()
        val contra = contraEditText.text.toString()

        crearButton.setOnClickListener(){
            val email = emailEditText.text.toString()
            val contra = contraEditText.text.toString()

            if (email.isNotEmpty() && contra.isNotEmpty()) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contra).addOnCompleteListener{
                    if(it.isSuccessful){
                        setContentView(R.layout.activity_pantalla_inicio)
                        showHome(it.result?.user?.email?:"", ProviderType.BASIC)
                    }else{
                        Log.i("Pantalla Principal","Error de autenticación")
                        showAlert()
                    }
                }
            }
            FirebaseAuth.getInstance().signOut()
            onBackPressed()


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