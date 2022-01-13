package dev.devous.electron;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.Queue;

public final class PacketQueue {
    private final MongoCollection<Document> collection;
    private final Queue<Packet> packetQueue = new ConcurrentLinkedQueue<>();

    PacketQueue(final @NotNull MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public boolean queue(final @NotNull Packet... packets) {
        return this.packetQueue.addAll(List.of(packets));
    }

    public boolean flush() {
        Packet packet = packetQueue.poll();

        if (packet == null) {
            return;
        }

        collection.replaceOne(Filters.eq("uid", packet.uid().toString()), packet.encode(),
                new ReplaceOptions().upsert(true));
    }

    public @NotNull MongoCollection<Document> collection() {
        return collection;
    }

    public @NotNull Queue<Packet> packetQueue() {
        return packetQueue;
    }
}
