import java.util.*;
import java.io.*;

//store Node info
class Node {
    int value;
    int index;
    //constructor
    public Node(int v, int i) {
        value = v;
        index = i;
    }
}

//keep descening order of elements
//adding elements from the Endm, deleting from both sides
class Dequeue {
    LinkedList < Node > list;
    int windowSize;
    int maxValue;
    //constructor
    public Dequeue(int s) {
        //intialize the ArrayList
        list = new LinkedList < > ();
        //set window size
        windowSize = s;
        //intialize maxValue
        maxValue = Integer.MIN_VALUE;
    }

    private void enqueue(int a, int i) {
        //update maxValue
        if (a > maxValue || list.isEmpty())
            maxValue = a;
        list.add(new Node(a, i));
    }

    public void readValue(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            //dequeue first after first block
            if (i >= windowSize) {
                dequeue(i);
            }
            //remove smaller values
            removeSmaller(arr[i]);
            //enqueue the element
            enqueue(arr[i], i);
            //first block not completed
            if ((i + 1) >= windowSize) {
                //print output
                System.out.print(findMax() + " ");
            }
        }
    }

    private void dequeue(int i) {
        if (!list.isEmpty() && list.getFirst().index == i - windowSize) {
            //store the removed value
            int removedValue = list.getFirst().value;
            //remove the element
            list.removeFirst();
            //update maxValue if needed
            if (removedValue == maxValue && !list.isEmpty())
                maxValue = list.getFirst().value;
        }
    }

    private void removeSmaller(int a) { //O(1) //keep descening order
        while (!list.isEmpty() && list.getLast().value <= a) {
            list.removeLast(); // Remove smaller elements from the end
        }
    }

    private void calcMax() { // O(windowSize) //not used --> overall complexit O(n*windowSize)
        int max = Integer.MIN_VALUE;
        for (Node a: list) {
            if (a.value > max)
                max = a.value;
        }
        maxValue = max;
    }
    private int findMax() { //O(1)
        return maxValue;
    }

    public void printList() { //O(n)
        if (list.isEmpty()) {
            System.out.println("empty list");
            return;
        }
        for (Node a: list) {
            System.out.println("index: " + a.index);
            System.out.println("value: " + a.value);

        }
    }
}

public class Main {
    public static void main(String[] args) {
        //read number of elements
        Scanner in = new Scanner(System.in);
        //read array length
        int len = in.nextInt();
        //read array elements
        int[] elements = new int[len];
        for (int i = 0; i < len; i++) {
            elements[i] = in.nextInt();
        }
        //read window size
        int size = in.nextInt();
        //initialize the class
        Dequeue d = new Dequeue(size);
        //start the program
        d.readValue(elements);
    }
}