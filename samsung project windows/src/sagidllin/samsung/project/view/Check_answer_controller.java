package sagidllin.samsung.project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sagidllin.samsung.project.MainApp;
import sagidllin.samsung.project.model.Test;

import java.util.ArrayList;

public class Check_answer_controller {
    private ObservableList<String> names=  FXCollections.observableArrayList();


    @FXML
    TextField five;
    @FXML
    TextField four;
    @FXML
    TextField three;
    @FXML
    ListView students;
    public  void initialize(){

        for(ArrayList<Object> student: Test.answers_data){
            String answer="";
            for(Object i:student){
                answer+=i.toString()+" ";
            }
            names.add(answer);
        }
        students.setItems(names);
    }
    @FXML
    public void onCheck(){
        double sr_ball=0;
        names.removeAll();
        Test.five=Integer.parseInt(five.getText());
        Test.four=Integer.parseInt(four.getText());
        Test.three=Integer.parseInt(three.getText());
        for(ArrayList<Object>answer:Test.answers_data){
            int ball=0;
            for(int i=1;i<answer.size();i++){
                if(Integer.parseInt(answer.get(i).toString())== MainApp.questionData.get(i-1).getAnswerinteger()){ball++;}

            };
            System.out.print(ball);
            sr_ball=+ball;
            if(ball<Test.three)answer.add(2);
            else if(ball<Test.four)answer.add(3);
            else if(ball<Test.five)answer.add(4);
            else answer.add(5);}
            sr_ball=Math.round(sr_ball/Test.answers_data.size()*100)/100;
            Test.sr_ball=sr_ball;
            for(ArrayList<Object> student: Test.answers_data){
                String answer1="";
                answer1=student.get(0).toString()+" "+student.get(student.size()-1);
                names.add(answer1);
            }
            students.getSelectionModel().getSelectedItems().removeAll();
            students.setItems(names);



    }

}
