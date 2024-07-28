public class Student {
    private String name;
    private int id;
    private String grade;

    public Student(String name, int id, String grade) {
        setName(name);
        setId(id);
        setGrade(grade);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
