#!/bin/bash

# Navigate to the base project directory (the script is run from the 'scripts' directory)
cd "$(dirname "$0")/../.."

# Create the 'workouts_various' directory within 'data_generated'
mkdir -p first-experiment/data_generated/workouts_various

# File to store results
result_file="first-experiment/results/workouts_various_results.csv"

# Initialize result file with headers
echo "Days Between Workouts,Mean Muscle Mass" > "$result_file"

# Define array of days between workouts including ranges and specific values
days_between_workouts=(1 2 3 4 5 6 7 10 15 20 30)

# Number of simulations to average
num_simulations=10

# Run the simulation for each value of days between workouts
for days in "${days_between_workouts[@]}"
do
    total_mass=0
    
    for (( j=1; j<=num_simulations; j++ ))
    do
        # Run the simulation with the current days between workouts
        java Simulation 95 true 8.0 "$days" 50 365
        
        # Define the expected filename based on the current directory output
        output_file="95_true_8n0_${days}_50_365.csv"
        
        # Move the generated file to the correct directory
        mv "$output_file" "first-experiment/data_generated/workouts_various/$output_file"
        
        # Generate the filename based on parameters after moving
        filename="first-experiment/data_generated/workouts_various/$output_file"
        
        # Extract the last line from the CSV and extract the Muscle Mass value
        muscle_mass=$(tail -1 "$filename" | cut -d',' -f2)
        
        # Accumulate muscle mass for averaging
        total_mass=$(echo "$total_mass + $muscle_mass" | bc)
    done

    # Calculate mean muscle mass
    mean_mass=$(echo "scale=2; $total_mass / $num_simulations" | bc)
    
    # Write the days between workouts and mean muscle mass to the results file
    echo "$days,$mean_mass" >> "$result_file"
done

echo "Script completed. Results are stored in $result_file"

