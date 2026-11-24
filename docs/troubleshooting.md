# Connection Testing & Troubleshooting Guide

This guide provides a systematic approach to diagnosing and resolving connectivity issues between clients and the Minecraft server.

## 1. Basic Verification
Before investigating network layers, ensure the fundamentals are correct.
- **Is the server running?** Check the console or use a process monitor.
- **Are you using the correct IP?** Verify against your public IP (if hosted at home) or the provided VPS IP.
- **Are you using the correct Port?** Default is `25565`. Ensure you aren't missing the port suffix (e.g., `:25565`).
- **Does the version match?** Ensure both client and server are on `1.21.11` (or current target).

## 2. Network Reachability Tests
Use these tools to determine where the packet is dropping.

### A. Ping Test
Check if the host machine is reachable.
```bash
ping <SERVER_IP>
```
*   **Success:** The host is online. Proceed to Port checks.
*   **Failure (Request timed out):** The host is down, behind a strict firewall, or the IP is incorrect.

### B. Port Connectivity (Telnet/NC)
Check if the specific Minecraft port is open and listening.
```bash
# Using nc (Netcat)
nc -vz <SERVER_IP> 25565

# Using Telnet
telnet <SERVER_IP> 25565
```
*   **Connected:** The port is open. The issue is likely internal to Minecraft (authentication, whitelist).
*   **Connection Refused:** The server software isn't listening on that port, or a local firewall (Windows Firewall/iptables) is rejecting connections.
*   **Timeout:** A router or ISP is dropping the packets before they reach the destination.

## 3. Common Scenarios & Fixes

| Symptom | Likely Cause | Recommended Action |
| :--- | :--- | :--- |
| **"Connection Refused"** | Server Offline / Port Closed | 1. Restart server software.<br>2. Check `server.properties` for correct `server-port`.<br>3. Ensure the Java process has permission to bind to the port. |
| **"Timed Out" (Immediate)** | Firewall Blocking | 1. Add an inbound rule to the OS firewall for TCP/UDP 25565.<br>2. If on AWS/Oracle/GCP, add a Security Group/Ingress rule. |
| **"Timed Out" (After 30s)** | ISP/Router Block | 1. Verify Port Forwarding rules on the router.<br>2. Check for CGNAT; if present, use [Playit.gg](https://playit.gg) or Tailscale. |
| **"Invalid Session"** | Auth/Online Mode | 1. If using a cracked client, set `online-mode=false` in `server.properties` (Warning: No UUID protection).<br>2. Otherwise, ensure the player is logged into their Microsoft account. |

## 4. CGNAT Workarounds
If your ISP does not provide a public IP (CGNAT), standard port forwarding will not work. Use one of the following:
1. **Playit.gg:** Install the agent and map a global tunnel to your local port.
2. **Tailscale/ZeroTier:** Create a private mesh VPN. Both the server and the client must be joined to the same tailnet.

## 5. Debugging Workflow Summary
1. **Verify** Server Status $\rightarrow$ **Ping** Host $\rightarrow$ **Netcat** Port $\rightarrow$ **Check Firewalls** $\rightarrow$ **Verify Credentials**.
