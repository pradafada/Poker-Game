import java.util.ArrayList;
/*
  Class: CSE 1322L
  Section: 15171
  Term: Spring 2023
  Instructor: Harshitha Nirujogi
  Name: Selah Rice
  Lab#: 15232
   */
public class Poker {
    private PlayingCards deck;

    ArrayList<String> hand1 = new ArrayList<>();
    ArrayList<String> hand2 = new ArrayList<>();

    public void dealHands () {
        for (int i = 0; i < 5; i++) {
        hand1.add(deck.getNextCard());
        hand2.add(deck.getNextCard());
        }
    }

    public Poker () {
        deck = new PlayingCards();
        deck.Shuffle();
        dealHands();
    }

    public Poker (ArrayList<String> hand1, ArrayList<String> hand2) {
        deck = new PlayingCards();
        this.hand1 = hand1;
        this.hand2 = hand2;
    }

    public void showHand (int x) {
        if (x == 1) {
            System.out.println ("Player 1's hand: ");
            for (String i : hand1) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
        else {
            System.out.println("Player 2's hand: ");
            for (String i : hand2) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
    }

    public int[] countSuite (ArrayList<String> hand) {
        int[] array = new int[4];
        for (String currentCard : hand) {
            if (currentCard.contains("Clubs")) {
                array[0] += 1;
            } else if (currentCard.contains("Diamonds")) {
                array[1] += 1;
            } else if (currentCard.contains("Hearts")) {
                array[2] += 1;
            } else if (currentCard.contains("Spades")) {
                array[3] += 1;
            }
        }
        return array;
    }

    public int[] countValues (ArrayList<String> hand) {
        String value, tValue;
        int[] array = new int[14];
        for (int i = 0; i < hand.size(); i ++) {

            value = hand.get(i).substring(0,1);
            tValue = hand.get(i).substring(0,2);

            if (value.contains("A")) {
                array[1] += 1;
            }
            else if (value.contains("2")) {
                array[2] += 1;
            }
            else if (value.contains("3")) {
                array[3] += 1;
            }
            else if (value.contains("4")) {
                array[4] += 1;
            }
            else if (value.contains("5")) {
                array[5] += 1;
            }
            else if (value.contains("6")) {
                array[6] += 1;
            }
            else if (value.contains("7")) {
                array[7] += 1;
            }
            else if (value.contains("8")) {
                array[8] += 1;
            }
            else if (value.contains("9")) {
                array[9] += 1;
            }
            else if (tValue.contains("10")) {
                array[10] += 1;
            }
            else if (value.contains("J")) {
                array[11] += 1;
            }
            else if (value.contains("Q")) {
                array[12] += 1;
            }
            else if (value.contains("K")) {
                array[13] += 1;
            }
        }
        return array;
    }

    public int numPairs (int[] numValues) {
        int pair = 0;
        for (int i = 0; i < numValues.length; i++) {
            if (numValues[i] == 2) {
                pair += 1;
            }
        }
        return pair;
    }

    public int threeOfAKind (int[] numValues) {
        int three = 0;
        for (int i = 0; i < numValues.length; i++) {
            if (numValues[i] == 3) {
                three = i;
            }
        }
        return three;
    }

    public int fourOfAKind (int[] numValues) {
        int four = 0;
        for (int i = 0; i < numValues.length; i++) {
            if (numValues[i] == 4) {
                four = i;
            }
        }
        return four;
    }

    public boolean fullHouse (int[] numValues) {
        boolean full = false;
        for (int i = 0; i < numValues.length; i++) {
            threeOfAKind(numValues);
            numPairs(numValues);
        }
        if ((threeOfAKind(numValues) != 0) && (numPairs(numValues) == 1)) {
            full = true;
        }
        return full;
    }

    public boolean straight (int[] numValues) {
        boolean straight = false;
        for (int i = 1; i < numValues.length - 4; i++) {
            if (numValues[i] == 1 && numValues[i + 1] == 1 && numValues[i + 2] == 1 && numValues[i + 3] == 1 && numValues[i + 4] == 1) {
                straight = true;
                break;
            }
        }

        if (numValues[10] == 1 && numValues[11] == 1 && numValues[12] == 1 && numValues[13] == 1 && numValues[1] == 1) {
            straight = true;
        }
        return straight;
    }

    public boolean flush (int[] numSuites) {
        boolean flush = false;
        for (int i = 0; i < numSuites.length; i++) {
            if (numSuites[i] == 5) {
                flush = true;
                break;
            }
        }
        return flush;
    }

    public boolean straightFlush (int[] numValues, int[] numSuites) {
        boolean sf = false;
        for (int i = 0; i < numValues.length; i++) {
            straight(numValues);
        }
        for (int i = 0; i < numSuites.length; i++) {
            flush(numSuites);
        }
        if (straight(numValues) && flush(numSuites)) {
            sf = true;
        }
        return sf;
    }

    public boolean royalFlush (int[] numValues, int[] numSuites) {
        boolean rf = false;
        for (int i = 0; i < numSuites.length; i++) {
            flush(numSuites);
        }
        if (flush(numSuites) && numValues[10] == 1 && numValues[11] == 1 && numValues[12] == 1 && numValues[13] == 1 && numValues[1] == 1) {
            rf = true;
        }
        return rf;
    }

    public String scoreHand (int x) {
        String strength;
        ArrayList<String> selectedHand;
        if (x == 1) {
            selectedHand = hand1;
        }
        else {
            selectedHand = hand2;
        }
        int[] numValues = countValues(selectedHand);
        int[] numSuites = countSuite(selectedHand);

        if (royalFlush(numValues, numSuites)) {
            strength = "Royal Flush";
        }
        else if (straightFlush(numValues, numSuites)) {
            strength = "Straight Flush";
        }
        else if (fourOfAKind(numValues) != 0) {
            strength = "4 of a kind";
        }
        else if (fullHouse(numValues)) {
            strength = "Full House";
        }
        else if (flush(numSuites)) {
            strength = "Flush";
        }
        else if (straight(numValues)) {
            strength = "Straight";
        }
        else if (threeOfAKind(numValues) != 0) {
            strength = "3 of a kind";
        }
        else if (numPairs(numValues) == 2) {
            strength = "2 pairs";
        }
        else if (numPairs(numValues) == 1) {
            strength = "1 pair";
        }
        else {
            strength = "High Card";
        }
        return strength;
    }
}
