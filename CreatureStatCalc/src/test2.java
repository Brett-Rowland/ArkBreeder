public class test2 {
    public static void main(String[] args) {

//        Base Value
        double base = 1;

//        Increase Wild
        double Iw = .05;


//        Levels Wild
        double levels = 0;


//        Increase Wild Modifier
        double IwM = 1;


//        Taming Additive
        double Ta = 1;


//        Taming Additive Modifier
        double TaM = .14;

//        Taming Effectiveness
        double TE = 1;


//        Taming Mulit
        double Tm = .4;

//        Taming Multi Modifier
        double TmM = .44;


        float stats = (float) ((float) (base * (1 + levels * Iw * IwM) + Ta * TaM) * (1 + TE * Tm * TmM));


        float statsFloat = Math.round(stats*1000)/10.0f;
        System.out.println(statsFloat);






    }
}
