package sagidllin.samsung.project.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sagidllin.samsung.project.model.Test;

import javax.swing.text.LabelView;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Result_controller {
    @FXML
    private BarChart barChart;
    @FXML
    private Label sr_ball;
    @FXML
    private CategoryAxis xAxis;
    private ObservableList<String> names=  FXCollections.observableArrayList();
    private ObservableList monthNames = FXCollections.observableArrayList();
    @FXML
    ListView students;

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл был загружен.
     */
    @FXML
    private void initialize() {
        sr_ball.setText(String.valueOf(Test.sr_ball));
        // Получаем массив с английскими именами месяцев.
        String[] months ={"2","3","4","5"};
        // Преобразуем его в список и добавляем в наш ObservableList месяцев.
        monthNames.addAll(Arrays.asList(months));

        // Назначаем имена месяцев категориями для горизонтальной оси.
        xAxis.setCategories(monthNames);
        for(ArrayList<Object> student: Test.answers_data){
            names.add(student.get(0).toString());
        }
        students.setItems(names);
    }

    /**
     * Задаёт адресатов, о которых будет показана статистика.
     *
     * @param persons
     */
    public void setPersonData(List persons) {
        // Считаем адресатов, имеющих дни рождения в указанном месяце.
        int[] monthCounter = new int[4];
        for (ArrayList<Object> answer : Test.answers_data) {
            int month =Integer.parseInt((String) answer.get(answer.size()-1))- 1;
            monthCounter[month]++;
        }

        XYChart.Series series = new XYChart.Series<>();

        // Создаём объект XYChart.Data для каждого месяца.
        // Добавляем его в серии.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);
    }
}
