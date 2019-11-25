package main.src.controllers;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.MainClass;
import main.src.controllers.Grades.GradeFive;
import main.src.controllers.Grades.GradeParent;
import main.src.controllers.Grades.GradeTwo;
import main.src.models.AssignmentModel;
import main.src.models.QuestionAnsModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;


/**
 * @author Karandeep Singh Grewal
 */


public class WorkspaceController implements Initializable {
    public static AssignmentModel assignmentModelw = new AssignmentModel();
    public static QuestionAnsModel questionAnsModelw = new QuestionAnsModel();
    List<String> questionidsw;
    List<String> questionsw = new ArrayList<>();

    @FXML
    private Pane sandBox;
    @FXML
    public StackPane commonPane;
    @FXML
    private Label homeButton;
    @FXML
    private Label submitButton;
    @FXML
    private VBox sidePane;

    @FXML
    private Label resetButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String assignment = "Assignment 90";

        HashMap<String, String> hashmap = displayQuestions(assignment);
        System.out.println("Printing Q and A pair");
        for (String s : hashmap.keySet())
            System.out.println("question:" + s + "ans:" + hashmap.get(s));
        int studentGrade = 2;
        if (LoginController.studentModel.getGrade() != null) {
            studentGrade = Integer.parseInt(LoginController.studentModel.getGrade());
        }

        GradeParent grade = null;
        switch (studentGrade) {
            case 2:
                grade = new GradeTwo();
                break;
            case 5:
                grade = new GradeFive();
                break;
            default:
                System.out.println("Unknown Grade");
                break;

        }

        grade.produceWorkspace(sandBox, sidePane, commonPane);

        homeButton.setOnMouseClicked(mouseEvent -> {
            new MainClass().openHomePageWindow();
            MainClass.workspaceStage.close();
        });

        submitButton.setOnMouseClicked(mouseEvent -> {
            if (commonPane.getChildren().get(0) instanceof StackPane) {
                StackPane questionPane = null;
                if (commonPane.getChildren().get(0) instanceof StackPane) {
                    questionPane = (StackPane) commonPane.getChildren().get(0);
                }
                System.out.println(questionPane.getChildren());

            } else {
                System.out.println("Switch to Result Pane");
            }
        });

    }

    HashMap<String, String> displayQuestions(String Assignment) {
        HashMap<String, String> map = new HashMap<>();
        CountDownLatch done = new CountDownLatch(1);
        final String[] message = {null};
        Firebase firebase = new Firebase("https://ser515-team4.firebaseio.com/");
        firebase.child("Assignment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    assignmentModelw = data.getValue(AssignmentModel.class);
                    assignmentModelw.setId(data.getKey());
                    if (assignmentModelw.getAssignmentName().equals(Assignment)) {
                        questionidsw = assignmentModelw.getQuestionId();
                    }
                    //System.out.println("Size:" + assignmentlist.size());
                }
                done.countDown();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < questionidsw.size(); i++)
            System.out.println("===" + questionidsw.get(i));

        CountDownLatch done2 = new CountDownLatch(1);
        final String[] message2 = {null};
        Firebase firebase2 = new Firebase("https://ser515-team4.firebaseio.com/");
        firebase2.child("Grade").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    questionAnsModelw = data.getValue(QuestionAnsModel.class);
                    questionAnsModelw.setId(data.getKey());
                    if (questionidsw.contains(questionAnsModelw.getId())) {
                        questionsw.add(questionAnsModelw.getQuestion());
                        map.put(questionAnsModelw.getQuestion(), questionAnsModelw.getAns());
                    }
                    //System.out.println("Size:" + assignmentlist.size());
                }
                done2.countDown();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        try {
            done2.await(); //it will wait till the response is received from firebase.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < questionsw.size(); i++)
            System.out.println(questionsw.get(i));
        System.out.println("****" + questionsw);
        return map;

    }

}