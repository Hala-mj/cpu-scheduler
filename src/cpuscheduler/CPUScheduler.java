
package cpuscheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Process {
    int pid;
    int arrivalTime;
    List<Integer> cpuBursts;
    List<Integer> ioBursts;

    public Process(int pid, int arrivalTime, List<Integer> cpuBursts, List<Integer> ioBursts) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.cpuBursts = cpuBursts;
        this.ioBursts = ioBursts;
    }
}

public class CPUScheduler {
    public static void main(String[] args) {
        // Usage: java cpuscheduler.CPUScheduler [dataFile] [timeQuantum]
        String dataFile = args.length > 0 ? args[0] : "data/CPU-NewData.txt";
        int timeQuantum = args.length > 1 ? Integer.parseInt(args[1]) : 20;

        List<Process> processes = readFromFile(dataFile);
        
        System.out.println("First-Come-First-Serve (FCFS) Scheduling:");
        FCFSscheduling(processes);
        System.out.println();
        
        System.out.println("Round Robin (RR) Scheduling (Time Quantum = " + timeQuantum + "ms):");
        RRscheduling(processes, timeQuantum);
    }

    private static List<Process> readFromFile(String fileName) {
        List<Process> processes = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                int pid = Integer.parseInt(tokens[0]);
                int arrivalTime = Integer.parseInt(tokens[1]);
                List<Integer> cpuBursts = new ArrayList<>();
                List<Integer> ioBursts = new ArrayList<>();
                for (int i = 2; i < tokens.length; i++) {
                    if (i % 2 == 0) {
                        cpuBursts.add(Integer.parseInt(tokens[i]));
                    } else {
                        ioBursts.add(Integer.parseInt(tokens[i]));
                    }
                }
                processes.add(new Process(pid, arrivalTime, cpuBursts, ioBursts));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return processes;
    }

    private static void FCFSscheduling(List<Process> processes) {
        int time = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int contextSwitches = 0;

        for (Process process : processes) {
   
            int waitingTime = Math.max(0, time - process.arrivalTime);
            totalWaitingTime += waitingTime;

         
            int burstTime = 0;
            for (int burst : process.cpuBursts) {
                burstTime += burst;
            }
            int turnaroundTime = waitingTime + burstTime;
            totalTurnaroundTime += turnaroundTime;

       
            time += burstTime;
            contextSwitches++;
        }

        int totalProcesses = processes.size();
        double avgWaitingTime = (double) totalWaitingTime / totalProcesses;
        double avgTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;

        System.out.println("Average Waiting Time = " + avgWaitingTime);
        System.out.println("Average Turnaround Time = " + avgTurnaroundTime);
        System.out.println("CPU Finish Time = " + time);
        System.out.println("Total Context Switches = " + contextSwitches);
    }

    private static void RRscheduling(List<Process> processes, int timeQuantum) {
        int time = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int contextSwitches = 0;

        List<Process> readyQueue = new ArrayList<>(processes);
        List<Process> ioQueue = new ArrayList<>();

        while (!readyQueue.isEmpty() || !ioQueue.isEmpty()) {
            Process currentProcess = readyQueue.get(0);
            readyQueue.remove(0);

            
            int remainingBurst = currentProcess.cpuBursts.get(0);
            int cpuTime = Math.min(timeQuantum, remainingBurst);
            remainingBurst -= cpuTime;
            time += cpuTime;


         
            for (Process process : readyQueue) {
                totalWaitingTime += cpuTime;                     
            }
            
          
            for (Process process : ioQueue) {
                totalWaitingTime += cpuTime;
            }
            
           
            {   int waitingTime = Math.max(0, time - currentProcess.arrivalTime - currentProcess.cpuBursts.get(0));
                totalTurnaroundTime += waitingTime + currentProcess.cpuBursts.get(0);
            }
        
            contextSwitches++;
        }

        int totalProcesses = processes.size();
        double avgWaitingTime = (double) totalWaitingTime / totalProcesses;
        double avgTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;

        System.out.println("Average Waiting Time = " + avgWaitingTime);
        System.out.println("Average Turnaround Time = " + avgTurnaroundTime);
        System.out.println("CPU Finish Time = " + time);
        System.out.println("Total Context Switches = " + contextSwitches);
    }
    
  }





