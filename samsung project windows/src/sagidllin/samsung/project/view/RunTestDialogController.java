package sagidllin.samsung.project.view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import sagidllin.samsung.project.MainApp;
import sagidllin.samsung.project.model.MultiThreadServer;
import sagidllin.samsung.project.model.Question;
import sagidllin.samsung.project.model.Test;

import java.util.Timer;
import java.util.TimerTask;

public class RunTestDialogController {
    @FXML
    Label quest;
    @FXML
    Label answer1;
    @FXML
    Label answer2;
    @FXML
    Label answer3;
    @FXML
    Label answer4;
    @FXML
    Label answer5;
    @FXML
    Label answer6;
    @FXML
    Label answer7;
    @FXML
    Label answer8;
    @FXML
    Label answer9;
    @FXML
    Label answer10;
    @FXML
    ProgressBar progressBar;
    @FXML
    Button onStart;
    @FXML
    Label number_question;
    private int cursor = 0;
    private ObservableList<Question> questions = MainApp.questionData;
    java.util.Timer timer = new java.util.Timer();

    public void RunTestDialogController() {

    }

    public void initialize() {
        answer1.setText("");
        answer2.setText("");
        answer3.setText("");
        answer4.setText("");
        answer5.setText("");
        answer6.setText("");
        answer7.setText("");
        answer8.setText("");
        answer9.setText("");
        answer10.setText("");
        quest.setText("");
        number_question.setText("");
    }


    private void start(int x) {
        Thread thread = new Thread();
        progressBar.setProgress(x);
        try {
            while (true) {
                thread.sleep(1000);
                if (x == 0) {
                    break;
                }
                x--;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void runTest() {
        onStart.setDisable(true);
        onRun();
        Test.flag_to_start=true;

    }

    int y = 0;

    public void onRun() {

        Question question = questions.get(cursor);
        int x = question.getVariantsstring().size();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                quest.setText(question.getNamestring().toString());
                for (int i = 0; i < x; i++) {
                    switch (i) {
                        case 0:
                            answer1.setText("1)"+question.getVariantsstring().get(i));
                            break;
                        case 1:
                            answer2.setText("2)"+question.getVariantsstring().get(i));
                            break;
                        case 2:
                            answer3.setText("3)"+question.getVariantsstring().get(i));
                            break;
                        case 3:
                            answer4.setText("4)"+question.getVariantsstring().get(i));
                            break;
                        case 4:
                            answer5.setText("5)"+question.getVariantsstring().get(i));
                            break;
                        case 5:
                            answer6.setText("6)"+question.getVariantsstring().get(i));
                            break;
                        case 6:
                            answer7.setText("7)"+question.getVariantsstring().get(i));
                            break;
                        case 7:
                            answer8.setText("8)"+question.getVariantsstring().get(i));
                            break;
                        case 8:
                            answer9.setText("9)"+question.getVariantsstring().get(i));
                            break;
                        case 9:
                            answer10.setText("10)"+question.getVariantsstring().get(i));
                            break;
                    }
                }
                for (int i = x; i < 10; i++) {
                    switch (i) {
                        case 0:
                            answer1.setText("");
                            break;
                        case 1:
                            answer2.setText("");
                            break;
                        case 2:
                            answer3.setText("");
                            break;
                        case 3:
                            answer4.setText("");
                            break;
                        case 4:
                            answer5.setText("");
                            break;
                        case 5:
                            answer6.setText("");
                            break;
                        case 6:
                            answer7.setText("");
                            break;
                        case 7:
                            answer8.setText("");
                            break;
                        case 8:
                            answer9.setText("");
                            break;
                        case 9:
                            answer10.setText("");
                            break;

                    }
                }
                number_question.setText("Вопрос " + y + " из " + questions.size());
            }

        });
        i[0] = 1;
        step[0] = question.getTimeinteger();
        y++;
        cursor++;
        timer();

    }

    public void timer() {
        timer.schedule(new Mytask(), 10);

    }

    class Mytask extends TimerTask {
        public void run() {
            progressBar.setProgress(i[0]);
            i[0] -= 1 / step[0] / 100;
            if (cursor == questions.size() && i[0] < 0) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MainApp.closeDialog();
                        try{
                        Thread.sleep(5000);}catch (InterruptedException e){e.printStackTrace();}
                        Test test =new  Test();
                        MainApp.showCheckDialog();

                    }
                });
            }
            else {
                if (i[0] < 0) {
                    if(cursor==questions.size()-3)Test.flag_to_read=true;
                    onRun();
                } else timer();
            }

        }
    }
    final double[] i = {1};
    final double step[] = {0};
}
