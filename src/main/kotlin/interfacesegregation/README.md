## Interface Segregation Principle (ISP) — RTC SDK Integration

### 📖 Story

We developed an **RTC SDK** at Airmeet that integrates with multiple real-time
communication providers like **Agora**, **100ms**, **Zoom**, and **Daily.co**.

While all providers support **video streaming**, they differ in feature support:
- Some support **screen sharing**
- Others support **active speaker detection**
- Some do **not** support callbacks with speaker UID
- Others offer only a subset of capabilities

This led us to rethink how we define interfaces for provider integration.

### 🚫 Problem

A naive approach is to define a single interface that declares all RTC capabilities —
assuming every provider will support everything.

This forces all providers to implement methods they may not support,
leading to empty implementations, exceptions, or misuse.


```kotlin
interface RtcEngine {
   fun startVideoStream()
   fun stopVideoStream()
   fun enableScreenShare()
   fun onActiveSpeaker(callback: (uid: String) -> Unit)
}
```

### ✅ Solution

---
> By applying the **Interface Segregation Principle**, we broke down the bloated interface into **focused, capability-specific contracts**:
>
> * `IVideoStreamEngine` → for starting and stopping video streams
> * `IScreenShare` → for enabling screen sharing
> * `IActiveSpeaker` → for handling active speaker detection with callbacks
>
> This allows each provider to implement **only the capabilities they support**, making the design more modular, safe, and extensible.

---

