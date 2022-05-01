package nick.template.logging

interface Logger {
    fun d(message: String? = null)
    fun e(message: String? = null, throwable: Throwable? = null)
}
