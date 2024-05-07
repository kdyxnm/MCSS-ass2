# 1. Run one simulation in the terminal
```bash
javac Simulation.java

#     int intensity; // [50, 100] step size 1
#     boolean lift; // true or false
#     double hoursOfSleep; // [0, 12] step size 0.5
#     int daysBetweenWorkouts; // [1, 30] step size 1
#     int slowTwitchPercentage; // [0, 100] step size 1
#     int DaysToSimulate; // [1, ] step size 1
# java Simulation <intensity> <lift> <hoursOfSleep> <daysBetweenWorkouts> <slowTwitchPercentage> <DaysToSimulate>

java Simulation 95 true 8.5 5 50 365
```

# 2. Run multiple simulations in the terminal with a bash script

```bash
chmod +x run_simulations.sh

./run_simulations.sh
```
 
Change the content in the run_simulations.sh file to run the simulations you want.


# 3. Plot the results in the terminal
```bash
python draw.py <filename.csv>

# If you got the error "ModuleNotFoundError: No module named 'matplotlib'", run the following command
pip install matplotlib
# If you got the error "ModuleNotFoundError: No module named 'pandas'", run the following command
pip install pandas

# If you got the error "command not found: python", try
python3 draw.py <filename.csv>
```