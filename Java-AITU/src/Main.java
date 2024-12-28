import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Емае, базовый класс "Адам" (человек)
class Person {
    private String name; // Аты ким, айтшы?
    private String surname; // Фамилиясы калай?
    private int age; // Жасы каншага келди?
    private boolean gender; // Еркек па, кыз ба? (true - еркек, false - кыз)

    public Person(String name, String surname, int age, boolean gender) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name; // Атын кайтарып беремиз
    }

    public String getSurname() {
        return surname; // Фамилиясын қайтар
    }

    @Override
    public String toString() {
        String genderStr = gender ? "Еркек" : "Кыз"; // Кандай екенин айтамыз
        return "Салем, мен " + name + " " + surname + ", " + age + " жастагы " + genderStr + "пын.";
        // Озин таныстыру метод)
    }
}

// Емае, енди "Студент" классы - "Адамнан" туындайды
class Student extends Person {
    private static int studentIdCounter = 1; // Студенттин номерлери
    private int studentID; // Студенттин уникальный номері
    private List<Integer> grades; // Багалары

    public Student(String name, String surname, int age, boolean gender) {
        super(name, surname, age, gender); // Ата-анасынан алган инфа
        this.studentID = studentIdCounter++; // Номерди беремиз
        this.grades = new ArrayList<>(); // Багаларын жинаймыз
    }

    public int getStudentID() {
        return studentID; // Студенттін номерін береміз
    }

    public void addGrade(int grade) {
        this.grades.add(grade); // Бага косамыз, а то сессия)
    }

    public double calculateGPA() {
        if (grades.isEmpty()) {
            return 0.0; // Если бага жок, онда 0 балл.
        }
        int sum = 0; // Барлык баганы косамыз
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size(); // Средний балл табамыз
    }

    @Override
    public String toString() {
        return super.toString() + " Мен " + studentID + " номерлі студентпін."; // Студент болып турады
    }
}

// Емае, енді "Мугалим" классы - "Адамнан" туындайды
class Teacher extends Person {
    private String subject; // Не окитатынын айтамыз
    private int yearsOfExperience; // Кашаннан бери жумыс жасаганын айтамыз
    private int salary; // Зарплатасы канша екен?

    public Teacher(String name, String surname, int age, boolean gender, String subject, int yearsOfExperience, int salary) {
        super(name, surname, age, gender); // Ата-анадан алган инфа
        this.subject = subject;
        this.yearsOfExperience = yearsOfExperience;
        this.salary = salary; // Зарплатаны береміз
    }

    public int getYearsOfExperience() {
        return yearsOfExperience; // Опытты береміз
    }

    public int getSalary() {
        return salary; // Зарплатаны кайтарып беремиз
    }

    public void giveRaise(double percentage) {
        this.salary += (this.salary * percentage / 100); // Зарплатаны котеремиз
    }

    @Override
    public String toString() {
        return super.toString() + " Мен " + subject + " панінен сабак беремін."; // Окытушы болып турады
    }
}

// Емае, мектеп классы, барлык Адамдар сонда журеди)
class School {
    private List<Person> members; // Барлык "адамдар" (студент, мугалим)

    public School() {
        this.members = new ArrayList<>(); // Адамдарды жинайтын место
    }

    public List<Person> getMembers() {
        return members; // Адамдарды береміз
    }

    public void addMember(Person member) {
        this.members.add(member); // Адам косамыз
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Строка жасаймыз
        for (Person member : members) {
            sb.append(member.toString()).append("\n"); // Барлык "адамдарды" косамыз
        }
        return sb.toString(); // Мектептін тізімін шыгарамыз
    }
}

// Главный класс, где барлык шоу начинается
public class Main {
    public static void main(String[] args) {
        School school = new School(); // Мектеп ашамыз
        String studentFilePath = "students.txt"; // Студенттер туралы файл
        String teacherFilePath = "teachers.txt"; // Мугалимдер туралы файл

        // Студенттерді файлдан окып, тізімге қосамыз
        try (Scanner scanner = new Scanner(new File(studentFilePath))) {
            while (scanner.hasNextLine()) {
                Student student = createStudentFromLine(scanner.nextLine()); // Студент жасаймыз
                if (student != null) {
                    school.addMember(student); // Мектепке косамыз
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Емае, students.txt табылмады!"); // Не болды, табылмады ма?
            return;
        }

        // Мугалимдерді файлдан окып, тізімге қосамыз
        try (Scanner scanner = new Scanner(new File(teacherFilePath))) {
            while (scanner.hasNextLine()) {
                Teacher teacher = createTeacherFromLine(scanner.nextLine()); // Мугалим жасаймыз
                if (teacher != null) {
                    school.addMember(teacher); // Мектепке косамыз
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Емае, teachers.txt табылмады!"); // Не болды, табылмады ма?
            return;
        }

        System.out.println("Мектептегі Адамдар:"); // Мектептегі Адамдарды шыгарамыз
        System.out.println(school); // Мектептін тізімін шыгарамыз

        for (Person member : school.getMembers()) {
            if (member instanceof Student) { // Если это студент
                Student student = (Student) member; // студент казир осы
                System.out.println(student.getName() + " " + student.getSurname() + " GPA: " + String.format("%.2f", student.calculateGPA())); // Студентін GPA-сын шыгарамыз
            } else if (member instanceof Teacher) { // Если это учитель
                Teacher teacher = (Teacher) member; // Мугалим казир осы
                if (teacher.getYearsOfExperience() > 10) { // Егер тәжірибесі 10 жылдан артық болса
                    teacher.giveRaise(10); // Зарплатасын 10% көтереміз
                    System.out.println("Зарплатасы " + teacher.getName() + " " + teacher.getSurname() + " көтерілді: " + teacher.getSalary()); // Зарплатасын шыгарамыз
                }
            }
        }
    }

    // Метод студент жасайтын
    private static Student createStudentFromLine(String line) {
        try {
            String[] parts = line.split(" "); // Боликтерге болип беремиз
            String name = parts[0]; // Аты
            String surname = parts[1]; // Фамилиясы
            int age = Integer.parseInt(parts[2]); // Жасы
            boolean gender = parts[3].equals("Male"); // Еркек па, кыз ба

            Student student = new Student(name, surname, age, gender); // Студент жасадык

            for (int i = 4; i < parts.length; i++) { // Багаларын жинаймыз
                student.addGrade(Integer.parseInt(parts[i]));
            }
            return student; // Студентті қайтарып береміз
        } catch (Exception e){
            System.err.println("Емае, студенттін строкасын дұрыс оқый алмадым: " + line);
            return null;
        }
    }

    // Метод мугалим жасайтын
    private static Teacher createTeacherFromLine(String line) {
        try {
            String[] parts = line.split(" "); // Боликтерге болип беремиз
            String name = parts[0]; // Аты
            String surname = parts[1]; // Фамилиясы
            int age = Integer.parseInt(parts[2]); // Жасы
            boolean gender = parts[3].equals("Male"); // Еркек па, кыз ба
            String subject = parts[4]; // Сабак беретін пәні
            int yearsOfExperience = Integer.parseInt(parts[5]); // Тәжірибесі
            int salary = Integer.parseInt(parts[6]); // Зарплатасы

            return new Teacher(name, surname, age, gender, subject, yearsOfExperience, salary); // Мугалимді кайтарамыз
        } catch (Exception e){
            System.err.println("Емае, мугалимнін строкасын дұрыс оқый алмадым: " + line);
            return null;
        }
    }
}