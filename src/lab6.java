import java.util.Scanner;

public class lab6 {

    public static LinkedList initialize_deck() {

        LinkedList deck = new LinkedList();

        // populate linked list with a single deck of cards
        for (Card.suites s : Card.suites.values()) {
            for(Card.ranks r : Card.ranks.values()) {
                if (r != Card.ranks.NULL && s != Card.suites.NULL) {
                    Card newCard = new Card(s, r);
//                    newCard.print_card();
                    deck.add_at_tail(newCard);
                }
            }
        }

        return deck;
    }

    private static void play_blind_mans_bluff(LinkedList player1, LinkedList computer, LinkedList deck) {
        System.out.println("\nStarting Blind mans Bluff \n");
        // play the game FIXME
        int wins = 0;
        int losses = 0;
        int lossesInARow = 0;

        Scanner scnr = new Scanner(System.in);

        for (int i = 0; i < 5; i++) {
            Card computerCard = computer.remove_from_head();
            Card playerCard = player1.remove_from_head();

            System.out.print("Computer's Card: ");
            computerCard.print_card();
            System.out.println();

            System.out.print("Is your card higher or lower? (Enter H or L): ");
            char guess = Character.toLowerCase(scnr.next().charAt(0));

            boolean correctGuess;
            if (playerCard.getRank().ordinal() > computerCard.getRank().ordinal()) {
                correctGuess = (guess == 'h');
            }
            else if (playerCard.getRank().ordinal() < computerCard.getRank().ordinal()) {
                correctGuess = (guess == 'l');
            }
            else {
                if (playerCard.getSuit().ordinal() > computerCard.getSuit().ordinal()) {
                    correctGuess = (guess == 'h');
                }
                else {
                    correctGuess = (guess == 'l');
                }
            }

            System.out.print("\nYour card: ");
            playerCard.print_card();
            System.out.print("\nComputer's card: ");
            computerCard.print_card();
            System.out.println();

            if (correctGuess) {
                System.out.println("Your guess is correct. You won this round!\n");
                wins++;
            }
            else {
                System.out.println("Your guess is incorrect. You lost this round!\n");
                losses++;
            }

            deck.add_at_head(computerCard);
            deck.add_at_tail(playerCard);

            if (losses == 3) {
                rage_quit(player1, computer, deck);
                losses = 0;
                i = -1;                                     // Resets the number of rounds after rage-quitting.
            }
        }

        System.out.println("Game Over!");
        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);

    }

    public static void rage_quit(LinkedList player1, LinkedList computer, LinkedList deck) {
        System.out.println("-----------------------------");
        System.out.println("        Rage Quitting        ");
        System.out.println("-----------------------------");

        while (player1.size > 0 || computer.size > 0) {                         // Tried to remove the remaining cards from player's and computer's deck within the same loop for better time complexity
            if (player1.size > 0) deck.add_at_tail(player1.remove_from_head());
            if (computer.size > 0) deck.add_at_tail(computer.remove_from_head());
        }

        deck.shuffle(512);

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        System.out.println("Cards successfully redealt");
    }

    public static void main(String[] args) {

        // create a deck (in order)
        LinkedList deck = initialize_deck();
        deck.print();
        deck.sanity_check(); // because we can all use one

        // shuffle the deck (random order)
        deck.shuffle(512);
        deck.print();
        deck.sanity_check(); // because we can all use one

        // cards for player 1 (hand)
        LinkedList player1 = new LinkedList();
        // cards for player 2 (hand)
        LinkedList computer = new LinkedList();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            // player removes a card from the deck and adds to their hand
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        // let the games begin!
        System.out.println("----------------------------------------------------------");
        play_blind_mans_bluff(player1, computer, deck);
    }
}
