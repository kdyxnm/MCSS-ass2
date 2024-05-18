# 1.How to use the model

## a. Run one simulation in the terminal

Acceptable parameters: 

+ int **intensity**; // [50, 100] step size 1

+ boolean **lift**; // true or false

+ double **hoursOfSleep**; // [0, 12] step size 0.5

+ int **daysBetweenWorkouts**; // [1, 30] step size 1

+ int **slowTwitchPercentage**; // [0, 100] step size 1

+ int **DaysToSimulate**; // [1, ] step size 1

```bash
javac Simulation.java

# java Simulation <intensity> <lift> <hoursOfSleep> <daysBetweenWorkouts> <slowTwitchPercentage> <DaysToSimulate>

java Simulation 95 true 8.0 5 50 730
```

## b. Run multiple simulations in the terminal with a bash script

```bash
chmod +x run_simulations.sh

./run_simulations.sh
```

You will get multiple csv files with the results of the simulations.

Change the content in the run_simulations.sh file to run the simulations you want.

## c. Plot the results in the terminal (used only for visualization)

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

# 2. How to run the experiments

## 1. Experiment: How each parameter influcnes muscle development over a year

First change directory to `first-experiment/scripts`

``` bash
cd first-experiment/scripts
```

This is where the scripts resides.

```bash
scripts
├── days_between_workouts.sh
├── intensity.sh
├── lift_various.sh
├── sleep_hours_various.sh
└── twitch_percentage_various.sh

1 directory, 5 files
```

You can run each bash script to run experiments. 

For instance, `./days_between_workouts.sh` will run the experiment observing different muscle mass values after 365 days with different workout gap days.

+ The data (.csv files) generated will be stored in `first-experiment/data_generated`

+ The result of each experiment will be created at `first-experiment/results`

Each script run would take no more than 10 seconds.
