package sagidllin.samsung.project.view;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import sagidllin.samsung.project.MainApp;
import sagidllin.samsung.project.model.MultiThreadServer;
import sagidllin.samsung.project.model.Question;

import javax.swing.event.ChangeListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static sagidllin.samsung.project.model.MultiThreadServer.*;

public class Showtestdialogcontroller {
    @FXML
    Label time;
    @FXML
     Label ip;
    @FXML
    Label number;
    @FXML
    Label participants;
    @FXML
    Button reboot;
    private MainApp mainApp;
    public  static IntegerProperty numbers=new SimpleIntegerProperty(0);

    public  Showtestdialogcontroller(){}
    //устанавлиаем параметры окна
    @FXML
    public void initialize(){
        numbers.addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setNumbersToParticipants();
            }
        });
        Mythread mythread=new Mythread();
        mythread.start();
        time.setText(String.valueOf(countTime()));
        number.setText(String.valueOf(MainApp.questionData.size()));

        getIpAddress();
        final ImageView imageView = new ImageView(
                new Image(getClass().getResourceAsStream("unnamed.png"))
        );
        reboot.setText("");
        reboot.setGraphic(imageView);
        reboot.setPrefSize(79,67);
        participants.setText(String.valueOf(numbers.get()));

    }

    public void setNumbersToParticipants(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                participants.setText(String.valueOf(numbers.get()));
            }
        });


    }
    //подсчитывает суммарное время затраченное на тест
        public double countTime(){
        double time = 0;

        for(Question question:  MainApp.questionData){
            time+=question.getTimeinteger();
        }
        time/=60;
        return Math.round(time);
        }



     void   getIpAddress(){
        InetAddress localIP;
        try{
            localIP= InetAddress.getLocalHost();
            String address=(String) localIP.getHostAddress();
            ip.setText(address.substring(address.indexOf('/')+1));
        }catch (UnknownHostException e){
            e.printStackTrace();
            ip.setText("Незможно узнать IP адрес попробуйте обновить/ ");

        }

    }


    @FXML
    private void reload(){
        getIpAddress();
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp=mainApp;
    }
    @FXML
        private void startRunTestDiaolg(){
       mainApp.showRunTestDialog();

    }
    class Mythread extends Thread{
        @Override
        public void run(){
            MultiThreadServer.main(new String[]{});
        }
    }
}
