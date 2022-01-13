package dev.devous.electron;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledExecutorService;

public final class Electron {
    public Electron(final @NotNull MongoCollection<Document> collection, final @NotNull PacketHandler packetHandler,
                    final @NotNull ScheduledExecutorService scheduledExecutorService) {
        PacketQueue packetQueue = new PacketQueue(collection);
        new PacketListener(packetQueue, packetHandler, scheduledExecutorService).subscribe();
    }
}
