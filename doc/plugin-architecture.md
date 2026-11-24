# Plugin Architecture Overview

In the context of Minecraft servers running on platforms like **PaperMC**, understanding how plugins are structured is fundamental for development. This document explains the core components: the JAR file, the configuration manifest, and the Java class hierarchy.

## 1. The .jar File (Java ARchive)
A `.jar` file is the final product of your build process. It is essentially a ZIP archive containing:
- **Compiled Bytecode**: The `.class` files generated from your `.java` source files. These contain the actual logic the JVM executes.
- **Resources**: Files required by the plugin, such as `plugin.yml`, config files (`config.yml`), textures, or sounds.
- **Manifest**: A metadata file inside the ZIP (META-INF/MANIFEST.MF) that specifies which class is the primary entry point.

When the server starts, it scans the `plugins/` folder, identifies every `.jar` file, and attempts to load them into its own JVM memory space.

## 2. The `plugin.yml` File
Every PaperMC plugin requires a `plugin.yml` file in its root directory. This is the **manifest** that the server reads before anything else. Without it, the server won't know your plugin exists.

### Key Fields Explained:
- `name`: The display name of your plugin.
- `version`: Helps users and the server track updates.
- `main`: The **absolute path** to your primary Java class (e.g., `com.example.helloworld.HelloWorld`). This is where the server looks for the `onEnable()` method.
- `api-version`: Tells the server which version of the Paper API you are targeting (e.g., `1.21`).
- `commands`: A list of commands registered directly through this file.
- `permissions`: Defines what permissions are required to run specific commands.

## 3. Class Structure & Lifecycle
Minecraft plugins follow a standard lifecycle managed by the server.

### Entry Point
Your main class must extend a base class (like `JavaPlugin`) and override two primary methods:
- `onEnable()`: Executed when the server starts or when the plugin is manually enabled. Use this for registering commands, listeners, and connecting to databases.
- `onDisable()`: Executed when the server shuts down or the plugin is disabled. Use this for saving data and cleaning up resources.

### Component Organization
To keep code manageable, we organize classes by responsibility:
1.  **Main Class**: Handles initialization and global state.
2.  **Command Classes**: Dedicated classes for processing player inputs (e.g., `/hello`).
3.  **Listener Classes**: Implement the `Listener` interface. They use `@EventHandler` annotations to react to game events (like a player joining, breaking a block, or chatting).
4.  **Manager/Utility Classes**: Helper classes for data persistence, configuration handling, or complex math/logic.

### Example Hierarchy (for our project):
```text
com.example.helloworld
├── HelloWorld.java         (Main Class / Entry Point)
├── commands
│   └── HelloCommand.java   (Command Logic)
├── listeners
│   └── JoinListener.java   (Event Handling)
└── utils
    └── ConfigManager.java   (Helper Utilities)
```
