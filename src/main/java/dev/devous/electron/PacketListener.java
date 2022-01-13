package dev.devous.electron;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PacketListener {
    private final @NotNull PacketQueue packetQueue;
    private final @NotNull PacketHandler packetHandler;
    private final @NotNull ScheduledExecutorService scheduledExecutorService;

    PacketListener(final @NotNull PacketQueue packetQueue, final @NotNull PacketHandler packetHandler,
                   final @NotNull ScheduledExecutorService scheduledExecutorService) {
        this.packetQueue = packetQueue;
        this.packetHandler = packetHandler;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public void subscribe() {
       scheduledExecutorService.scheduleAtFixedRate(() -> {
            for (Document document : packetQueue.collection().find()) {
                packetHandler.handle(Packet.decode(document));
                packetQueue.collection().deleteOne(document);
            }
            packetQueue.flush();
        }, 0L, 50L, TimeUnit.MILLISECONDS);
    }
}
