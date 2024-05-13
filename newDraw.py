import pandas as pd
import matplotlib.pyplot as plt
import sys

def main(filename):
    try:
        df = pd.read_csv(filename)
    except FileNotFoundError:
        print(f"Error: The file '{filename}' does not exist.")
        sys.exit(1)
    except Exception as e:
        print(f"An error occurred: {e}")
        sys.exit(1)

    # Plotting
    plt.figure(figsize=(10, 6))  # 设置图形的尺寸

    # Muscle Development Plot
    plt.plot(df["Day"], df["Muscle Mass"], label="Muscle Mass", color='red')
    plt.title('Muscle Development Over Time')
    plt.xlabel('Day')
    plt.ylabel('Muscle Mass (units)')
    plt.grid(True)
    plt.legend()

    # Show the plot
    plt.tight_layout()  # 调整布局
    plt.show()

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python draw.py <filename>")
        sys.exit(1)  # Exit the script if no filename is provided

    # the second command-line argument is the filename
    filename = sys.argv[1]
    main(filename)