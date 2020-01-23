package sagidllin.samsung.project.model;

import sagidllin.samsung.project.MainApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable {
    static List<int[]> sentTestToAndroid=new ArrayList<int[]>();
    static boolean flag_to_write=true;
   public static boolean flag_to_start=false;
    public  Test(){
        for(Question question: MainApp.questionData){
            sentTestToAndroid.add(new int[]{question.getNumber_variantsinteger(),question.getTimeinteger()});
        }
    }
    public  static ArrayList<ArrayList<Object>>answers_data= new ArrayList<>();
    @Override
    public String toString(){
        String result="";
        for(Object obj:answers_data){
            result+=obj.toString();
        }
        return result;
    }
    public  static boolean flag_to_read=false;
    public static int five;
    public static int four;
    public static int three;
    public static double sr_ball;
}
