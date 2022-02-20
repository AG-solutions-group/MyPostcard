package app.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypostcard.databinding.ActivityMainBinding

lateinit var bindingMain: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // just a simple activity with a button to start the archive
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        bindingMain.startBtn.setOnClickListener(){
            intent = Intent(this, ArchiveActivity::class.java)
            startActivity(intent)
        }
    }
}