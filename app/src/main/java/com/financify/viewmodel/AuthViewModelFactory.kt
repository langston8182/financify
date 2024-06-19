import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.financify.service.TokenDataStore
import com.financify.viewmodel.AuthViewModel

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val context: Context, private val tokenDataStore: TokenDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(context, tokenDataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
