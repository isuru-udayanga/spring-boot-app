package com.app.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoInitilizer {

    private static String URI = "mongodb://localhost:27017";

    private MongoClientURI getMongoClientURI() {
        return new MongoClientURI(URI);
    }

    private MongoClient getMongoClient() {
        return new MongoClient(getMongoClientURI());
    }

    private MongoDatabase getMongoDatabase(String database) {
        return getMongoClient().getDatabase(database);
    }

    public MongoCollection getMongoCollection(String database, String collection) {
        return getMongoDatabase(database).getCollection(collection);
    }
}
