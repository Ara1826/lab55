package sources.models.helpers;

import sources.exceptions.io.WrongArgumentException;

import java.time.LocalDateTime;

public class PersonArgumentChecker extends ArgumentChecker {
    public static void checkArguments(String name, LocalDateTime birthday, Integer weight, String salary) throws WrongArgumentException {
        PersonArgumentChecker.checkName(name);
        PersonArgumentChecker.checkBirthday(birthday);
        PersonArgumentChecker.checkWeight(weight);
        PersonArgumentChecker.checksalary(salary);
    }

    public static void checkName(String name) throws WrongArgumentException {
        checkNull(name, "name");
        checkArgument(!name.equals(""), "argument can't be null");
    }

    public static void checkBirthday(LocalDateTime birthday) throws WrongArgumentException {
        checkNull(birthday, "birthday");
    }

    public static void checkWeight(Integer weight) throws WrongArgumentException {
        checkNull(weight, "weight");
        checkArgument(weight > 0, "argument can't be <= 0");
    }

    public static void checksalary(String salary) throws WrongArgumentException {
        if (salary != null) {
            checkArgument(1 <= salary.length(), "argument can't be < 1");
            checkArgument(salary.length() <= 64, "argument can't be > 64");
        }
    }
}
