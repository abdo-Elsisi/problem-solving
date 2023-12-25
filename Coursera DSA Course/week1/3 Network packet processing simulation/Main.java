import java.util.*;
import java.io.*;

class Packet {
    int arriveTime;
    int processTime;
    int packetNum;
    //constructor
    public Packet() {
        
    }
    public Packet(int Ai,int Pi,int i) {
        arriveTime = Ai;
        processTime = Pi;
        packetNum = i;
    }
    
    public void printPacket() {
        System.out.println("packer arrive time: "+arriveTime);
        System.out.println("packer process time: "+processTime);
    }
}

class PacketProcessor {
    int bufferSize;
    int currentProcessingTime;
    Queue<Packet> buffer; // Example using LinkedList
    LinkedList<Packet> inputProcesses;
    int[] startTime;
    
    //constructor
    public PacketProcessor(int s,int n) {
        //read the buff size
        bufferSize = s;
        currentProcessingTime = 0;
        buffer = new LinkedList<>();
        inputProcesses = new LinkedList<>();
        startTime = new int[n];
    }
    //read process from user
    public void readProcess(int Ai,int Pi,int i) {
        //create the packet
        Packet currPacket = new Packet(Ai,Pi,i);
        //add it to the LinkedList
        inputProcesses.add(currPacket);
    }
    
    public void printInputProcesses(){
        for(Packet p:inputProcesses) {
		    p.printPacket();
		}
    }
    //buffer Queue methods
    private void enqueuePacket(Packet p) {
        buffer.offer(p);
    }
    
    private Packet dequeuePacket() {
        return buffer.poll();
    }
    
    private boolean isFull() {
        return buffer.size() >= bufferSize ;
    }
    
    private void fillBuffer() {
        //check buffer capcacity and arrived packets
        while(!isFull() && !inputProcesses.isEmpty()) {
            Packet curr = inputProcesses.get(0);
            if (curr.arriveTime > currentProcessingTime)
                break;
        //enqueu them
            enqueuePacket(curr);
        //remove them from the input
            inputProcesses.remove(0);
        }
    }
    
    private void dropTailPackets() {
        //drop packets not buffered because buffer is full
        while(!inputProcesses.isEmpty()) {
            Packet curr = inputProcesses.get(0);
            if (curr.arriveTime <= currentProcessingTime){
                startTime[curr.packetNum] = -1;
                inputProcesses.remove(0);
                // System.out.println("packet dropped");
            } else {
                break;
            }
        }
    }
    
    private void updateBuffer(){
        fillBuffer();
        dropTailPackets();
    }
    //add packets arrived while processor where running
    private void updateBufferAsync(){
        while(!inputProcesses.isEmpty() && inputProcesses.get(0).processTime < currentProcessingTime)  {
            Packet curr = inputProcesses.get(0);
            if(!isFull()) {
                enqueuePacket(curr);
            }
            else {
                //drop the Packet
                startTime[inputProcesses.get(0).packetNum] = -1;
                inputProcesses.remove(0);
            }
        }
    }
    //processor methods
    private void processPacket(Packet p) {
        //print start time
        startTime[p.packetNum] = currentProcessingTime;
        // System.out.println(currentProcessingTime);
        int i = p.processTime;
        while(i-- > 0) {
            //update the buffer
            updateBuffer();
            //update counter
            currentProcessingTime ++;
        }
        //handle packets arrived during running
        updateBufferAsync();
    }
    //run
    public void start() {
        //update the buffer
        updateBuffer();
        while(!buffer.isEmpty() || !inputProcesses.isEmpty()) {
            //run first process
            while(!buffer.isEmpty()) {
                processPacket(buffer.element());
                //dequeue finished
                dequeuePacket();
                //update buffer
                updateBuffer();
            }
            //update counter
            currentProcessingTime ++;
            updateBuffer();
        }
    }
    
    public void printTimes(){
        for(int t:startTime){
            System.out.println(t);
        }
    }
}

public class Main
{
	public static void main(String[] args) {
		//read user input
		Scanner scanner = new Scanner(System.in);
		int size = scanner.nextInt();
		int numOfProcesses = scanner.nextInt();
		scanner.nextLine();//consume the nex line char
// 		System.out.println("your Buffer Size is :"+size);
// 		System.out.println("processes available: "+numOfProcesses);
		
		PacketProcessor pc = new PacketProcessor(size,numOfProcesses);
		//test arr
        for(int i = 0; i < numOfProcesses ; i++) {
		    int processArriveTime = scanner.nextInt();
		    int processProcessingTime = scanner.nextInt();
		    pc.readProcess(processArriveTime,processProcessingTime,i);
		}
		pc.start();
		pc.printTimes();
		

	}
}
