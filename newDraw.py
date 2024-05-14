import pandas as pd
import matplotlib.pyplot as plt
import sys

# Check if two filenames were passed as command-line arguments
if len(sys.argv) < 3:
    print("Usage: python draw.py <filename1> <filename2>")
    sys.exit(1)  # Exit the script if the filenames are not provided

# Command-line arguments for the filenames
filename1 = sys.argv[1]
filename2 = sys.argv[2]

# Load the data from CSV files into pandas DataFrames
df1 = pd.read_csv(filename1)
df2 = pd.read_csv(filename2)

# Plotting
plt.figure(figsize=(10, 5))

# Muscle Development Plot for the first file
plt.plot(df1["Day"], df1["Muscle Mass"], label=f"Muscle Mass (File 1: {filename1})", color='red')

# Muscle Development Plot for the second file
plt.plot(df2["Day"], df2["Muscle Mass"], label=f"Muscle Mass (File 2: {filename2})", color='blue')

# Setting titles and labels
plt.title('Comparison of Muscle Development')
plt.xlabel('Day')
plt.ylabel('Muscle Mass')
plt.legend()
plt.grid(True)

# Adjust layout to prevent overlap
plt.tight_layout()

# Show the plot
plt.show()
