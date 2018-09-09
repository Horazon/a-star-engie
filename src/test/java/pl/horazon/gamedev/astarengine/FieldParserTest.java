package pl.horazon.gamedev.astarengine;


import pl.horazon.gamedev.astarengine.load.FieldParser;

import java.io.File;
import java.io.IOException;

class FieldParserTest {

    void parse() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        File dir = new File(classLoader.getResource("fields.txt").getFile());

        new FieldParser(dir).parse();
    }
}