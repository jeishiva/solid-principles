## Open/Closed Principle (OCP) — WhatsApp Media Renderer

### 📖 Story

WhatsApp initially supported only **text messages**. Over time, it added support for **images**, **videos**, and other media formats.

The **home screen** now needs to render multiple message types dynamically.

This brings up a key design question:

> How do we support new media types **without modifying existing rendering logic**?

### 🚫 Problem

A naive implementation might use `if` or `when` blocks to check message type and render accordingly.  
This violates the **Open/Closed Principle** — because every new format forces us to **modify** existing, stable code.

### ✅ Solution

By applying the **Open/Closed Principle**, we make the system:

- **Open for extension** → New media types can be added as new classes.
- **Closed for modification** → Existing renderer logic remains untouched.

This is done by defining an interface like `MessageRenderer`,
and implementing it for each media type.


---

### 📁 Files

| File                     | Purpose                              |
|--------------------------|--------------------------------------|
| `MediaRendererViolation.kt` | Violates OCP — logic is not extensible |
| `MediaRendererCompliant.kt` | Follows OCP — clean, extensible design |

