package Data;

import java.io.FileWriter;
import java.io.IOException;

public class WritePeopleInFiles {
    public static void writeLineFile(String dataPeople, String patchDB) {
        String[] currentPeople = dataPeople.split(" ");
        String pathFile = patchDB + currentPeople[0];
        try (FileWriter writer = new FileWriter(pathFile, true)) {
            writer.write(dataPeople + "\n");

        } catch (IOException e) {
            System.out.println("ошибка записи данных: указанный путь не существует. " + e.getClass().getSimpleName());
            e.printStackTrace();
        }
    }
}
