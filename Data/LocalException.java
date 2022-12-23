package Data;


public class LocalException extends RuntimeException {

    public LocalException(String message) { // проверка ввода пустой строки
        super("Ошибка в веденных данных: " + message);
    }

}