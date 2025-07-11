package dependencyinversion

/**
 * This code follows the Dependency Inversion Principle (DIP).
 *
 * The high-level `HealthMonitor` depends on an abstraction `Notifier` instead of a concrete class.
 * Different notifier types (message, whatsApp, etc.) can be plugged in without modifying `HealthMonitor`.
 *
 * This improves modularity, testability, and allows extension without rewriting business logic.
 */

// Abstraction for any notifier
interface Notifier {
    fun send(patientId: Int, message: String)
}

// Message notifier implementation
class MessageNotifierV2 : Notifier {
    override fun send(patientId: Int, message: String) {
        // here we can fetch child details and send message
        println("📩 Sending SMS for $patientId: $message")
    }
}

// Whatsapp notifier implementation
class WhatsAppNotifier : Notifier {
    override fun send(patientId: Int, message: String) {
        // here we can fetch child details and send message
        println("📩 Sending whatsapp message for $patientId: $message")
    }
}

// Health monitoring logic depends on abstraction
class HealthMonitorV2(
    private val patientId: Int,
    private val notifier: Notifier
) {
    /**
     * Checks parent's health metrics and sends alert if unsafe.
     */
    fun checkHealth(bloodPressure: Int, sugarLevel: Int) {
        if (bloodPressure > BLOOD_PRESSURE_THRESHOLD || sugarLevel > SUGAR_LEVEL_THRESHOLD) {
            val alert = "$patientId has abnormal vitals! BP: $bloodPressure, Sugar: $sugarLevel"
            notifier.send(patientId, alert)
        } else {
            println("$patientId's vitals are normal.")
        }
    }

    companion object {
        const val BLOOD_PRESSURE_THRESHOLD = 140
        const val SUGAR_LEVEL_THRESHOLD = 250
    }
}

/**
 * Demonstrates dependency inversion with notifier abstraction.
 */
fun main() {
    val monitor = HealthMonitorV2(
        patientId = 124,
        notifier = MessageNotifierV2()
    )
    monitor.checkHealth(bloodPressure = 150, sugarLevel = 260) // Should alert
    monitor.checkHealth(bloodPressure = 120, sugarLevel = 100) // Should not alert
}
