package sagidllin.samsung.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import sagidllin.samsung.project.model.Question;
import sagidllin.samsung.project.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;
    private BorderPane rootLayout;
   public static ObservableList<Question> questionData = FXCollections.observableArrayList();


    public MainApp() {

    }
    static Stage testDialog=new Stage();
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showQuestionOverview();
    }


    
    /**
     * устанавливаем root layout.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Даём контроллеру доступ к главному прилодению.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Пытается загрузить последний открытый файл с вопросами.
        File file = getQuestionFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * устанавливает question overview внутри root layout.
     */
    public void showQuestionOverview() {
        try {
            // загружаем question overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/QuestionOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // устанавливаем question overview в нутри root layout.
            rootLayout.setCenter(personOverview);
            QuestionOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Question> getquestionData() {
        return questionData;
    }
    /**
     * Открывает диалоговое окно для изменения деталей указанного вопроса.
     * Если пользователь кликнул OK, то изменения сохраняются в предоставленном
     * объекте вопроса и возвращается значение true.
     *
     * @param question - объект вопроса, который надо изменить
     * @return true, если пользователь кликнул OK, в противном случае false.
     */
    public boolean showQuestionEditDialog(Question question) {
            try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/QuestionEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Question");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём вопрос в контроллер.
            QuestionEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setQuestion(question);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public File getQuestionFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    public void setQuestionFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            primaryStage.setTitle("AddressApp");
        }
    }
    public void  loadPersonDataFromFile(File fileName){
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            org.jdom2.Document jdomDocument = saxBuilder.build(fileName);
            Element root = jdomDocument.getRootElement();
            // получаем список всех элементов Question
            List<Element> questListElements = root.getChildren("Question");
            List<Question> questions=new ArrayList<>();
            for (Element quest : questListElements) {
                Question student = new Question();
                student.setName((quest.getChildText("name")));
                student.setNumber_variants(Integer.parseInt(quest.getChildText("number_variants")));
                student.setAnswer(Integer.parseInt(quest.getChildText("answer")));
                student.setTime(Integer.parseInt(quest.getChildText("time")));
                List<Element> variants=quest.getChildren("variants");
                List<String> stranswer=new ArrayList<>();
                for(Element variant:variants){
                    stranswer.add(variant.getText());
                }
                student.setVariants(stranswer);

                questions.add(student);
            }
            questionData.clear();
            questionData.addAll(questions);
            setQuestionFilePath(fileName);

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + fileName.getPath());

            alert.showAndWait();
        }

    }

    /**
     * Сохраняет текущую информацию об вопросах в указанном файле.
     *
     * @param fileName
     */
    public void savePersonDataToFile( File fileName) {
        try{
        Document doc = new Document();
        List<Question> questions=questionData;
        // создаем корневой элемент с пространством имен
        doc.setRootElement(new Element("Questions"));
        // формируем JDOM документ из объектов Student
        for (Question question : questions) {
            Element studentElement = new Element("Question");
            studentElement.addContent(new Element("name").setText("" + question.getNamestring()));
            studentElement.addContent(new Element("number_variants").setText(question.getNumber_variantsinteger().toString()));
            studentElement.addContent(new Element("answer"
                    ).setText(question.getAnswerinteger().toString()));
            studentElement.addContent(new Element("time").setText(question.getTimeinteger().toString()));
            for(String variant: question.getVariantsstring()){
                studentElement.addContent(new Element("variants").setText(variant));
            }
            doc.getRootElement().addContent(studentElement);
            setQuestionFilePath(fileName);
        }
        // Документ JDOM сформирован и готов к записи в файл
        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
        // сохнаряем в файл
        xmlWriter.output(doc, new FileOutputStream(fileName));}
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + fileName.getPath());

            alert.showAndWait();
        }
    }

    /**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
    public void showtestdialog(){

        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ShowTestDialog.fxml"));
            AnchorPane testDialog = (AnchorPane) loader.load();
            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Prepare to test");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(testDialog);
            dialogStage.setScene(scene);
            // Передаём тест в контроллер.
            Showtestdialogcontroller showtestdialogcontroller = loader.getController();
            showtestdialogcontroller.setMainApp(this);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void showRunTestDialog(){
	    try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RunTestDialog.fxml"));
            AnchorPane runTestDialog = (AnchorPane) loader.load();

            testDialog.setFullScreen(true);
            testDialog.setTitle("Test run");
            testDialog.initModality(Modality.WINDOW_MODAL);
            testDialog.initOwner(primaryStage);
            Scene scene=new Scene(runTestDialog);
            testDialog.setScene(scene);
            //устанавливаем контроллер дляя разметки
            RunTestDialogController controller=loader.getController();
            testDialog.show();
        }catch (IOException e){
	        e.printStackTrace();
        }
    }
    static public void showCheckDialog(){
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Check_answer.fxml"));
            AnchorPane runTestDialog = (AnchorPane) loader.load();
            Stage dialog=new Stage();
            dialog.setTitle("Check");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            Scene scene=new Scene(runTestDialog);
            dialog.setScene(scene);
            //устанавливаем контроллер дляя разметки
            Check_answer_controller controller=loader.getController();
            dialog.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public  static void closeDialog(){
	    testDialog.close();
    }
}