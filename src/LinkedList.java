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
        // FIXME
        if (index < 0 || index >= size) {                                // THIS IS JUST FOR TESTING PURPOSE
            System.out.println("The desired index is out of bounds for removing " + index);
            return null;
        }

        if (head == null) return null;                                  // Can add this to the previous condition too

        if (index == 0) {
            return remove_from_head();                                // Update the size
        }
        if (index == size - 1) {
            return remove_from_tail();
        }

        Node curr = head;
        for (int i = 1; i <= index; i++) {
            curr = curr.next;
        }

        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        size--;                                 // Update the size

        return curr.data;
    }

    // insert a card at a specific index
    public void insert_at_index(Card x, int index) {
        // FIXME
        if (index < 0 || index > size) {                                // THIS IS JUST FOR TESTING PURPOSE
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

        Node newNode = new Node(x);
        Node curr = head;
        for (int i = 1; i < index; i++) {               // Used i < index so that curr stops at one node before the desired index
            curr = curr.next;
        }

        newNode.next = curr.next;                       // Will throw an exception if curr is null (the desired index is larger than the size of the list)
        newNode.prev = curr;
        curr.next.prev = newNode;
        curr.next = newNode;

        size++;                                         // Update the size
    }

    // swap two cards in the deck at the specific indices
    public void swap(int index1, int index2) {                                          // --> CHECK THIS LATER
        // FIXME
        if (index1 == index2) { return; }
        else if (index1 < index2) {
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




//        int i = 0;
//        int j = 0;
//        Node n1 = head;
//        Node n2 = head;
//
//        while (i <= index1 || j <= index2) {
//            if (i != index1) {
//                n1 = n1.next;
//                i++;
//            }
//            if (j != index2) {
//                n2 = n2.next;
//                j++;
//            }
//        }
//
//        n1.prev.next = n2;
//        n2.prev.next = n1;
//        n1.next = n2.next;
//        n2.next = n1.next;
//        n1.next.prev = n1;
//        n2.next.prev = n2;
//        n2.prev = n1.prev;
    }

    // add card at the end of the list
    public void add_at_tail(Card data) {
        // FIXME
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

    public void add_at_head(Card data) {                    // This is a self-made helper method
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
        // FIXME
        if (head == null) return null;

        Card removedData = head.data;
        if (head == tail) {                 // If the list only had one element and would be empty after removing the node, update tail as well
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

    public Card remove_from_tail() {
        if (head == null) return null;

        Card removedData = tail.data;
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