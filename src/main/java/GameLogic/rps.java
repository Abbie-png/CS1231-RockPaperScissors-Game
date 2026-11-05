package GameLogic;

public class rps {
    //public static void main(String[] args) {}

    public static int round(String player, int[] score) {

        int computer = (int) (Math.random()*3 + 1);

        String creator;

        if (computer == 3)
            creator = "rock";

        else if (computer == 2)
            creator = "paper";

        else
            creator = "scissors";

        //checking who wins...
        if ((player.equals("rock") && creator.equals("scissors"))
                || (player.equals("paper") && creator.equals("rock"))
                || (player.equals("scissors") && creator.equals("paper"))) {
            score[0]++;
            return 1;
        }
        else if (player.equals(creator)) {
            return 2;
        }
        else {
            score[1]++;
            return 0;
        }
    }

    public static int getScore(int who, int[] score) {
        if (who == 0)
            return score[0];
        else
            return score[1];
    }
}
