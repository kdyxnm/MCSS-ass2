#!/bin/bash

# make sure to run "chmod +x run_simulations.sh" to make this file executable

# command to run this file: ./run_simulations.sh

# Compile the Java program
javac DisorderedSleepingHours.java


# Run simulations with specified parameters
#  "Running simulation with Intensity: 95, Lift: true, Sleep Hours: 8.5, Days Between Workouts: 5, Twitch Percentage: 50, Days to Simulate: 365"
java DisorderedSleepingHours 95 True 8.0 2 50 365 True
java DisorderedSleepingHours 95 True 8.0 2 50 365 False

java DisorderedSleepingHours 50 True 8.0 2 50 365 True
java DisorderedSleepingHours 50 True 8.0 2 50 365 False


# Add more parameter sets as desired
