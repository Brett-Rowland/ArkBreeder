public class CreatureTestCalc {
    public static void main(String[] args) {

//        Base Value
        double B = 850;

//        Increase per wild point
        double lw = .2;

//      Increase Per Wild Level Modifier
        double Iwm = 1;

//        Taming effectiveness
//        double TE = 1;

//        Taming Additive
        double Ta = .5;

//        Taming Additive Slider
        double TaSlider = .14;


        double wildLevels = 0;


        double Tm = -.1;

//        double TmM = .44;

        double health;
        if (Tm <= 0) {
             health = ((B * (1 + wildLevels * lw * Iwm)) * (1 + Tm)) + (Ta * TaSlider);
        }
        else{
            health = ((B * (1 + wildLevels * lw * Iwm)) * (Tm)) + (Ta * TaSlider);
        }

        health = Math.round(health*10.0)/10.0;

        System.out.println(health);





    }
}
