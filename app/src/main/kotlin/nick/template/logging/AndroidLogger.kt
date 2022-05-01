package nick.template.logging

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidLogger @Inject constructor() : Logger {
    override fun d(message: String?) {
        Log.d("asdf", message ?: return)
    }

    override fun e(message: String?, throwable: Throwable?) {
        Log.e("asdf", message ?: return, throwable)
    }
}
