import pandas as pd
import matplotlib.pyplot as plt

# Load the data from the CSV file
df = pd.read_csv("simulation_results.csv")

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
