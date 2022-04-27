package com.warehousing.aquarium.entity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    private final List<Student> students;
    private final List<Project> projects;
    private int[][] preferences;

    private static enum ReadState {
        STUDENT_MODE, PROJECT_MODE, PREFERENCE_MODE, UNKNOWN;
    };

    public Test() {
        super();
        this.students = new ArrayList<Student>();
        this.projects = new ArrayList<Project>();
    }

    public void addStudent(Student s) {
        this.students.add(s);
    }

    public void addStudent(String s) {
        this.addStudent(Student.createStudent(s));
    }

    public void addProject(Project p) {
        this.projects.add(p);
    }

    public void addProject(String p) {
        this.addProject(Project.createProject(p));
    }

    public void createPreferenceMatrix() {
        this.preferences = new int[this.students.size()][this.projects.size()];
    }

    public void setPreference(Student s, Project p, int preference) {
        this.preferences[this.students.indexOf(s)][this.projects.indexOf(p)] = preference;
    }

    public void setPreference(int row, int column, int preference) {
        this.preferences[row][column] = preference;
    }

    public void setPreferenceRow(int row, String[] prefValues) {
        for (int j = 0; j < prefValues.length; j++) {
            this.preferences[row][j] = Integer.parseInt(prefValues[j]);
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public int[][] getPreferences() {
        return preferences;
    }

    @Override
    public String toString() {
        return "PreferenceData [students=" + students + ", projects=" + projects + ", preferences="
                + Arrays.toString(preferences) + "]";
    }

    static Test readData(String inputFile) {

        Test prefs = new Test();

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {

            ReadState state = ReadState.UNKNOWN;
            int row = 0;

            while (reader.ready()) {
                String line = reader.readLine();
                switch (line.trim()) {
                    case "Students:":
                        state = ReadState.STUDENT_MODE;
                        break;
                    case "Projects:":
                        state = ReadState.PROJECT_MODE;
                        break;
                    case "Preferences:":
                        prefs.createPreferenceMatrix();
                        state = ReadState.PREFERENCE_MODE;
                        break;
                    default:
                        switch (state) {
                            case STUDENT_MODE:
                                prefs.addStudent(line);
                                break;
                            case PROJECT_MODE:
                                prefs.addProject(line);
                                break;
                            case PREFERENCE_MODE:
                                prefs.setPreferenceRow(row, line.split(","));
                                row++;
                                break;
                            default:
                                throw new PreferenceFormatException(line);
                        }
                }

            }

            reader.close();

        } catch (FileNotFoundException e) {
            System.err.println("Error opening preferences file. File does not exist as specified.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading from file.");
            e.printStackTrace();
        } catch (PreferenceFormatException e) {
            System.err.println("Preference file in incorrect format. I can't tell which section I'm in.");
            System.err.println("Line being read: " + e.getCurrentLine());
            e.printStackTrace();
        }

        return prefs;
    }

    public int getPreference(int i, int j) {
        return this.preferences[i][j];
    }

    public int numStudents() {
        return this.students.size();
    }

    public int numProjects() {
        return this.projects.size();
    }

}