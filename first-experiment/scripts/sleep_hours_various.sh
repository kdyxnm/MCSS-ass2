#!/bin/bash

# Navigate to the base project directory (assumes the script is run from the 'scripts' directory)
cd "$(dirname "$0")/../.."

# Create the 'sleep_various' directory within 'data_generated'
mkdir -p first-experiment/data_generated/sleep_various

# Ensure the 'results' directory exists
mkdir -p first-experiment/results

# File to store results
result_file="first-experiment/results/sleep_various_results.csv"

# Initialize result file with headers
echo "Sleep Hours,Mean Muscle Mass" > "$result_file"

# Range and step of sleep hours
start=4
end=12
step=1

# Number of simulations to average
num_simulations=10

# Run the simulation for each sleep hour setting and extract results
for (( h=$start; h<=$end; h+=$step ))
do
    total_mass=0
    
    for (( j=1; j<=num_simulations; j++ ))
    do
        # Run the simulation with the current sleep hours
        java Simulation 95 true "$h".0 5 50 365
        
        # Define the expected filename based on the current directory output
        output_file="95_true_${h}n0_5_50_365.csv"
        
        # Move the generated file to the correct directory
        mv "$output_file" "first-experiment/data_generated/sleep_various/$output_file"
        
        # Generate the filename based on parameters after moving
        filename="first-experiment/data_generated/sleep_various/$output_file"
        
        # Extract the last line from the CSV and extract the Muscle Mass value
        muscle_mass=$(tail -1 "$filename" | cut -d',' -f2)
        
        # Accumulate muscle mass for averaging
        total_mass=$(echo "$total_mass + $muscle_mass" | bc)
    done

    # Calculate mean muscle mass
    mean_mass=$(echo "scale=2; $total_mass / $num_simulations" | bc)
    
    # Write the sleep hours and mean muscle mass to the results file
    echo "$h,$mean_mass" >> "$result_file"
done

echo "Script completed. Results are stored in $result_file"

