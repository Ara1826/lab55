package sources.models;

import sources.exceptions.io.WrongArgumentException;

import java.time.LocalDateTime;

import static sources.models.helpers.PersonArgumentChecker.*;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDateTime birthday; //Поле не может быть null
    private Integer weight; //Поле может быть null, Значение поля должно быть больше 0
    private String salary; //Длина строки не должна быть больше 64, Длина строки должна быть не меньше 1, Значение этого поля должно быть уникальным, Поле может быть null

    public Person(String name, LocalDateTime birthday, Integer weight, String salary) throws WrongArgumentException {
        checkArguments(name, birthday, weight, salary);
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws WrongArgumentException {
        checkName(name);
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) throws WrongArgumentException {
        checkBirthday(birthday);
        this.birthday = birthday;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) throws WrongArgumentException {
        checkWeight(weight);
        this.weight = weight;
    }

    public String getsalary() {
        return salary;
    }

    public void setsalary(String salary) throws WrongArgumentException {
        checksalary(salary);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", weight=" + weight +
                ", salary='" + salary + '\'' +
                '}';
    }
}
