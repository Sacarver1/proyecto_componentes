package Modelo

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
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
            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }


    }
}