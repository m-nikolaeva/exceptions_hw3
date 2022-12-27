
public class User {
    private String firstName;
    private String lastName;
    private String middleName;
    private String dateOfBirth;
    private long phoneNumber;
    private String sex;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public User createModel(String userString) throws BadDataException {
        userString = userString.replace(" ", "");
        userString = userString.replace("><", " ");
        userString = userString.replace('<', ' ');
        userString = userString.replace('>', ' ');
        userString = userString.trim();
        String[] userArr = userString.split(" ");

        if (userArr.length != 6) {
            throw new BadDataException("Не совпадает количество введенных данных, хранящихся в файле.");
        }

        lastName = userArr[0];
        firstName = userArr[1];
        middleName = userArr[2];
        dateOfBirth = userArr[3];
        try {
            phoneNumber = Long.parseLong(userArr[4]);
        } catch (NumberFormatException ex) {
            throw new BadDataException(ex.getMessage());
        }
        sex = userArr[5];
        return this;
    }

    public User createModel(String[] userArr) throws BadDataException {
        if (userArr.length != 6) {
            throw new BadDataException("Не совпадает количество введенных данных, хранящихся в файле.");
        }

        lastName = userArr[0];
        firstName = userArr[1];
        middleName = userArr[2];
        if (userArr[3].contains("+")) {
            throw new BadDataException("Номер телефона не может начинаться с символа \"+\".");
        } else if (userArr[3].length() > 15) {
            throw new BadDataException("Номер телефона не может содержать более 15 цифр.");
        } else {
            try {
                phoneNumber = Long.parseLong(userArr[3]);
            } catch (NumberFormatException ex) {
                throw new BadDataException(ex.getMessage());
            }
        }
        dateOfBirth = userArr[4];
        sex = userArr[5];
        return this;
    }

    @Override
    public String toString() {
        return "Пользователь:" + " " +
                lastName + " " +
                firstName + " " +
                middleName + ", " +
                dateOfBirth + ", " +
                phoneNumber + ", " +
                sex;
    }

    public String toFileString() {
        return '<' + lastName + '>' +
                '<' + firstName + '>' +
                '<' + middleName + '>' +
                '<' + dateOfBirth + '>' +
                '<' + phoneNumber + '>' +
                '<' + sex + '>';
    }
}