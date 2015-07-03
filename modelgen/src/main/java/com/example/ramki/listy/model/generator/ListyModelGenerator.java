package com.example.ramki.listy.model.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


/**
 * GreenDAO Model Generator class
 * NOTE: updte GENERATED_MODEL_PATH based on where your run this class.
 */
public class ListyModelGenerator {

    private static java.lang.String GENERATED_MODEL_PATH = "app/src/main/java/";

    public static void main(String[] args) throws Exception {
        // Need to increment number to not crash DB.
        Schema schema = new Schema(1, "com.example.ramki.listy.model");
        schema.enableKeepSectionsByDefault();
        makeModels(schema);
        new DaoGenerator().generateAll(schema, GENERATED_MODEL_PATH);
    }

    private static void makeModels(Schema schema) {
        // Category
        Entity todoEntry = schema.addEntity("TodoEntry");
        todoEntry.setSuperclass("TodoEntryComparable");
        todoEntry.addIdProperty().autoincrement();
        todoEntry.addStringProperty("title").notNull();
        todoEntry.addStringProperty("notes");
        todoEntry.addBooleanProperty("deleted").notNull();
        todoEntry.addDateProperty("created_on").notNull();
        todoEntry.addDateProperty("due");
    }
}