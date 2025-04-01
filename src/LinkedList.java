import java.util.Random;

// linked list class for a deck of cards
public class LinkedList {

    public Node head;
    public Node tail;
    public int size = 0;

    LinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    public void shuffle(int shuffle_count) {

        Random rand = new Random();
        for(int i = 0; i < shuffle_count; i++) {
            // pick two random integers
            int r1 = rand.nextInt(52);
            int r2 = rand.nextInt(52);

            swap(r1,r2); // swap nodes at these indices
        }
    }

    // remove a card from a specific index
    public Card remove_from_index(int index) {
        // Ensure the index is valid
        if (index < 0 || index >= size) {
            System.out.println("The desired index is out of bounds for adding " + index);
            return null;
        }
        if (index == 0) {
            return remove_from_head();
        }
        if (index == size - 1) {
            return remove_from_tail();
        }

        // Traverse to the desired index
        Node curr = head;
        for (int i = 1; i <= index; i++) {
            curr = curr.next;
        }

        // Remove the node
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        size--;                                 // Update the size

        return curr.data;
    }

    // insert a card at a specific index
    public void insert_at_index(Card x, int index) {
        // Ensure the index is valid
        if (index < 0 || index > size) {
            System.out.println("The desired index is out of bounds for adding " + index);
            return;
        }
        if (index == 0) {
            add_at_head(x);
            return;
        }
        if (index == size) {
            add_at_tail(x);
            return;
        }

        // Traverse to the desired index
        Node curr = head;
        for (int i = 1; i < index; i++) {               // Used i < index so that curr stops at one node before the desired index
            curr = curr.next;
        }

        // Insert the new node
        Node newNode = new Node(x);

        newNode.next = curr.next;
        newNode.prev = curr;
        curr.next.prev = newNode;
        curr.next = newNode;

        size++;                                         // Update the size
    }

    // swap two cards in the deck at the specific indices
    public void swap(int index1, int index2) {
        // Do nothing if indices are the same
        if (index1 == index2) { return; }

        if (index1 < index2) {
            Card card2 = remove_from_index(index2);
            Card card1 = remove_from_index(index1);
            insert_at_index(card2, index1);
            insert_at_index(card1, index2);
        }
        else {
            Card card1 = remove_from_index(index1);
            Card card2 = remove_from_index(index2);
            insert_at_index(card1, index2);
            insert_at_index(card2, index1);
        }
    }

    // add card at the end of the list
    public void add_at_tail(Card data) {
        if (head == null) {
            head = new Node(data);
            tail = head;
        }
        else {
            tail.next = new Node(data);
            tail.next.prev = tail;
            tail = tail.next;
        }
        size++;
    }

    // This is a self-made helper method
    private void add_at_head(Card data) {
        if (head == null) {
            head = new Node(data);
            tail = head;
        }
        else {
            Node newNode = new Node(data);
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    // remove a card from the beginning of the list
    public Card remove_from_head() {
        if (head == null) return null;

        Card removedData = head.data;

        // If list has only one element, update head and tail
        if (head == tail) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }

        size--;
        return removedData;
    }

    // This is a self-made helper method
    private Card remove_from_tail() {
        if (head == null) return null;

        Card removedData = tail.data;

        // If list has only one element, update head and tail
        if (head == tail) {
            head = null;
            tail = null;
        }
        else {
            tail.prev.next = null;
            tail = tail.prev;
        }

        size--;
        return removedData;
    }

    // check to make sure the linked list is implemented correctly by iterating forwards and backwards
    // and verifying that the size of the list is the same when counted both ways.
    // 1) if a node is incorrectly removed
    // 2) and head and tail are correctly updated
    // 3) each node's prev and next elements are correctly updated
    public void sanity_check() {
        // count nodes, counting forward
        Node curr = head;
        int count_forward = 0;
        while (curr != null) {
            curr = curr.next;
            count_forward++;
        }

        // count nodes, counting backward
        curr = tail;
        int count_backward = 0;
        while (curr != null) {
            curr = curr.prev;
            count_backward++;
        }

        // check that forward count, backward count, and internal size of the list match
        if (count_backward == count_forward && count_backward == size) {
            System.out.println("Basic sanity Checks passed");
        }
        else {
            // there was an error, here are the stats
            System.out.println("Count forward:  " + count_forward);
            System.out.println("Count backward: " + count_backward);
            System.out.println("Size of LL:     " + size);
            System.out.println("Sanity checks failed");
            System.exit(-1);
        }
    }

    // print the deck
    public void print() {
        Node curr = head;
        int i = 1;
        while(curr != null) {
            curr.data.print_card();
            if(curr.next != null)
                System.out.print(" -->  ");
            else
                System.out.println(" X");

            if (i % 7 == 0) System.out.println("");
            i = i + 1;
            curr = curr.next;
        }
        System.out.println("");
    }
}