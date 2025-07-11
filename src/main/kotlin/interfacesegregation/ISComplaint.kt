package interfacesegregation

/**
 * This design follows the Interface Segregation Principle (ISP).
 *
 * Each interface defines a focused capability: video streaming, screen sharing, or active speaker detection.
 * RTC providers implement only the interfaces relevant to them, avoiding unnecessary or unsupported methods.
 *
 * This makes the design modular, testable, and safer for extension — while preventing misuse or fake implementations.
 */

// Core capability: starting/stopping video
interface IVideoStreamEngine {
    fun startVideoStream()
    fun stopVideoStream()
}

// Optional capability: screen sharing
interface IScreenShare {
    fun enableScreenShare()
}

// Optional capability: active speaker detection
interface IActiveSpeaker {
    fun onActiveSpeaker(callback: (uid: String) -> Unit)
}

/**
 * Agora supports all three capabilities.
 */
class AgoraRtcEngineV2 : IVideoStreamEngine, IScreenShare, IActiveSpeaker {
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
 * 100ms supports only video.
 * It is not forced to implement unrelated capabilities.
 */
class HundredMsRtcEngineV2 : IVideoStreamEngine {
    override fun startVideoStream() {
        println("100ms: Starting video stream")
    }

    override fun stopVideoStream() {
        println("100ms: Stopping video stream")
    }
}

/**
 * Client uses only capabilities that a provider declares support for.
 */
fun main() {
    val agora = AgoraRtcEngineV2()
    val hundredMs = HundredMsRtcEngineV2()

    val videoEngines: List<IVideoStreamEngine> = listOf(
        agora, hundredMs
    )
    // Generic video operations
    videoEngines.forEach {
        it.startVideoStream()
        it.stopVideoStream()
    }

    // Only screen sharing engines
    val screenSharingEngines: List<IScreenShare> = listOf(agora)
    screenSharingEngines.forEach {
        it.enableScreenShare()
    }

    // Only active speaker engines
    val speakerEngines: List<IActiveSpeaker> = listOf(agora)
    speakerEngines.forEach {
        it.onActiveSpeaker { uid ->
            println("Active speaker UID: $uid")
        }
    }
}
