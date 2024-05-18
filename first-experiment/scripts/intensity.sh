#!/bin/bash

# Navigate to the base project directory (assumes the script is run from the 'scripts' directory)
cd "$(dirname "$0")/../.."

# Create the 'intensity_various' directory within 'data_generated'
mkdir -p first-experiment/data_generated/intensity_various

# File to store results
result_file="first-experiment/results/intensity_various_results.csv"

# Initialize result file with headers
echo "Intensity,Mean Muscle Mass" > "$result_file"

# Range and step of intensity values
start=50
end=100
step=5

# Number of simulations to average
num_simulations=10

# Run the simulation for each intensity level and extract results
for (( i=$start; i<=$end; i+=$step ))
do
    total_mass=0
    
    for (( j=1; j<=num_simulations; j++ ))
    do
        # Run the simulation with the current intensity
        java Simulation "$i" true 8.0 5 50 365
        
        # Define the expected filename based on the current directory output
        output_file="${i}_true_8n0_5_50_365.csv"
        
        # Move the generated file to the correct directory
        mv "$output_file" "first-experiment/data_generated/intensity_various/$output_file"
        
        # Generate the filename based on parameters after moving
        filename="first-experiment/data_generated/intensity_various/$output_file"
        
        # Extract the last line from the CSV and extract the Muscle Mass value
        muscle_mass=$(tail -1 "$filename" | cut -d',' -f2)
        
        # Accumulate muscle mass for averaging
        total_mass=$(echo "$total_mass + $muscle_mass" | bc)
    done

    # Calculate mean muscle mass
    mean_mass=$(echo "scale=2; $total_mass / $num_simulations" | bc)
    
    # Write the intensity and mean muscle mass to the results file
    echo "$i,$mean_mass" >> "$result_file"
done

echo "Script completed. Results are stored in $result_file"

