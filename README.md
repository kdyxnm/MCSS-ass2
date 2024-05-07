# 1. Run one simulation in the terminal

     int intensity; // [50, 100] step size 1
     boolean lift; // true or false
     double hoursOfSleep; // [0, 12] step size 0.5
     int daysBetweenWorkouts; // [1, 30] step size 1
     int slowTwitchPercentage; // [0, 100] step size 1
     int DaysToSimulate; // [1, ] step size 1
```bash
javac Simulation.java

# java Simulation <intensity> <lift> <hoursOfSleep> <daysBetweenWorkouts> <slowTwitchPercentage> <DaysToSimulate>

java Simulation 95 true 8.0 5 50 730
```

# 2. Run multiple simulations in the terminal with a bash script

```bash
chmod +x run_simulations.sh

./run_simulations.sh
```

You will get multiple csv files with the results of the simulations.
 
Change the content in the run_simulations.sh file to run the simulations you want.


# 3. Plot the results in the terminal
```bash
# python draw.py <filename.csv>
python draw.py 95_true_8n0_5_50_730.csv
```
```bash
# If you got the error "ModuleNotFoundError: No module named 'matplotlib'", run the following command
pip install matplotlib
# If you got the error "ModuleNotFoundError: No module named 'pandas'", run the following command
pip install pandas
```

```bash
# If you got the error "command not found: python", try
python3 draw.py 95_true_8n0_5_50_730.csv
```