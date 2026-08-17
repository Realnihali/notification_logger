# 📲 Notification Logger (Android Application)

An Android application designed to log incoming system notifications in real-time and forward them seamlessly to a designated Telegram chat using the Telegram Bot API.

---

> ### ⚠️ Disclaimer & Usage Notice
> * **Educational & Awareness Purpose Only:** This application is created strictly for study, system logging awareness, and educational purposes.
> * **Use At Your Own Risk:** The developer holds no responsibility or liability for any consequences, data exposure, or misuse of this tool. The end-user is fully responsible for complying with personal privacy standards and device permissions.
> * **Important Configuration:** You **must replace** the default placeholder `TELEGRAM_BOT_TOKEN` and `TELEGRAM_CHAT_ID` with your own credentials before compiling or running the app. Do not push your private tokens to public repositories.

---

## ✨ Features

* **Live Notification Interception:** Reads incoming status-bar notifications across apps via Android's `NotificationListenerService`.
* **Instant Forwarding:** Forwards app names, notification titles, messages, and timestamps directly to your Telegram chat.
* **Background Service:** Operates persistently as a background service without requiring constant UI focus.
* **Clean & Lightweight:** Optimized to minimize battery drain and memory footprint.

---

## 🛠️ Configuration & Setup

### 1. Telegram Setup
1. Create a bot using [@BotFather](https://t.me/BotFather) on Telegram to get your **Bot Token**.
2. Find your Telegram **Chat ID** (via [@userinfobot](https://t.me/userinfobot) or similar).

### 2. Update Credentials in App
Before building the project, navigate to the configuration file in Android Studio and add your credentials:

```java
// Example Configuration
public class Config {
    public static final String TELEGRAM_BOT_TOKEN = "YOUR_BOT_TOKEN_HERE";
    public static final String TELEGRAM_CHAT_ID = "YOUR_CHAT_ID_HERE";
}
