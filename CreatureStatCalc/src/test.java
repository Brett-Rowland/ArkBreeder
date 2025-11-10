import java.util.HashMap;
import java.util.Set;

public class test {
    public static void main(String[] args) {

//        System.out.println(add(1f,2f));

        String test = "ABC123DE#$@Orange";

        HashMap<String, Double> test2 = new HashMap<>();


        test2.put("Orange", 2.0);
        test2.put("Orange2", 3.0);


        System.out.println(test2);

        System.out.println(test2.keySet());

        Set<String> test3 = test2.keySet();
        for (String t: test3){
            System.out.println(t);
        }



    }


    static Float add(Float a, Float b){
        return a + b;
    }
}
