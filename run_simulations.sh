#!/bin/bash

# make sure to run "chmod +x run_simulations.sh" to make this file executable

# command to run this file: ./run_simulations.sh

# Compile the Java program
javac Main.java

# Run simulations with specified parameters
#  "Running simulation with Intensity: 95, Lift: true, Sleep Hours: 8.5, Days Between Workouts: 5, Twitch Percentage: 50, Days to Simulate: 365"

java Main 95 true 8.0 3 50 365

java Main 95 true 8.0 4 50 365

java Main 95 true 8.0 5 50 365

# Add more parameter sets as desired
