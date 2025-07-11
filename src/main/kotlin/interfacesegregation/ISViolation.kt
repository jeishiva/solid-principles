package interfacesegregation

/**
 * This design violates the Interface Segregation Principle (ISP).
 *
 * The RtcEngine interface defines multiple capabilities: video, screen share, and active speaker.
 * All providers are forced to implement every method, even if their platform does not support some features.
 *
 * This leads to fragile code: empty implementations, unsupported operations, or misleading behavior.
 * The design does not respect the fact that capabilities are optional and vary across providers.
 */

interface RtcEngine {
    fun startVideoStream()
    fun stopVideoStream()
    fun enableScreenShare()
    fun onActiveSpeaker(callback: (uid: String) -> Unit)
}

/**
 * Agora supports all features.
 */
class AgoraRtcEngine : RtcEngine {
    override fun startVideoStream() {
        println("Agora: Starting video stream")
    }

    override fun stopVideoStream() {
        println("Agora: Stopping video stream")
    }

    override fun enableScreenShare() {
        println("Agora: Screen share enabled")
    }

    override fun onActiveSpeaker(callback: (uid: String) -> Unit) {
        callback("agora-user-123")
    }
}

/**
 * 100ms does not support screen sharing or active speaker callbacks,
 * but is forced to implement them anyway — violating ISP.
 */
class HundredMsRtcEngine : RtcEngine {
    override fun startVideoStream() {
        println("100ms: Starting video stream")
    }

    override fun stopVideoStream() {
        println("100ms: Stopping video stream")
    }

    override fun enableScreenShare() {
        // Unsupported but must be implemented
        println("100ms: Screen share not supported")
    }

    override fun onActiveSpeaker(callback: (uid: String) -> Unit) {
        // Callback not available, using dummy fallback
        println("100ms: Active speaker not supported")
    }
}

/**
 * Client code expects all engines to support everything.
 * This leads to false assumptions and runtime inconsistencies.
 */
fun main() {
    val engines: List<RtcEngine> = listOf(
        AgoraRtcEngine(),
        HundredMsRtcEngine()
    )

    engines.forEach { engine ->
        engine.startVideoStream()
        engine.enableScreenShare()
        engine.onActiveSpeaker { uid -> println("Active speaker UID: $uid") }
    }
}
