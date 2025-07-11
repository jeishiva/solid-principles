package liskovsubstitution

/**
 * This design violates the Liskov Substitution Principle (LSP).
 *
 * LiveStream inherits from VideoPlayer but overrides the `rewind()` method
 * to throw an exception — breaking the expected behavior of clients using VideoPlayer.
 *
 * This means LiveStream cannot safely be substituted where a VideoPlayer is expected,
 * causing runtime errors or defensive programming in client code.
 */

open class VideoPlayer {
    open fun play() {
        // Start video playback
        println("Playing video")
    }

    open fun rewind() {
        // Rewind video to previous position
        println("Rewinding video")
    }
}

class RecordedVideo : VideoPlayer() {
    override fun rewind() {
        // Rewind is supported in recorded videos
        println("Rewinding recorded video")
    }
}

class LiveStream : VideoPlayer() {
    override fun rewind() {
        // Violates LSP: Live stream doesn't support rewind
        throw UnsupportedOperationException("Live stream cannot be rewound")
    }
}


// --------------------- Client Code ---------------------
fun main() {
    val recorded = RecordedVideo()
    val live = LiveStream()
    playAndRewind(recorded) // Works fine
    playAndRewind(live)     // Crashes
}


/**
 * Client function that assumes all video players support rewind.
 */
fun playAndRewind(video: VideoPlayer) {
    video.play()
    video.rewind() // Unsafe: LiveStream will throw here
}


/**
 * Function that assumes all video players support rewind.
 */
fun replay(video: VideoPlayer) {
    video.rewind() // Will crash if LiveStream is passed
}