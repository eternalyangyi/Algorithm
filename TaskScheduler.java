package net.datastructures;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.datastructures.HeapPriorityQueue;

public class TaskScheduler {
	/* Define pattern
	 * Task name should be several numbers following at least one letter.
	 */
	private static String INTEGER_PATTEN = "^[0-9]\\d*$";
	private static String TASKNUM_PATTEN = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$";
	public static Pattern pattern1 = Pattern.compile(INTEGER_PATTEN);
	public static Pattern pattern2 = Pattern.compile(TASKNUM_PATTEN);
	/* This method is designed to read file and add contents into a Arraylist.
	 * 
	 */
    private ArrayList<String> readFile(String FilePath) throws FileDoesnotExist, IOException {
        ArrayList<String> fileContent = new ArrayList<String>();
        File File1 = new File(FilePath);
        if(!File1.exists()){
            throw new FileDoesnotExist();
        }
        FileInputStream fis = new FileInputStream(File1);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        while ((line = br.readLine()) != null) {
            fileContent.add(line);
        }
        br.close();
        return fileContent;
    }
    /*
     * This method is designed for add value to object task.
     * After that, insert object into HeapPriorityQueue based on object's release time.
     * Because of sort function of HeapPriorityQueue, the time complexity is O(N * logN).
     * This method return a HeapPriorityQueue.
     */
    public HeapPriorityQueue<Integer,TaskInformation> ProcessingData(String[] TaskData) throws InputError{
    	HeapPriorityQueue<Integer,TaskInformation> Tasklist = new HeapPriorityQueue<Integer,TaskInformation>();
    	int index = 0;
        while(index < TaskData.length){
        	TaskInformation task = new TaskInformation();
        	String TaskNumber = TaskData[index];
        	Matcher matcher1 = pattern2.matcher(TaskNumber);
        	boolean result1 = matcher1.matches();
        	if(result1){
        		task.setTaskNum(TaskNumber);
        		index ++;
        	}else{
        		throw new InputError(TaskNumber);
        	}
        	String ExecutionTime = TaskData[index];
        	Matcher matcher2 = pattern1.matcher(ExecutionTime);
        	boolean result2 = matcher2.matches();
        	if(result2){
        		task.setExecutionTime(Integer.parseInt(ExecutionTime));
        		index ++;
        	}else{
        		throw new InputError(TaskNumber);
        	}
        	String ReleaseTime = TaskData[index];
        	Matcher matcher3 = pattern1.matcher(ReleaseTime);
        	boolean result3 = matcher3.matches();
        	if(result3){
        		task.setReleaseTime(Integer.parseInt(ReleaseTime));
        		index ++;
        	}else{
        		throw new InputError(TaskNumber);
        	}
        	String Deadline = TaskData[index];
        	Matcher matcher4 = pattern1.matcher(Deadline);
        	boolean result4 = matcher4.matches();
        	if(result4){
        		task.setDeadline(Integer.parseInt(Deadline));
        		index ++;
        	}else{
        		throw new InputError(TaskNumber);
        	}
        	if ((task.getDeadline() - task.getReleaseTime()) < task.getExecutionTime()){
        		throw new InputError(TaskNumber);
        	}
        	Tasklist.insert(Integer.parseInt(ReleaseTime), task);
        }
    	return Tasklist;
    }
    /*
     * This method is designed to scheduling task.
     * In the first step, initializing available time for each core.
     * After that, create a main while loop for push objects from HeapPriorityQueue and count tasks which are arranged.
     * Then the first internal while loop judge whether objects pushed by HeapPriorityQueue equal current time or not.
     * If it equals current time, insert it into another HeapPriorityQueue based on its dead line.
     * The second internal while loop is designed to arrange task.
     * If current time is larger or equal to available time, insert it into final result list.
     * Otherwise, continue the loop.
     * For the time complexity, the first internal while loop should be O(N)[Removing object from HeapPriorityQueue] 
     * + O(N * logN)[insert object into HeapPriorityQueue],
     * the second internal while loop's time complexity should be O(N)[Removing object from HeapPriorityQueue].
     * In conclusion, the total time complexity is O(N * logN).
     */
    public ArrayList<String> ScheduleTask(HeapPriorityQueue<Integer,TaskInformation> PQ,int CoreNum) {
    	ArrayList<String> Result = new ArrayList<String>();
    	int time = 0;
    	int [] CoreAvailableTime = new int[CoreNum];
    	for(int i = 0; i <CoreNum; i++){
    		CoreAvailableTime[i] = 0;
    	}
    	TaskInformation TaskInitial = new TaskInformation();
    	TaskInitial.setTaskNum("Initial");
    	HeapPriorityQueue<Integer,TaskInformation> WaittingQueue = new HeapPriorityQueue<Integer,TaskInformation>();
    	int arrangedtask = 0;
    	int totaltasknum = PQ.size();
    	//while(!PQ.isEmpty() && !WaittingQueue.isEmpty()){
    	while(arrangedtask < totaltasknum){
    		while(!PQ.isEmpty() && PQ.min().getKey() == time){
    			TaskInformation TaskIn = new TaskInformation();
    			TaskIn = PQ.removeMin().getValue();
    			//System.out.println(TaskIn);
    			WaittingQueue.insert(TaskIn.getDeadline(), TaskIn);
    		}
    		int CurrentCoreNo = 1;
    		while(!WaittingQueue.isEmpty() && CurrentCoreNo<=CoreNum){
    			if(time >= CoreAvailableTime[CurrentCoreNo-1]){
    				TaskInformation Task = new TaskInformation();
        			Task = WaittingQueue.removeMin().getValue();
        			//System.out.println(Task);
        			if (time + Task.getExecutionTime() > Task.getDeadline()){
        				return null;
        			}
        			else{
        				CoreAvailableTime[CurrentCoreNo-1] = Task.getExecutionTime() + time;
        				Result.add(Task.getTaskNum()+" Core"+CurrentCoreNo+" "+time);
        				arrangedtask ++;
        			}
    			}
    			CurrentCoreNo ++;
    		}
    		time ++;
        }
    	return Result;
    }
    /*
     * This method is designed for writting contents into a txt file.
     */
    public void WriteFile2(ArrayList<String> Result,String filename) throws IOException{
    	File TaskSchedule = new File(filename);
    	FileOutputStream fos = new FileOutputStream(TaskSchedule);
    	if (Result != null){
    	for(String line : Result){
    		fos.write(line.getBytes());
    		fos.write(" ".getBytes());
    	}
    	}
    	else{
    		fos.write("No feasible schedule".getBytes());
    	}
    	fos.close();	
    }
    /*
     * This method is designed to initialize basic variables and processing files which is ready for getting contents,
     * as well as calling three main functions.
     */
    static void scheduler(String file1, String file2, int m) throws InputError{
        TaskScheduler scheduler = new TaskScheduler();
        ArrayList<String> FileContent = new ArrayList<String>();
        try{
            FileContent = scheduler.readFile(file1);
            StringBuilder b = new StringBuilder();
            for(String line : FileContent){
            	b.append(line);
            	b.append(" ");
            }
            String temp = b.toString();
            String[] TaskData = temp.replaceAll(" +"," ").split(" ");
            HeapPriorityQueue<Integer, TaskInformation> Tasklist = scheduler.ProcessingData(TaskData);
            ArrayList<String> Result = scheduler.ScheduleTask(Tasklist, m);
            scheduler.WriteFile2(Result, file2);
        }
        catch(FileDoesnotExist e){
        }
        catch(IOException ioException){
        }
    }
    public static void main(String[] args) throws Exception{

        
        TaskScheduler.scheduler("samplefile1.txt", "feasibleschedule1", 4);
   		/** There is a feasible schedule on 4 cores */      
		TaskScheduler.scheduler("samplefile1.txt", "feasibleschedule2", 3);
  		 /** There is no feasible schedule on 3 cores */
    	TaskScheduler.scheduler("samplefile2.txt", "feasibleschedule3", 5);
   /** There is a feasible scheduler on 5 cores */ 
    	TaskScheduler.scheduler("samplefile2.txt", "feasibleschedule4", 4);
   /** There is no feasible schedule on 4 cores */

   /** There is no feasible scheduler on 2 cores */ 
   		TaskScheduler.scheduler("samplefile3.txt", "feasibleschedule5", 2);
    /** There is a feasible scheduler on 2 cores */ 
   		TaskScheduler.scheduler("samplefile4.txt", "feasibleschedule6", 2);

      }

    

}
/*
 * Two exception.
 */
class FileDoesnotExist extends Exception{
	public FileDoesnotExist() {
		System.out.println("File1 does not exist");
	}
}
class InputError extends Exception{
	public InputError(String Tasknum) {
		System.out.println("input error when reading the attribute of the task " + Tasknum);
	}
}
/*
 * This class is designed for packaging four values into one object.
 */
class TaskInformation{
	private String TaskNum;
	private int ReleaseTime;
	private int Deadline;
	private int ExecutionTime;
	public TaskInformation(){
		
	}
	public TaskInformation(String TaskNum,int ReleaseTime, int Deadline,int ExecutionTime){
		this.TaskNum = TaskNum;
		this.ReleaseTime = ReleaseTime;
		this.Deadline = Deadline;
		this.ExecutionTime = ExecutionTime;
	}
	public String getTaskNum(){
		return TaskNum;
	}
	public int getReleaseTime(){
		return ReleaseTime;
	}
	public int getDeadline(){
		return Deadline;
	}
	public int getExecutionTime(){
		return ExecutionTime;
	}
	public void setTaskNum(String TaskNum){
		this.TaskNum = TaskNum;
	}
	public void setReleaseTime(int ReleaseTime){
		this.ReleaseTime = ReleaseTime;
	}
	public void setDeadline(int Deadline){
		this.Deadline = Deadline;
	}
	public void setExecutionTime(int ExecutionTime){
		this.ExecutionTime = ExecutionTime;
	}
}
