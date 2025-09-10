import java.util.Scanner;

public class CreatureCalc {

    public static Float[] fill_in(String[] statName){
        Float[] in = new Float[6];


        Scanner sc  = new Scanner(System.in);

        for (int i = 0; i < 6; i++) {
            System.out.print(statName[i] + ": ");
            in[i] = sc.nextFloat();
        }
     return in;
    }

    public static void calc_stats(String[] statName,Float[] base, Float[] incremental, Float[] current){

        for (int i = 0; i < 6; i++) {
            System.out.println(statName[i] + " Points: " + (current[i] - base[i]) / incremental[i]);
        }


    }

    public static void main(String[] args) {
        String[] statName = new String[6];
        statName[0] = "Health";
        statName[1] = "Stamina";
        statName[2] = "Food";
        statName[3] = "Weight";
        statName[4] = "Oxygen";
        statName[5] = "Melee";
        System.out.println("Base Values");
        Float[] base_stats = fill_in(statName);

        System.out.println("Incremental Values");
        Float[] increment_stats= fill_in(statName);

        System.out.println("Creature stats Values");
        Float[] current_stats =  fill_in(statName);

        calc_stats(statName, base_stats, increment_stats, current_stats);

    }
}
