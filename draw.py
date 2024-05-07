"""
This script reads a CSV file containing data about muscle development,
 and hormone levels and plots the data in two subplots.

The expected CSV file should have the following columns:
- Day: The day number
- Muscle Mass: The muscle mass
- Anabolic Hormone: The level of anabolic hormone
- Catabolic Hormone: The level of catabolic hormone

Example:

Day,Muscle Mass,Anabolic Hormone,Catabolic Hormone
0,5.82,50.00,52.00
1,5.82,50.00,52.00
2,5.81,50.00,52.00
3,5.82,81.47,75.48
4,5.83,75.95,69.42
5,5.84,70.55,63.52
6,5.85,100.38,85.75
7,5.86,94.53,79.48
8,5.88,88.78,73.34
9,5.90,119.29,96.15
... (more rows)

"""
import pandas as pd
import matplotlib.pyplot as plt
import sys

# check if the filename was passed as a command-line argument
if len(sys.argv) < 2:
    print("Usage: python draw.py <filename>")
    sys.exit(1)  # Exit the script if no filename is provided

# first command-line argument is the script name, so the second one is the filename
filename = sys.argv[1]

df = pd.read_csv(filename)

# Plotting
fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(10, 8))

# Muscle Development Plot
ax1.plot(df["Day"], df["Muscle Mass"], label="Muscle Mass", color='red')
ax1.set_title('Muscle Development')
ax1.set_xlabel('Day')
ax1.set_ylabel('Muscle Mass')
ax1.grid(True)

# Hormones Plot
ax2.plot(df["Day"], df["Anabolic Hormone"], label="Anabolic", color='black')
ax2.plot(df["Day"], df["Catabolic Hormone"], label="Catabolic", color='yellow')
ax2.set_title('Hormones')
ax2.set_xlabel('Day')
ax2.set_ylabel('Hormone Level')
ax2.legend()
ax2.grid(True)

# Adjust layout to prevent overlap
plt.tight_layout()

# Show the plot
plt.show()
