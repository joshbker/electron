package dev.devous.electron.handler;

import dev.devous.electron.Packet;
import org.jetbrains.annotations.NotNull;

public interface PacketHandler {
    void handle(final @NotNull Packet packet);
}
