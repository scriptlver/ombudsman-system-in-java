import java.util.Date;


abstract class User {
    private String name;
    private String phone;
    private Date birthDate;

    public User(String name, String phone, Date birthDate) {
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }
}

class Student extends User {
    private double average;

    public Student(String name, String phone, Date birthDate, double average) {
        super(name, phone, birthDate);
        this.average = average;
    }

    public double getAverage() {
        return average;
    }
}

class Teacher extends User {
    private int workLoad;

    public Teacher(String name, String phone, Date birthDate, int workLoad) {
        super(name, phone, birthDate);
        this.workLoad = workLoad;
    }

    public int getWorkLoad() {
        return workLoad;
    }
}

class Collaborator extends User {
    private String sector;

    public Collaborator(String name, String phone, Date birthDate, String sector) {
        super(name, phone, birthDate);
        this.sector = sector;
    }

    public String getSector() {
        return sector;
    }
}


