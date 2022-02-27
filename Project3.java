import java.io.*;
import java.util.*;


public class Project3 {


   public static void main(String[] args) throws IOException {
   
      if(args.length != 1) {
         System.out.printf("ERROR: Program requires one command line argument which is the path to a tab-delimited jobs text file");
         System.exit(0);
      }
   
      Scanner sc = new Scanner(new File(args[0]));    //Gets input from given command line argument file
   
      List<String> jobs = new ArrayList<>();
      List<Integer> start = new ArrayList<>();
      List<Integer> duration = new ArrayList<>();
   
      while (sc.hasNextLine()) {
         String input = sc.nextLine();
         String[] split = input.split("\\t");    //Delimits by tab
         jobs.add(split[0]);                           //Adds task name to jobs array
         start.add(Integer.parseInt(split[1]));        //Adds start time to start array
         duration.add(Integer.parseInt(split[2]));     //Adds duration to duration array
      }
   
      System.out.println("\nFCFS");
      List<Integer> scheduledFCFS = FCFS(start, duration);
      gant(jobs, scheduledFCFS, duration);
   
      System.out.println("\nSPN");
      List<Integer> scheduledSPN = SPN(start, duration);
      gant(jobs, scheduledSPN, duration);
   
      System.out.println("\nHRRN");
      List<Integer> scheduledHRRN = HRRN(start, duration);
      gant(jobs, scheduledHRRN, duration);
   
   }

   public static List<Integer> FCFS(List<Integer> start, List<Integer> duration) {
      List<Integer> scheduledStart = new ArrayList<>();
      scheduledStart.add(start.get(0));                                           //Schedules first job
      for (int i = 0; i < duration.size() - 1; i++) {
         scheduledStart.add( scheduledStart.get(i) + duration.get(i) );          //Schedules the following jobs in order
      }
      return scheduledStart;
   }

   public static List<Integer> SPN(List<Integer> start, List<Integer> duration) {
      int timeElapsed = 0;       //Keeps track of time elapsed for all scheduled tasks
   
      List<Integer> scheduledStart = new ArrayList<Integer>(Collections.nCopies(start.size(), -1)); //Initalized with -1 which indicates no tasks scheduled for that index
      List<Integer> remaining = new ArrayList<>(duration);    //Copy of duration times to be deleted by index once scheduled
      List<Integer> waitlist = new ArrayList<Integer>(Collections.nCopies(duration.size(), 1000)); //List to keep track of smallest task duration which has to be schuled for time elapsed
   
      scheduledStart.set(0, start.get(0)); //Schedules first job
      timeElapsed += duration.get(0);
   
   
      while(scheduledStart.contains(-1)) {      //Continues until all tasks have been scheduled
      
         for (int i = 1; i < remaining.size(); i++) { //Continues for remaining tasks that haven't been scheuled
         
            int currentDur = remaining.get(i);
            int currentStart = start.get(i);
         
            if (currentStart <= timeElapsed) {       //if the task is waiting to be scheuled
               waitlist.set(i, currentDur);         //Adds to list of tasks waiting to be schueled
            }
         
         }
      
         int minIndex = waitlist.indexOf(Collections.min(waitlist));     //Gets the smallest for waitlist
         remaining.set(minIndex, 1000);                                  //Removes it from remaining tasks
         scheduledStart.set(minIndex, timeElapsed);                      //Adds to schedule
         timeElapsed += duration.get(minIndex);
      
      }
   
      return scheduledStart;
   }

   public static List<Integer> HRRN(List<Integer> start, List<Integer> duration) {
      int timeElapsed = 0;
   
      List<Integer> scheduledStart = new ArrayList<Integer>(Collections.nCopies(start.size(), -1));
      List<Integer> remaining = new ArrayList<>(duration);
      List<Integer> waitlist = new ArrayList<Integer>(Collections.nCopies(duration.size(), -1));
   
      scheduledStart.set(0, start.get(0));
      timeElapsed += duration.get(0);
   
   
      while(scheduledStart.contains(-1)) {
      
         for (int i = 1; i < remaining.size(); i++) {
         
            int currentDur = remaining.get(i);
            int currentStart = start.get(i);
         
            if (currentStart <= timeElapsed) {
               int waitRatio = ( ((timeElapsed - currentStart) + (currentDur) ) / (currentDur) );  //calculates wait response ratio
               waitlist.set(i, waitRatio);
            }
         
         }
      
         int maxIndex = waitlist.indexOf(Collections.max(waitlist));   //gets task which has the highest wait response ratio
         remaining.set(maxIndex, -1);
         scheduledStart.set(maxIndex, timeElapsed);                    //Adds to schedule
         timeElapsed += duration.get(maxIndex);
      
      }
   
      return scheduledStart;
   
   }

   //Prints schedule into a text based gantt chart
   public static void gant(List<String> taskNames, List<Integer> scheduledStart, List<Integer> duration) {
      for (int i = 0; i < taskNames.size(); i++) {
         System.out.print(taskNames.get(i) + " ");
         System.out.print(" ".repeat(scheduledStart.get(i)));
         System.out.println("X".repeat(duration.get(i)));
      }
   }


}


