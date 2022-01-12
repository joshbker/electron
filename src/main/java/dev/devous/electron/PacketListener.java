package dev.devous.electron;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PacketListener {
    private final @NotNull PacketQueue packetQueue;
    private final @NotNull PacketHandler packetHandler;

    PacketListener(@NotNull PacketQueue packetQueue, @NotNull PacketHandler packetHandler) {
        this.packetQueue = packetQueue;
        this.packetHandler = packetHandler;
    }

    public void subscribe() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            for (Document document : packetQueue.collection().find()) {
                packetHandler.handle(Packet.decode(document));
                packetQueue.collection().deleteOne(document);
            }
            packetQueue.flush();
        }, 0L, 50L, TimeUnit.MILLISECONDS);
    }
}
