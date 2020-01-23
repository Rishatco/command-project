package sagidllin.samsung.project.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sagidllin.samsung.project.model.Question;

import java.util.LinkedList;


public class QuestionEditDialogController {
    @FXML
    private TextField quest;
    @FXML
    private TextField number_variants;
    @FXML
    private TextField time;
    @FXML
    private TextField answer;
    @FXML
    private TextField answer1;
    @FXML
    private TextField answer2;
    @FXML
    private TextField answer3;
    @FXML
    private TextField answer4;
    @FXML
    private TextField answer5;
    @FXML
    private TextField answer6;
    @FXML
    private TextField answer7;
    @FXML
    private TextField answer8;
    @FXML
    private TextField answer9;
    @FXML
    private TextField answer10;


    private Stage dialogStage;
    private Question question;

    private boolean okClicked = false;
    @FXML
    private void initialize() {}


        //иницилизируем все данные вопроса в форме
        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }
        public void setQuestion(Question question) {
        LinkedList<String>variants=new LinkedList<>();
        variants.addAll(question.getVariantsstring());
            this.question = question;
            quest.setText(question.getNamestring());
            number_variants.setText(question.getNumber_variantsinteger().toString());
            time.setText(question.getTimeinteger().toString());
            answer.setText(Integer.toString(question.getAnswerinteger()));
            try{
            answer1.setText(variants.get(0));
            answer2.setText(variants.get(1));
            answer3.setText(variants.get(2));
            answer4.setText(variants.get(3));
            answer5.setText(variants.get(4));
            answer6.setText(variants.get(5));
            answer7.setText(variants.get(6));
            answer8.setText(variants.get(7));
            answer9.setText(variants.get(8));
            answer10.setText(variants.get(9));}
            catch (IndexOutOfBoundsException e){

            }




        }
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            //сохраняет данные вопроса
            question.setName(quest.getText());
            question.setNumber_variants( Integer.parseInt(number_variants.getText()));}
            question.setTime(Integer.parseInt(time.getText()));
            question.setAnswer(Integer.parseInt(answer.getText()));

             LinkedList<String>variant=new LinkedList<>();
            if(answer1.getText().length()>0){
                variant.add(answer1.getText());
                if(answer2.getText().length()>0){
                    variant.add(answer2.getText());
                    if(answer3.getText().length()>0){
                        variant.add(answer3.getText());
                        if(answer4.getText().length()>0){
                            variant.add(answer4.getText());
                            if(answer5.getText().length()>0){
                                variant.add(answer5.getText());
                                if(answer6.getText().length()>0){
                                    variant.add(answer6.getText());
                                    if(answer7.getText().length()>0){
                                        variant.add(answer7.getText());
                                        if(answer8.getText().length()>0){
                                            variant.add(answer8.getText());
                                            if(answer9.getText().length()>0){
                                                variant.add(answer9.getText());
                                                if(answer10.getText().length()>0){
                                                    variant.add(answer10.getText());
                                                } } } } } } } } } }
            question.setVariants(variant);
            okClicked = true;
            dialogStage.close();
        }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }



    private boolean isInputValid() {
        //проверка на соответствие данных к типам и вывод предупреждения
        String errorMessage = "";

        if (quest.getText() == null || quest.getText().length() == 0) {
            errorMessage += "No valid question!\n";
        }
        if (number_variants.getText() == null || number_variants.getText().length() == 0) {
            errorMessage += "No valid number of variants!\n";
        }else {
            // пытаемся преобразовать количество вариантов в int.
            try {
                Integer.parseInt(number_variants.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid number of variants (must be an integer)!\n";
            }
        }
        if (time.getText() == null || time.getText().length() == 0) {
            errorMessage += "No valid time!\n";
        }else {
            // пытаемся преобразовать время в int.
            try {
                Integer.parseInt(time.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid time (must be an integer)!\n";
            }
        }

        if (answer.getText() == null || answer.getText().length() == 0) {
            errorMessage += "No valid answer!\n";
        }else {
            // пытаемся преобразовать ответ в int.
            try {
                Integer.parseInt(answer.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid answer (must be an integer)!\n";
            }
        }




        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
