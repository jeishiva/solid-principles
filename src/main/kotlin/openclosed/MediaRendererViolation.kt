package com.jeishiva.openclosed

import com.jeishiva.common.Message
import com.jeishiva.common.MessageType

/**
 * ❌ This class violates the Open/Closed Principle.
 *
 * It handles rendering for multiple message types using a `when` block.
 * Every time a new message type (e.g., audio, location) is added,
 * we must modify this class directly.
 *
 * This leads to fragile code, tight coupling, and poor extensibility.
 * Changes to rendering logic can unintentionally affect existing formats.
 */

class MediaRendererViolationDemo {

    fun render(message: Message) {
        // Rendering logic varies based on message type
        when (message.type) {
            MessageType.Text -> renderText(message)
            MessageType.Image -> renderImage(message)
            MessageType.Video -> renderVideo(message)
        }
    }

    private fun renderText(message: Message) {
        // Render a text message on screen
        println("Text: ${message.content}")
    }

    private fun renderImage(message: Message) {
        // Render an image preview on screen
        println("Image: [preview for ${message.content}]")
    }

    private fun renderVideo(message: Message) {
        // Render a video thumbnail on screen
        println("Video: [thumbnail for ${message.content}]")
    }
}

