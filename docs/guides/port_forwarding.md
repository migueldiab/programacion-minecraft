# Secure Port Forwarding Guide

This guide provides step-by-step instructions for opening local ports securely to allow friends to join your Minecraft server.

## Table of Contents
1. [Pre-flight Checks](#pre-flight-checks)
2. [Method 1: Standard Port Forwarding (Recommended for Direct Connection)](#method-1-standard-port-forwarding)
3. [Method 2: Tunneling (Best for CGNAT & Security)](#method-2-tunneling)
4. [Security Best Practices](#security-best-practices)

---

## Pre-flight Checks

Before attempting to forward ports, you must determine if your Network Address Translation (NAT) type allows it. Many modern ISPs use **CGNAT** (Carrier-Grade NAT), which prevents traditional port forwarding.

**How to detect CGNAT:**
1. Log into your router's web interface and find your **WAN IP**.
2. Visit [WhatIsMyIP.com](https://www.whatismyip.com/).
3. **Compare the two.** If the WAN IP on your router does not match the IP shown on the website, you are behind CGNAT. Proceed to **Method 2**.

---

## Method 1: Standard Port Forwarding

Use this method if your WAN IP matches your router's WAN IP.

### Step 1: Set a Static Local IP
Your computer's internal IP address might change. You must assign a static one so the router always knows where to send the traffic.
- **Windows:** Settings > Network & Internet > Ethernet/Wi-Fi > Edit IP Assignment > Manual > Toggle IPv4.
- **Router Tip:** Look for "DHCP Reservation" in your router settings to lock your device's MAC address to a specific IP (e.g., `192.168.1.50`).

### Step 2: Configure Router Rules
1. Access your router's gateway (usually `192.168.1.1` or `192.168.0.1`).
2. Locate the **Port Forwarding**, **NAT**, or **Virtual Server** section.
3. Create a new rule:
   - **Service Name:** Minecraft_Java
   - **Protocol:** TCP
   - **External/Internal Port:** `25565`
   - **Internal IP:** Your Static Local IP (from Step 1).
4. (Optional) Add another rule for Bedrock Edition:
   - **Protocol:** UDP
   - **Port:** `19132`

### Step 3: Open Software Firewalls
Even if the router allows the traffic, your OS might block it.
- **Windows:** Search for "Windows Defender Firewall with Advanced Security" > Inbound Rules > New Rule > Port > TCP `25565` > Allow Connection.
- **Linux (ufw):** `sudo ufw allow 25565/tcp`

---

## Method 2: Tunneling (Bypass CGNAT)

If you are behind CGNAT or want a simpler setup without touching router settings, use a tunneling service. These create a secure bridge between your PC and a public relay.

### Option A: Playit.gg (Highly Recommended)
[Playit.gg](https://playit.gg) acts as a global proxy.
1. Download and run the Playit agent.
2. Follow the link provided to claim your account and create a "Tunnel."
3. Select the **Minecraft Java** template.
4. Playit provides a custom URL (e.g., `something.playit.gg`) that your friends can use to connect directly.

### Option B: Tailscale (Private Mesh VPN)
Best for permanent play with a consistent group of friends.
1. Install [Tailscale](https://tailscale.com) on your host and all players' machines.
2. Log in with the same account (or share a "Node Key").
3. Players connect using your **Tailscale IP** (the internal IP assigned by the mesh).

---

## Security Best Practices

- **Minimize Exposure:** Only open the specific ports required (25565 or 19132). Never open ports like 22 (SSH) or 3389 (RDP) to the public internet unless absolutely necessary and secured with keys.
- **Strong Passwords:** Ensure your Minecraft server's authentication (online-mode=true) is active to prevent unauthorized users from joining.
- **Update Firmware:** Keep your router's firmware up to date to patch known security vulnerabilities.
- **DDoS Protection:** For high-traffic servers, consider a VPS (Virtual Private Server) or Dedicated Host which provides built-in DDoS mitigation.

---
*Reference: Based on Hosting and Port Forwarding Research Report (Nov 2026).*
`,path: