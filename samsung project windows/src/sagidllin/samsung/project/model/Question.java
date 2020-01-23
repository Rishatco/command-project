package sagidllin.samsung.project.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;


public class Question {
    private final StringProperty name;
    private final IntegerProperty number_variants;
    private  final IntegerProperty time;
    private  final IntegerProperty answer;
    private ObservableList<String> variants= FXCollections.observableArrayList();



    public Question(String name, int number_variants, int time,int answer,  String[] variants){
        this.name= new SimpleStringProperty(name);
        this.number_variants=new SimpleIntegerProperty(number_variants);
        this.answer= new SimpleIntegerProperty(answer);
        this.time=new SimpleIntegerProperty(time);
        this.variants.addAll(variants);
    }
    public  Question(){
        this(null,0,0,0,new String[0]);
    }
    public Question(String name, int number_variants){
        this.name= new SimpleStringProperty(name);
        this.number_variants=new SimpleIntegerProperty(number_variants);
        this.answer= new SimpleIntegerProperty(5);
        this.time=new SimpleIntegerProperty(40);
        this.variants.addAll("Asq","Asdw");
    }
    public StringProperty getName() {
        return name;
    }
    public String getNamestring() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public IntegerProperty getNumber_variants() {
        return number_variants;
    }
    public Integer getNumber_variantsinteger() {
        return number_variants.get();
    }

    public void setNumber_variants(int number_variants) {
        this.number_variants.set(number_variants);
    }

    public IntegerProperty getTime() {
        return time;
    }
    public Integer getTimeinteger() {
        return time.get();
    }

    public IntegerProperty getAnswer() {
        return answer;
    }
    public Integer getAnswerinteger() {
        return answer.get();
    }
    public void setAnswer(int answer) {
        this.answer.set(answer);
    }

    public void setTime(int time) {
        this.time.set(time);
    }


    public ObservableList<String> getVariantsstring(){
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants.setAll(variants);
    }
    @Override
    public String toString(){
        return ""+name+" "+number_variants+" "+time+" "+answer;
    }
}
