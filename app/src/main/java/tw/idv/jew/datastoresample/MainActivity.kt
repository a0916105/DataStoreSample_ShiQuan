package tw.idv.jew.datastoresample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tw.idv.jew.datastoresample.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            viewModel.increaseCounter()
            viewModel.updateCounter()
        }

        viewModel.counter.asLiveData().observe(this) {
            binding.result.text = "Click count: $it"
        }
    }
}