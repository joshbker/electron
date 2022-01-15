package dev.devous.electron;

import com.mongodb.client.MongoCollection;
import dev.devous.electron.handler.PacketHandler;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledExecutorService;

public final class Electron {
    private final @NotNull PacketQueue packetQueue;

    public Electron(final @NotNull MongoCollection<Document> collection, final @NotNull PacketHandler packetHandler,
                    final @NotNull ScheduledExecutorService scheduledExecutorService) {
        packetQueue = new PacketQueue(collection);
        new PacketListener(packetQueue, packetHandler, scheduledExecutorService).subscribe();
    }

    public @NotNull PacketQueue packetQueue() {
        return packetQueue;
    }
}
