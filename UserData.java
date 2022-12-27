import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserData {
    public void run() {
        boolean run = true;
        String request;
        String userString;
        var scanner = new Scanner(System.in);
        while (run) {
            System.out.println();
            System.out.print("""
                    Выберите пункт меню:
                    Записать данные пользователя в файл\tвведите 1
                    Вывести данные пользователя на экран\tвведите 2
                    Завершить работу\t\t\tвведите 0
                    Ввод>>>\s""");

            String userChoice = scanner.nextLine();
            switch (userChoice.toUpperCase()) {
                case "1" -> {
                    request = """
                            Введите данные в произвольном порядке, разделенные пробелом:
                            Фамилия Имя Отчество
                            Дата рождения\tdd.mm.yyyy
                            Номер телефона\t71112223344
                            Пол\t\tf или m\s
                            """;
                    userString = readUserData(scanner, request);
                    try {
                        User user = createModelFromUserInput(userString);
                        writeToFile(user);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    pressEnter(scanner);
                }
                case "2" -> {
                    request = "Введите фамилию пользователя для поиска\n";
                    userString = readUserData(scanner, request);
                    List<User> users = new ArrayList<>();
                    try {
                        users = readFromFile(userString);
                    } catch (BadDataException ex) {
                        System.err.println(ex.getMessage());
                    }
                    printUserData(users);
                    pressEnter(scanner);
                }
                case "0" -> run = false;
                default -> {
                }
            }
        }
    }

    private String readUserData(Scanner scanner, String request) {
        System.out.print(request + "Пользователь по фамилии >>> ");
        return scanner.nextLine();
    }

    private User createModelFromUserInput(String userLine) throws BadDataException {
        userLine = userLine.trim();
        userLine = userLine.replace("\\s+", " ");
        String[] userLineArr = userLine.split(" ");
        String[] lineArr = new String[6];

        if (userLineArr.length != 6) {
            throw new BadDataException("Некорректный ввод данных.");
        } else {
            for (String str : userLineArr) {
                str = str.trim();

                if (str.contains(".")) {
                    str = str.replace('.', '-');
                }

                Pattern pattern = Pattern.compile("^(\\d{2}-\\d{2}-\\d{4})$");
                Pattern pattern1 = Pattern.compile("^(\\d+)$");
                Matcher matcher = pattern.matcher(str);
                Matcher matcher1 = pattern1.matcher(str);
                if ((str.equals("f") || str.equals("m")) && lineArr[5] == null) {
                    lineArr[5] = str;
                } else if (matcher.find() && lineArr[4] == null) {
                    str = str.replace('-', '.');
                    lineArr[4] = str;
                } else if (matcher1.find() && lineArr[3] == null) {
                    lineArr[3] = str;
                } else if (lineArr[0] == null) {
                    lineArr[0] = str;
                } else if (lineArr[1] == null) {
                    lineArr[1] = str;
                } else if (lineArr[2] == null) {
                    lineArr[2] = str;
                }
            }
        }

        for (String str : lineArr) {
            if (str == null) {
                throw new BadDataException("Некорректно введены данные.");
            }
        }

        User user = new User();

        return user.createModel(lineArr);
    }

    private void printUserData(List<User> users) {
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    private void pressEnter(Scanner scanner) {
        System.out.print("Для продолжения нажмите клавишу Enter.");
        scanner.nextLine();
    }

    private void writeToFile(User user) throws InOutException {
        String fileName = user.getLastName();
        File file = new File(fileName);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(user.toFileString() + "\n");
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            throw new InOutException(ex.getMessage());
        }
    }

    private List<User> readFromFile(String fileName) throws BadDataException {

        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                User user = new User();
                users.add(user.createModel(line));
                line = reader.readLine();
            }
        } catch (IOException | BadDataException ex) {
            throw new BadDataException(ex.getMessage());
        }
        return users;
    }

}