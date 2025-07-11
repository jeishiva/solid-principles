package openclosed

import com.jeishiva.common.Message


/**
 * This design follows the Open/Closed Principle.
 *
 * Each message type has its own renderer class implementing a common interface.
 * To add support for a new message (e.g., audio), we just create a new class.
 *
 * Existing code remains untouched — we extend the system without modifying it.
 * This improves flexibility, maintainability, and testing.
 */

interface MessageRenderer {
    fun render(message: Message)
}

/**
 * Renderer for plain text messages.
 */
class TextMessageRenderer : MessageRenderer {
    override fun render(message: Message) {
        // Display text message
        println("Text: ${message.content}")
    }
}

/**
 * Renderer for image messages.
 */
class ImageMessageRenderer : MessageRenderer {
    override fun render(message: Message) {
        // Display image preview
        println("Image: [preview for ${message.content}]")
    }
}

/**
 * Renderer for video messages.
 */
class VideoMessageRenderer : MessageRenderer {
    override fun render(message: Message) {
        // Display video thumbnail
        println("Video: [thumbnail for ${message.content}]")
    }
}

/**
 * RendererRegistry is a pluggable rendering dispatcher.
 *
 * This class maintains a map of message types to their respective renderers.
 * Rendering logic is decoupled and extensible — new types can be added without modifying existing code.
 *
 * Benefits:
 * --------------------------------------------------
 * - Adheres to the Open/Closed Principle
 * - Supports dynamic registration of renderers
 * - Keeps rendering logic modular and clean
 */
class RenderingEngine {
    private val renderers = mutableMapOf<String, MessageRenderer>()

    fun register(type: String, renderer: MessageRenderer) {
        renderers[type] = renderer
    }

    fun render(type: String, message: Message) {
        renderers[type]?.render(message) ?: println("Unknown type: $type")
    }
}
