package nick.template.list.shared

import nick.template.logging.Logger

class NoOpLogger : Logger {
    override fun d(message: String?) = Unit
    override fun e(message: String?, throwable: Throwable?) = Unit
}
