package com.example.ramki.listy.model.generator;

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
        //generateTestFiles(schema, "../../../Pinterest/src/test/java/com/pinterest/api/model/");
    }

    /**
     * Creates a test file for all entities, if the file does not already exist.
     */
    private static void generateTestFiles(Schema schema, String saveDirectory) {
        for (Entity entity : schema.getEntities()) {
            String modelClassName = entity.getClassName();
            String modelTestClassName = entity.getClassNameTest();
            String testFileName = modelTestClassName + ".java";
            String filePath = saveDirectory + testFileName;

            StringBuilder fileContentBuilder = new StringBuilder();
            fileContentBuilder.append("package com.example.ramki.listy.model;\n\n");
            fileContentBuilder.append(
                "import " + entity.getJavaPackage() + "." + modelClassName + ";\n\n");

            fileContentBuilder
                .append("public class " + modelTestClassName + " extends BaseModelTestCase {\n");
            fileContentBuilder.append("    @Override\n");
            fileContentBuilder.append("    public Class getDaoClass() {\n");
            fileContentBuilder.append("        return " + modelClassName + ".class;\n");
            fileContentBuilder.append("    }\n");
            fileContentBuilder.append("\n}\n");

//            try {
//                Path path = Files.write(Paths.get(filePath),
//                    fileContentBuilder.toString().getBytes(), StandardOpenOption.CREATE_NEW);
//            } catch (IOException e) {
//                // ignore files that already exist.
//            }
        }
    }

    private static void makeModels(Schema schema) {
        // Category
        Entity todoEntry = schema.addEntity("TodoEntries");
        todoEntry.addIdProperty().autoincrement();
        todoEntry.addStringProperty("title");
        todoEntry.addStringProperty("notes");
        todoEntry.addBooleanProperty("deleted");
        todoEntry.addDateProperty("created_on");
        todoEntry.addDateProperty("due");
    }
}