package dev.devous.electron;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class PacketQueue {
    private final MongoCollection<Document> collection;
    private final Queue<Packet> packetQueue = new LinkedList<>();

    PacketQueue(final @NotNull MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public boolean queue(final @NotNull Packet... packets) {
        return this.packetQueue.addAll(List.of(packets));
    }

    public boolean flush() {
        Packet packet = packetQueue.peek();

        if (packet == null) {
            return false;
        }

        return collection.replaceOne(Filters.eq("uid", packet.uid().toString()), packet.encode(),
                new ReplaceOptions().upsert(true)).wasAcknowledged();
    }

    public @NotNull MongoCollection<Document> collection() {
        return collection;
    }

    public @NotNull Queue<Packet> packetQueue() {
        return packetQueue;
    }
}
