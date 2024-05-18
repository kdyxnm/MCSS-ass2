#!/bin/bash

# Navigate to the base project directory (assumes the script is run from the 'scripts' directory)
cd "$(dirname "$0")/../.."

# Create the 'twitch_various' directory within 'data_generated'
mkdir -p first-experiment/data_generated/twitch_various

# File to store results
result_file="first-experiment/results/twitch_various_results.csv"

# Initialize result file with headers
echo "Twitch Percentage,Mean Muscle Mass" > "$result_file"

# Range and step of twitch percentages
start=0
end=100
step=10

# Number of simulations to average
num_simulations=10

# Run the simulation for each twitch percentage level and extract results
for (( t=$start; t<=$end; t+=$step ))
do
    total_mass=0
    
    for (( j=1; j<=num_simulations; j++ ))
    do
        # Run the simulation with the current twitch percentage
        java Simulation 95 true 8.0 5 "$t" 365
        
        # Define the expected filename based on the current directory output
        output_file="95_true_8n0_5_${t}_365.csv"
        
        # Move the generated file to the correct directory
        mv "$output_file" "first-experiment/data_generated/twitch_various/$output_file"
        
        # Generate the filename based on parameters after moving
        filename="first-experiment/data_generated/twitch_various/$output_file"
        
        # Extract the last line from the CSV and extract the Muscle Mass value
        muscle_mass=$(tail -1 "$filename" | cut -d',' -f2)
        
        # Accumulate muscle mass for averaging
        total_mass=$(echo "$total_mass + $muscle_mass" | bc)
    done

    # Calculate mean muscle mass
    mean_mass=$(echo "scale=2; $total_mass / $num_simulations" | bc)
    
    # Write the twitch percentage and mean muscle mass to the results file
    echo "$t,$mean_mass" >> "$result_file"
done

echo "Script completed. Results are stored in $result_file"

