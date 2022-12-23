package Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import static Data.checkInputData.emptyInputData;
import static Data.checkInputData.fullInputFIO;

public class CreateStringRecord {
    public static String createStringRecord(String dataPeople) throws LocalException { // соберем строку для записи в нужном формате

        ArrayList<String> currentPeople = new ArrayList<>(Arrays.asList(dataPeople.split(" ")));
        while (currentPeople.contains("")) {
            currentPeople.remove("");
        }

        String[] fio = new String[3];
        int counter = 0;
        DateFormat pattern = new SimpleDateFormat("dd.MM.yyyy");
        String dataB = null;
        String numberPhone = null;
        String gender = null;

        for (String item : currentPeople) {

            if ((item.equals("f") || item.equals("m"))) { // это пол?
                if (gender != null) {
                    throw new LocalException("пол указан 2 раза");
                }
                gender = item;

            } else if (item.matches("([+]*)\\d+")) { // это телефон (может быть как сотовый так и внутренний?
                if (numberPhone != null) {
                    throw new LocalException("телефон указан 2 раза");
                }
                numberPhone = item;
            } else if (item.matches("[a-zA-Zа-яА-Я]+")  // ФИО
                    || (item.matches("([a-zA-Zа-яА-Я]+)(-)([a-zA-Zа-яА-Я]+)"))) {
                if (counter < 3) {
                    fio[counter] = item;
                    counter++;
                }
            } else {
                try {
                    pattern.parse(item);
                    LocalDate.parse(item, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    dataB = item;
                } catch (DateTimeParseException e) {
                    throw new LocalException("некорректная дата: " + item);
                } catch (ParseException e) {
                    throw new LocalException("неизвестный формат данных: " + item);
                }

            }
        }

        emptyInputData(dataB, "день рождения в формате (dd.mm.yyyy)"); //
        emptyInputData(numberPhone, "Телефон (целое беззнаковое число)");
        emptyInputData(gender, "пол (f/m)");
        fullInputFIO(fio, counter); // проверка полноты ФИО

        return String.join(" ", fio[0], fio[1], fio[2], dataB, numberPhone, gender);
    }
}
