
package firebase.app.gsingup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {
    companion object{
        private const val RC_SIGN_IN = 100
    }
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        //Configuracion de FireBase
        mAuth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.btn_GsingIn).setOnClickListener{
            signIn()
        }
    }
    private fun signIn(){
        val sigInIntent = googleSignInClient.signInIntent
        startActivityForResult(sigInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if (task.isSuccessful){
                try{
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("AppGSIGN", "FirebaseWithGoogle: " + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                }catch (e:ApiException){
                    Log.w("AppGSIGN", "Autentication sin exito", e)
                }

            }else{
                Log.w("AppGSIGN", exception.toString())
            }



        }
    }
    private fun firebaseAuthWithGoogle(idtoken:String){
        val credential = GoogleAuthProvider.getCredential(idtoken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) {task->
                if(task.isSuccessful){
                    Log.d("AppGSIGN", "Credenciales aceptadas")
                    val user = mAuth.currentUser
                    val intent = Intent(this,AccountActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Log.w("AppGSIGN", "Credenciales rechazadas" , task.exception)
                }


            }
    }
}