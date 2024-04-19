package br.com.alura.service;

public class EmailSenderService {

    public static void send(String instructorEmail, String studentName, int score, String courseName, String instructor) {
        String subject = "Course Evaluation";
        String body = String.format("The user %s placed as a score %d in the course %s for the instructor %s.", studentName, score, courseName, instructor);

        System.out.printf("Sending email to [%s]:\n\n", instructorEmail);
        System.out.printf("Subject: %s\n", subject);
        System.out.printf("Body:\n%s\n\n", body);
    }
}
