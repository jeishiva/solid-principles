package liskovsubstitution

/**
 * This design follows the Liskov Substitution Principle (LSP).
 *
 * Play and rewind are modeled as separate capabilities using interfaces.
 * RecordedVideo supports both, LiveStream only supports play.
 *
 * Clients interact only with the interfaces they need,
 * ensuring safe substitutions and avoiding runtime surprises.
 */

// Common capability: all media types must be playable
interface Playable {
    fun play()
}

// Separate capability: only some media types support rewind
interface Rewindable {
    fun rewind()
}

/**
 * Recorded videos support both play and rewind.
 */
class RecordedVideoV2 : Playable, Rewindable {
    override fun play() {
        println("Playing recorded video")
    }

    override fun rewind() {
        println("Rewinding recorded video")
    }
}

/**
 * Live streams support only play, not rewind.
 */
class LiveStreamV2 : Playable {
    override fun play() {
        println("Playing live stream")
    }
}

// --------------------- Client Code ---------------------

fun main() {
    val recorded = RecordedVideoV2()
    val live = LiveStreamV2()

    playMedia(recorded)
    playMedia(live)

    rewindMedia(recorded)  // OK
    // rewindMedia(live)   // Compile error: live is not Rewindable
}

/**
 * Plays any media that supports Playable.
 */
fun playMedia(media: Playable) {
    media.play()
}

/**
 * Rewinds only media that supports Rewindable.
 */
fun rewindMedia(media: Rewindable) {
    media.rewind()
}
