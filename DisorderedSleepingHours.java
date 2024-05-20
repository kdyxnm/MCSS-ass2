import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * The DisorderedSleepingHours class extends the Simulation class and represents a simulation
 * where the sleep hours are irregular.
 */
public class DisorderedSleepingHours extends Simulation {
    private boolean irregularSleep; // Indicates if sleep is irregular
    private Random random; // Random number generator
    public ArrayList<Double> dailyAdjustedSleepHours; // Stores daily adjusted sleep hours

    /**
     * Constructs a new DisorderedSleepingHours with the given parameters.
     * @param intensity The intensity of the workout.
     * @param lift Whether the person lifts or not.
     * @param hoursOfSleep The hours of sleep the person gets.
     * @param daysBetweenWorkouts The days between workouts.
     * @param slowTwitchPercentage The percentage of slow twitch muscles.
     * @param irregularSleep Whether the sleep is irregular.
     */
    public DisorderedSleepingHours(
            int intensity,
            boolean lift,
            double hoursOfSleep,
            int daysBetweenWorkouts,
            int slowTwitchPercentage,
            boolean irregularSleep
    ) {
        super(intensity, lift, hoursOfSleep, daysBetweenWorkouts, slowTwitchPercentage);
        this.irregularSleep = irregularSleep;
        this.random = new Random();
        this.dailyAdjustedSleepHours = new ArrayList<>();
    }

    /**
     * Overrides the sleep method from the Simulation class.
     * Adjusts sleep hours if sleep is irregular.
     */
    @Override
    public void sleep() {
        double adjustedSleep = getHoursOfSleep();
        if (irregularSleep) {
            // Generate a random value between -5 and 5
            double randomAdjustment = -5 + random.nextDouble() * 10;
            adjustedSleep += randomAdjustment;
            // Ensure that sleep hours do not go below 0
            adjustedSleep = Math.max(adjustedSleep, 0);
        }
        dailyAdjustedSleepHours.add(adjustedSleep); // Record adjusted sleep hours

        // Adjust hormone levels based on sleep quality
        double sleepFactor = adjustedSleep / getHoursOfSleep();
        for (int i = 0; i < getGridSize(); i++) {
            for (int j = 0; j < getGridSize(); j++) {
                double currentAnabolic = getPatches()[i][j].getAnabolicHormone();
                double currentCatabolic = getPatches()[i][j].getCatabolicHormone();
                getPatches()[i][j].setAnabolicHormone(currentAnabolic *
                        (1 + 0.1 * (sleepFactor - 1)));
                getPatches()[i][j].setCatabolicHormone(currentCatabolic *
                        (1 - 0.1 * (sleepFactor - 1)));
                getPatches()[i][j].updateHormonesFromSleep(adjustedSleep);
            }
        }
    }

    /**
     * The main method for the DisorderedSleepingHours class.
     * It takes command line arguments for the simulation parameters, runs the simulation,
     * and writes the results to a CSV file.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 7) {
            System.out.println("Usage: java DisorderedSleepingHours " +
                    "<intensity> <lift> <hoursOfSleep> " +
                    "<daysBetweenWorkouts> <slowTwitchPercentage> " +
                    "<daysToSimulate> <irregularSleep>");
            return;
        }

        int intensity = Integer.parseInt(args[0]);
        boolean lift = Boolean.parseBoolean(args[1]);
        double hoursOfSleep = Double.parseDouble(args[2]);
        int daysBetweenWorkouts = Integer.parseInt(args[3]);
        int slowTwitchPercentage = Integer.parseInt(args[4]);
        int days = Integer.parseInt(args[5]);
        boolean irregularSleep = Boolean.parseBoolean(args[6]);

        DisorderedSleepingHours simulation = new DisorderedSleepingHours(
                intensity,
                lift,
                hoursOfSleep,
                daysBetweenWorkouts,
                slowTwitchPercentage,
                irregularSleep
        );

        double[] muscleMass = new double[days + 1];
        double[] averageAnabolicHormone = new double[days + 1];
        double[] averageCatabolicHormone = new double[days + 1];
        double[] sleepingHours = new double[days + 1];

        simulation.sleep(); // Perform initial sleep

        muscleMass[0] = simulation.muscleMass();
        averageAnabolicHormone[0] = simulation.averageAnabolicHormone();
        averageCatabolicHormone[0] = simulation.averageCatabolicHormone();
        sleepingHours[0] = simulation.getHoursOfSleep();

        // Run the simulation for the specified number of days
        for (int i = 0; i < days; i++) {
            simulation.performDailyActivity();
            if (lift && i % daysBetweenWorkouts == 0) {
                simulation.liftWeights();
            }
            simulation.sleep();
            simulation.regulateHormones();
            simulation.developMuscle();

            muscleMass[i] = simulation.muscleMass();
            averageAnabolicHormone[i] = simulation.averageAnabolicHormone();
            averageCatabolicHormone[i] = simulation.averageCatabolicHormone();
            sleepingHours[i] = simulation.dailyAdjustedSleepHours.get(i);
        }

        String directoryPath = "DisorderedSleepingExperiments";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        String sleepHoursFormatted = String.format("%.1f", hoursOfSleep).
                replace('.', 'n');
        String filename = String.format("%s/%d_%s_%s_%d_%d_%d_%s.csv",
                directoryPath,
                intensity,
                lift ? "true" : "false",
                sleepHoursFormatted,
                daysBetweenWorkouts,
                slowTwitchPercentage,
                days,
                irregularSleep ? "true" : "false");
        // Write the results to a CSV file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Day,Sleeping Hours,Muscle Mass,Anabolic Hormone,Catabolic Hormone");
            for (int i = 0; i <= days; i++) {
                writer.printf("%d,%.2f,%.2f,%.2f,%.2f\n", i, sleepingHours[i], muscleMass[i],
                        averageAnabolicHormone[i], averageCatabolicHormone[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}