package sagidllin.samsung.project.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import sagidllin.samsung.project.MainApp;
import sagidllin.samsung.project.model.Question;


public class QuestionOverviewController {
    @FXML
    private TableView<Question> personTable;
    @FXML
    private TableColumn<Question, String> name;
    @FXML
    private TableView<Question> variantsTable;
    @FXML
    private Label fname;
    @FXML
    private Label number_variants;
    @FXML
    private Label time;
    @FXML
    private Label answer;
    @FXML
    private ListView<String>variants;


    // Ссылка на главное приложение.
    private MainApp mainApp;

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public QuestionOverviewController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы вопросов с одним столбцом.
        name.setCellValueFactory(cellData -> cellData.getValue().getName());
        showQuestionDetails(null);
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showQuestionDetails(newValue));

    }
    private void showQuestionDetails(Question question){
        if(question!=null){
            fname.setText(question.getNamestring());
            number_variants.setText(question.getNumber_variantsinteger().toString());
            time.setText(question.getTimeinteger().toString());
            answer.setText(question.getAnswerinteger().toString());
            variants.setItems(question.getVariantsstring());

        }
        else{
            fname.setText("");
            number_variants.setText("");
            time.setText("");
            answer.setText("");
            variants.setItems(null);
        }
    }
    @FXML
    private void handleDeletePerson() {


            int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex!=-1)
            personTable.getItems().remove(selectedIndex);
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleEditQuestion() {
        Question selectedPerson =personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showQuestionEditDialog(selectedPerson);
            if (okClicked) {
                showQuestionDetails(selectedPerson);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    /**
     * Вызывается, когда пользователь кликает по кнопке New...
     * Открывает диалоговое окно с дополнительной информацией нового вопроса.
     */
    @FXML
    private void handleNewPerson() {
       Question tempQuestion = new Question();
        boolean okClicked = mainApp.showQuestionEditDialog(tempQuestion);
        if (okClicked) {
            mainApp.getquestionData().add(tempQuestion);
        }
    }



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
        personTable.setItems(mainApp.getquestionData());
    }
}

