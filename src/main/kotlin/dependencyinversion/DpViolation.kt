package dependencyinversion

/**
 * This code violates the Dependency Inversion Principle (DIP).
 *
 * The high-level `HealthMonitor` module directly depends on the low-level `MessageNotifier` implementation.
 * As a result, changes to how notifications are delivered require modifying health logic.
 *
 * Abstractions are missing — logic is tied to a single notification mechanism.
 * This creates tight coupling, poor testability, and makes it hard to introduce new types of notifiers.
 *
 */

// Low-level module: Message delivery
class MessageNotifier {
    fun sendMessage(patientId: Int, message: String) {
        // here we can fetch child details and send message
        println("📩 Sending SMS: $message")
    }
}

// High-level module: Health monitoring logic
class HealthMonitor(
    private val patientId: Int,
    private val notifier: MessageNotifier
) {
    /**
     * Checks parent's health metrics and sends alert if unsafe.
     */
    fun checkHealth(bloodPressure: Int, sugarLevel: Int) {
        if (bloodPressure > BLOOD_PRESSURE_THRESHOLD || sugarLevel > SUGAR_LEVEL_THRESHOLD) {
            val alert = "$patientId has abnormal vitals! BP: $bloodPressure, Sugar: $sugarLevel"
            notifier.sendMessage(patientId, alert)
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
 * Demonstrates direct dependency between health monitor and message system.
 */
fun main() {
    val monitor = HealthMonitor(
        patientId = 124,
        MessageNotifier()
    )
    monitor.checkHealth(bloodPressure = 150, sugarLevel = 260) // Should alert
    monitor.checkHealth(bloodPressure = 120, sugarLevel = 100) // Should not alert
}
