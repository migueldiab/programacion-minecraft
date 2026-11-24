# Background Process Management & Persistence

To ensure the Minecraft server remains active even after terminal disconnection or unexpected crashes, we use **systemd** to manage the process.

## Overview
Systemd allows us to define the server as a service. This provides several advantages:
- **Auto-restart**: The server restarts automatically if the process dies.
- **Persistence**: The server starts automatically on system boot.
- **Background Operation**: You can close your SSH session without killing the server.

## Service Configuration

Create a service file at `/etc/systemd/system/minecraft.service`:

```ini
[Unit]
Description=Minecraft Paper Server
After=network.target

[Service]
Type=simple
User=madrax
Group=madrax
WorkingDirectory=/home/madrax/src/ai/programacion-minecraft
ExecStart=/usr/bin/java -Xms4G -Xmx4G -jar bin/paper.jar nogui
Restart=always
RestartSec=10

StandardOutput=append:/home/madrax/src/ai/programacion-minecraft/logs/server.log
StandardError=append:/home/madrax/src/ai/programacion-minecraft/logs/server.log

[Install]
WantedBy=multi-user.target
```

### Parameter Explanations:
- **ExecStart**: The command to launch the server. Adjust `-Xms` and `-Xmx` based on available memory.
- **Restart=always**: Ensures that if the Java process exits (due to crash or manual stop), systemd attempts to bring it back up.
- **RestartSec=10**: Waits 10 seconds before attempting a restart.

## Management Commands

Use the following commands to control the server:

| Action | Command |
| :--- | :--- |
| Start Server | `sudo systemctl start minecraft` |
| Stop Server | `sudo systemctl stop minecraft` |
| Restart Server | `sudo systemctl restart minecraft` |
| Check Status | `sudo systemctl status minecraft` |
| Enable on Boot | `sudo systemctl enable minecraft` |
| View Logs | `journalctl -u minecraft -f` |

## Verification
To verify the persistence, you can stop the server manually and check if it comes back online (if configured to do so) or simply confirm that starting it with `systemctl` works regardless of your current shell session.
