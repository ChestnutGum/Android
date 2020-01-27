package fr.isen.kelly.androidtoolbox
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //on lance la page de login
        val logActivity = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(logActivity)
    }
}
