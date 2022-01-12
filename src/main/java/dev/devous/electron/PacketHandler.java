package dev.devous.electron;

import org.jetbrains.annotations.NotNull;

public interface PacketHandler {
    void handle(final @NotNull Packet packet);
}
