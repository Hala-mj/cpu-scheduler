# CPU Scheduler – FCFS vs Round Robin

> A Java simulation of CPU scheduling that compares First-Come-First-Serve and Round Robin on average waiting time, turnaround time and context switches.

## Overview

An Operating Systems course project. The program reads a list of processes from a text file. Each process has an ID, an arrival time, and alternating CPU and I/O bursts. The program then simulates two scheduling algorithms:

- **FCFS (First-Come-First-Serve):** processes run in arrival order until their CPU bursts finish.
- **RR (Round Robin):** each process gets the CPU for at most one *time quantum* at a time.

For each algorithm it reports:

- Average waiting time
- Average turnaround time
- CPU finish time
- Total number of context switches

The project report ([`docs/OS_CPU_Scheduling_Report.pdf`](docs/OS_CPU_Scheduling_Report.pdf); student IDs removed) compared RR with quantum **q = 20 ms** and **q = 200 ms** against FCFS. Its finding was that Round Robin gave lower waiting and turnaround times than FCFS. With q = 200, RR behaved much like FCFS. With q = 20, the extra context switching raised waiting time for some processes.

## Tech Stack

- **Java** (no external dependencies)
- NetBeans Ant project

## How to Run

```bash
javac -d out src/cpuscheduler/CPUScheduler.java

# default: data/CPU-NewData.txt with quantum = 20 ms
java -cp out cpuscheduler.CPUScheduler

# custom data file and time quantum
java -cp out cpuscheduler.CPUScheduler data/CPU-NewData.txt 200
```

In NetBeans, open the folder as a project and run it. The main class is `cpuscheduler.CPUScheduler`, and arguments can be set under *Project Properties → Run*.

### Input format
```
<pid> <arrivalTime> <cpuBurst1> <ioBurst1> <cpuBurst2> ... <cpuBurstN>
```
See [`data/README.md`](data/README.md). The included `CPU-NewData.txt` is a small sample. The original course dataset was not part of the archive.

## Example Output

```
First-Come-First-Serve (FCFS) Scheduling:
Average Waiting Time = 532.0
Average Turnaround Time = 788.0
CPU Finish Time = 1280
Total Context Switches = 5

Round Robin (RR) Scheduling (Time Quantum = 20ms):
...
```

## Notes

The Round Robin routine is a simplified model. Each process runs for one quantum of its first CPU burst and is not re-queued, and the I/O queue is not used. Extending it to re-queue unfinished processes and to model I/O bursts would make it a full RR simulation.

## Project Structure

```
cpu-scheduler/
├── src/cpuscheduler/CPUScheduler.java   # Process model, file parsing, FCFS + RR
├── data/CPU-NewData.txt                 # sample input
├── docs/OS_CPU_Scheduling_Report.pdf
├── nbproject/  build.xml  manifest.mf
└── LICENSE
```

## Credits

Team project for the Operating Systems course at Umm Al-Qura University (Group No. 2).
Team members (from the course report): Jana Abdulrahman Alqurashi, Hala Mohammed Jalal, and Azizah Hamed Alamari.

## License

[MIT](LICENSE) © 2025 Hala Jalal
