package dev.devous.electron;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Packet {
    private final @NotNull UUID uid;
    private final @NotNull String header;
    private final @NotNull String content;

    public Packet(final @NotNull UUID uid, final @NotNull String header, final @NotNull String content) {
        this.uid = uid;
        this.header = header;
        this.content = content;
    }

    public @NotNull UUID uid() {
        return uid;
    }

    public @NotNull String header() {
        return header;
    }

    public @NotNull String content() {
        return content;
    }

    public Document encode() {
        Document document = new Document();
        document.put("uid", uid.toString());
        document.put("header", header);
        document.put("content", content);
        return document;
    }

    public static @NotNull Packet decode(final @NotNull Document document) {
        return new Packet(UUID.fromString(document.getString("unique_id")),
                document.getString("header"), document.getString("content"));
    }

    @Override
    public @NotNull String toString() {
        return "Packet{" +
                "uid=" + uid +
                ", header='" + header + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
