## Dependency Inversion Principle (DIP) — Health Alert Notification System

### 📖 Story

We’re building a system that monitors elderly parents’ health data — like **blood pressure** and **blood sugar** — using smart wearables (e.g., watches).

If a parent’s health reading crosses a dangerous threshold, the system should **notify their children**.

Initially, we support **only message notifications**, but over time we may want to support:
- **Email**
- **Phone call**
- **WhatsApp**
- **Push notifications**

### 🚫 Problem

In a naive implementation, the monitoring logic depends **directly on concrete notifier classes**, like `MessageNotifier`.

This creates tight coupling between the **health logic** and the **delivery mechanism**.  
Any new channel requires modifying the health logic — violating the **Dependency Inversion Principle**.

> High-level modules (health monitor) depend on low-level details (message, email).

### ✅ Solution

We introduce an abstraction: `Notifier`.  
Each delivery method (message, email, call) implements this interface.

Now:
- The health monitoring logic **depends only on `Notifier`**
- New channels can be added by injecting new `Notifier` implementations
- The system becomes **testable, extensible, and loosely coupled**

