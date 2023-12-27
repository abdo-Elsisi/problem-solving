import java.util.*;
import java.io.*;

class FlippingGame{
    int numOfOnes;
    int FlippedSeqMaxOnes;
    int[] arr;
    int left;
    int right;
    
    public FlippingGame(int[] nums) {
        arr = nums;
        //initialize variables
        left = 0;
        right = 0;
        numOfOnes = 0;
        FlippedSeqMaxOnes = Integer.MIN_VALUE;
    }
    
    public void findMaxNumOfOnes() {
        //initialize num of ones for a flipped Sequence
        int flippedSeqOnes = 0;
        for(int i = 0; i < arr.length; i++) {
            //start a new Sequence ?
            if(flippedSeqOnes < 0) {
                //start new Sequence
                flippedSeqOnes = 0;
                left = i+1;
                right = i+1;
            }
            if(arr[i] == 1) {
                //update num of ones
                numOfOnes++;
                //update num of ones when flipping this Sequence
                flippedSeqOnes--;
            }
            else {
                flippedSeqOnes++;
                //update the right
                right = i;
                
            }
            //update max if needed
            if(flippedSeqOnes > FlippedSeqMaxOnes)
                FlippedSeqMaxOnes = flippedSeqOnes;
        }
    }
    
    public int maxOnes() {
        return numOfOnes + FlippedSeqMaxOnes;
    }
}
public class Main
{
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		//initialize num of elements
		int n = 0;
		//read num of elements 
		try {
		    n = in.nextInt();
		} catch(InputMismatchException e) {
		    System.out.println("sorry you should enter a num only");
		    return;
		}
		//initialize an array to store number
		int[] nums = new int[n];
		//start reading numbers
		for(int i = 0; i <nums.length ; i++) {
		    try {
		        nums[i] = in.nextInt();
		    } catch(InputMismatchException e) {
		        //throw an error and exit
		        System.out.println("sorry, enter nums only");
		        return;
		    }
		    //check a value
		    if(nums[i] != 0 && nums[i] != 1) {
		        System.out.println("sorry, 0s and 1s only are accepted");
		        return;
		    }
		} 
		//create an instanc
		FlippingGame f = new FlippingGame(nums);
		f.findMaxNumOfOnes();
		System.out.println(f.maxOnes());
	}
}
