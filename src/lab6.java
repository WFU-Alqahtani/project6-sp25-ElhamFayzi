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

        int wins = 0;
        int losses = 0;
        int lossesInARow = 0;

        Scanner scnr = new Scanner(System.in);

        // Game loops for 5 rounds
        for (int i = 0; i < 5; i++) {
            Card computerCard = computer.remove_from_head();
            Card playerCard = player1.remove_from_head();

            System.out.print("Computer's Card: ");
            computerCard.print_card();
            System.out.println();

            char guess;
            boolean validInput = true;

            // Loop until input is valid
            do {
                System.out.print("Is your card higher or lower? (Enter H or L): ");
                guess = Character.toLowerCase(scnr.next().charAt(0));
                if (guess != 'h' && guess != 'l') {
                    validInput = false;
                }
            } while (!validInput);


            boolean correctGuess;
            // Compare player's card against computer's card
            if (playerCard.getRank().ordinal() > computerCard.getRank().ordinal()) {
                correctGuess = (guess == 'h');
            }
            else if (playerCard.getRank().ordinal() < computerCard.getRank().ordinal()) {
                correctGuess = (guess == 'l');
            }
            else {
                // In case of a rank tie, compare suits, too
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

            // Check if the guess was correct
            if (correctGuess) {
                System.out.println("Your guess is correct. You won this round!\n");
                wins++;
                lossesInARow = 0;               // Number of losses in a row resets on a win
            }
            else {
                System.out.println("Your guess is incorrect. You lost this round!\n");
                losses++;
                lossesInARow++;
            }

            // Adding cards back to the deck
            deck.add_at_tail(computerCard);
            deck.add_at_tail(playerCard);

            if (lossesInARow == 3) {
                rage_quit(player1, computer, deck);
                lossesInARow = 0;
                i = -1;                                     // Reset the number of rounds so that the game restarts from first round
            }
        }

        // Display win/loss statistics
        System.out.println("Game Over!");
        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);

    }

    public static void rage_quit(LinkedList player1, LinkedList computer, LinkedList deck) {
        System.out.println("-----------------------------");
        System.out.println("        Rage Quitting        ");
        System.out.println("-----------------------------");

        // Return all remaining cards from both player's and computer's hands back to the deck
        while (player1.size > 0 || computer.size > 0) {                         // Tried to remove the remaining cards from player's and computer's deck within the same loop for runtime for larger inputs
            if (player1.size > 0) deck.add_at_tail(player1.remove_from_head());
            if (computer.size > 0) deck.add_at_tail(computer.remove_from_head());
        }

        deck.shuffle(512);

        // Redeal 5 cards to each player after rage-quit
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
