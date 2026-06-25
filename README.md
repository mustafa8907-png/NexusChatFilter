# NexusChatFilter 🛡️

**NexusChatFilter** is a lightweight, highly optimized, and customizable chat protection plugin designed for Spigot and Bukkit servers (1.16.5 - 1.21.x). It natively supports modern MiniMessage (RGB/Gradients) text formatting without relying on the Paper API!

Developed by **mustafa8907** | [Discord Support](https://discord.gg/nexussetups) | [Website](https://mustafa8907.com.tr)

---

## ⚡ Features

- **No Paper Dependency:** Runs perfectly on standard Spigot/Bukkit utilizing an embedded (shaded) Kyori Adventure library.
- **Full MiniMessage Support:** Use standard `<red>`, `<bold>`, or even `<gradient:#ff4e50:#f9d423>Text</gradient>` in your configurations.
- **Advanced AntiCaps System:** Detects if a player is screaming in the chat based on a customizable percentage threshold. It can automatically warn them and lowercase their text, or issue a mute/ban.
- **Tier-Based Word Filtering:** Configure different severities for caught words:
  - `Tier 1`: Heavy profanity (Executes strict commands like Ban).
  - `Tier 2`: Advertising and regular swears (Executes medium commands like Mute).
  - `Tier 3`: Minor offensive words (Sends a built-in, customizable warning message).
- **Smart Actions:** You can configure tiers to run console commands or simply use the `"warn"` keyword to send a lightweight, internal plugin message to the player.
- **Role Bypasses:** Exempt specific roles (like admins or moderators) from the filter smoothly.
- **Extremely Lightweight:** Uses an efficient `HashSet` memory structure to process chat events in nanoseconds, preventing chat lag.

## 🛠️ Installation

1. Download the latest `NexusChatFilter.jar` from the [Releases](../../releases) page.
2. Drop the file into your server's `plugins` folder.
3. Restart or reload the server.
4. Customize the configurations located in `plugins/NexusChatFilter/`.
5. Use `/nexusfilter reload` to apply your changes in real-time.

## ⚙️ Configuration Files

NexusChatFilter uses 3 main configuration files to keep everything organized:
- `config.yml` - Contains core logic, caps threshold, bypass roles, and the commands/actions executed per tier.
- `messages.yml` - Contains plugin prefix, no-permission alerts, and internal warning messages formatted in MiniMessage.
- `Blocked-Words.yml` - Your dictionary of blocked words categorized by tiers.

## 🔑 Commands & Permissions

| Command | Description | Permission Node |
|---------|-------------|-----------------|
| `/nexusfilter reload` | Reloads all configuration files | `nexuschatfilter.admin` |
| **Bypass Filter** | Allows a player to bypass the filter | `nexuschatfilter.bypass.<role>` |

*(Note: The bypass role must match the ones defined under `bypass-roles` in `config.yml`. Example: `nexuschatfilter.bypass.admin`)*

## 🚀 Building from Source

This project uses Maven and GitHub Actions for continuous integration.

1. Clone the repository: `git clone https://github.com/YourName/NexusChatFilter.git`
2. Navigate to the directory: `cd NexusChatFilter`
3. Build the project: `mvn clean package`
4. Find the compiled jar in the `target/` directory.

---
*Created with ❤️ by the Nexus Setups Team.*
