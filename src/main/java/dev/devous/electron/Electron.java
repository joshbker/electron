package dev.devous.electron;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

public final class Electron {
    public Electron(final @NotNull MongoCollection<Document> collection, final @NotNull PacketHandler packetHandler) {
        PacketQueue packetQueue = new PacketQueue(collection);
        new PacketListener(packetQueue, packetHandler).subscribe();
    }
}
