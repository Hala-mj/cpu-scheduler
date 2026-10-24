# Input format

Each line describes one process, with values separated by **single spaces**:

```
<pid> <arrivalTime> <cpuBurst1> <ioBurst1> <cpuBurst2> <ioBurst2> ... <cpuBurstN>
```

Values after the arrival time alternate between CPU bursts and I/O bursts, starting with a CPU burst. All times are in milliseconds.

`CPU-NewData.txt` is a **small sample file** for trying the program. The original course dataset was not included in the project archive.
