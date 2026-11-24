package com.example.reward.model;

import java.io.Serializable;
import java.util.UUID;

public record PlayerData(UUID id, long lastGrantTime, int totalRewards) implements Serializable {}
