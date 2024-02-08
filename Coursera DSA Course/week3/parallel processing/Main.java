import java.io.*;
import java.util.*;

class Thread {
    int index;
    long freeTime;
    //constructor
    public Thread(int i) {
        index = i;
        freeTime = 0;
    }
}

class MinHeap {
    Thread[] heap;
    int index;
    
    public MinHeap(int size){
        heap = new Thread[size];
        index = 0;
    }
    
    public Thread top(){
        return heap[0];
    }
    
    public void insert(Thread t){
        if(index >= heap.length) //no space
            return;
        heap[index] = t;
        siftUp(index);
        index ++;
    }
    
    public Thread remove(){
        Thread poped = heap[0];
        //replace root with the last element
        heap[0] = heap[--index];
        siftDown(0);
        return poped;
    }
    
    private boolean isLeaf(int i) {
        return (leftChild(i) >= index);
    }
    
    private int rightChild(int i){
        return i * 2 + 2;
    }
    
    private int leftChild(int i){
        return i * 2 + 1;
    }
    private int parent(int i) {
        return (i - 1) / 2;
    }
    private void swap(int s,int d) {
        Thread temp = heap[s];
        heap[s] = heap[d];
        heap[d] = temp; 
    }
    private void siftUp(int i) {
        if(parent(i) >= 0 && heap[i].index < heap[parent(i)].index) {
            swap(i,parent(i));
            siftUp(parent(i));
        }
    }
    
    public void siftDown(int i) {
        int minIndex;
        if(isLeaf(i))
            return;
        if(rightChild(i) >= index){//leftChild only
            minIndex = leftChild(i);
        } 
        else {//left and right childs
            if(heap[leftChild(i)].freeTime < heap[rightChild(i)].freeTime || (heap[leftChild(i)].freeTime == heap[rightChild(i)].freeTime && heap[leftChild(i)].index < heap[rightChild(i)].index ))
                minIndex = leftChild(i);
            else
                minIndex = rightChild(i);
        }
        if(heap[i].freeTime > heap[minIndex].freeTime || ( heap[i].freeTime == heap[minIndex].freeTime && heap[i].index > heap[minIndex].index)) {
            swap(i,minIndex);
            siftDown(minIndex);
        }
        else
            return;
    }
    
    public boolean isEmpty() {
        return index == 0;
    }
}


class Scheduler {
    MinHeap runningThreads;// Threads free now
    Queue<Long> tasksList; //tasks to process
    long time; //current time
    
    public Scheduler(MinHeap threads, Queue<Long> tasks) {
        //minHeap to store free threads according to their index
        runningThreads = threads;
        //input tasks queue
        tasksList = tasks;
    }
    
    public void assignTasks() {
        // while free threads is not empty
        while(!tasksList.isEmpty()) {
            Thread top = runningThreads.top();
            long taskTime = tasksList.remove();
            printOutput(top);
            //update its freeTime
            top.freeTime += taskTime;
            //update MinHeap
            runningThreads.siftDown(0);
        }
    }
    
    private void printOutput(Thread t){
      System.out.println(t.index +" "+t.freeTime);
    }
}
public class Main
{
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
	    //read number of threads
	    int n = scanner.nextInt();
	    //create a MinHeap to store threads according to their index
      MinHeap threads = new MinHeap(n);
	    //add threads to List
	    for(int i = 0 ; i < n; i++){
	        threads.insert(new Thread(i));
	    }
	    //read number of tasks
	    int m = scanner.nextInt();
      // List tasks queue
      Queue<Long> tasks = new LinkedList<>();
	    //read tasks processing time
	    for(int i = 0; i < m; i++){
	        long processingTime = scanner.nextLong();
	        tasks.add(processingTime);
	    }
		//create an instance of Scheduler
		Scheduler s = new Scheduler(threads,tasks);
		//start Scheduler
		s.assignTasks();
	}
}
