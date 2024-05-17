import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Simulation class represents a simulation of muscle development.
 * It includes properties such as grid size, patches, muscle fibers,
 * workout intensity, sleep hours, and methods to perform daily activities,
 * lift weights, sleep, regulate hormones, and develop muscle.
 */
public class Simulation {
    public int GRID_SIZE = 17; // The size of the grid
    public Patch[][] patches; // The patches in the grid
    public MuscleFiber[][] muscleFibers; // The muscle fibers in the grid
    public int intensity; // The intensity of the workout
    public boolean lift; // Whether the person lifts or not
    public double hoursOfSleep; // The hours of sleep the person gets
    public int daysBetweenWorkouts; // The days between workouts
    public int slowTwitchPercentage; // The percentage of slow twitch muscles

    /**
     * Constructs a new Simulation with the given parameters.
     * @param intensity The intensity of the workout.
     * @param lift Whether the person lifts or not.
     * @param hoursOfSleep The hours of sleep the person gets.
     * @param daysBetweenWorkouts The days between workouts.
     * @param slowTwitchPercentage The percentage of slow twitch muscles.
     */
    public Simulation(
            int intensity,
            boolean lift,
            double hoursOfSleep,
            int daysBetweenWorkouts,
            int slowTwitchPercentage
    ) {
        this.intensity = intensity;
        this.lift = lift;
        this.hoursOfSleep = hoursOfSleep;
        this.daysBetweenWorkouts = daysBetweenWorkouts;
        this.slowTwitchPercentage = slowTwitchPercentage;

        // Initialize the grid of patches and muscle fibers
        patches = new Patch[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j] = new Patch();
            }
        }

        // Initialize the muscle fibers
        muscleFibers = new MuscleFiber[17][17];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                muscleFibers[i][j] = new MuscleFiber(slowTwitchPercentage);
            }
        }

    }

    /**
     * Returns the hours of sleep.
     * @return The hours of sleep.
     */
    public double getHoursOfSleep() {
        return hoursOfSleep;
    }

    /**
     * Returns the grid size.
     * @return The grid size.
     */
    public int getGridSize() {
        return GRID_SIZE;
    }

    /**
     * Returns the patches in the simulation.
     * @return The patches in the simulation.
     */
    public Patch[][] getPatches() {
        return patches;
    }

    /**
     * Update the hormones in the patches based on daily activities.
     */
    public void performDailyActivity() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j].updateHormonesFromDailyActivities(muscleFibers[i][j].getFiberSize());
            }
        }
    }


    /**
     * Lift weights and update the hormones in the patches.
     */
    public void liftWeights() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j]
                        .updateHormonesFromWeightLifting(
                                muscleFibers[i][j].getFiberSize(),
                                intensity
                        );
            }
        }
    }

    /**
     * Sleep and update the hormones in the patches.
     */
    public void sleep() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j].updateHormonesFromSleep(hoursOfSleep);
            }
        }
    }

    /**
     * Regulate the hormones in the patches.
     */
    public void regulateHormones() {
        // buffer matrix to store the diffused hormones
        Patch[][] bufferPatches = new Patch[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                bufferPatches[i][j] = new Patch(0.0, 0.0);
            }
        }

        // diffuse hormones
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j].diffuse(bufferPatches, i, j);
            }
        }

        // combine the remaining hormones with the diffused hormones
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j].setAnabolicHormone(
                        patches[i][j]
                                .getAnabolicHormone()
                                + bufferPatches[i][j].getAnabolicHormone()
                );
                patches[i][j].setCatabolicHormone(
                        patches[i][j].getCatabolicHormone()
                                + bufferPatches[i][j].getCatabolicHormone()
                );
                patches[i][j].balanceHormones();
            }
        }
    }

    /**
     * Develop the muscle fibers based on the hormones in the patches.
     */
    public void developMuscle() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                muscleFibers[i][j]
                        .developMuscle(
                                patches[i][j].getAnabolicHormone(),
                                patches[i][j].getCatabolicHormone()
                        );
            }
        }
    }

    /**
     * Returns the muscle mass in the simulation.
     * @return The muscle mass in the simulation.
     */
    public double muscleMass() {
        double mass = 0;
        for (int i = 0; i < patches.length; i++) {
            for (int j = 0; j < patches[0].length; j++) {
                mass += muscleFibers[i][j].getFiberSize();
            }
        }
        // Assume Muscle Mass in the NetLogo plot is calculated as:
        // sum of all fiber sizes / 100
        return mass / 100.0;
    }

    /**
     * Returns the average anabolic hormone level in the simulation.
     * @return The average anabolic hormone level in the simulation.
     */
    public double averageAnabolicHormone() {
        double total = 0;
        for (int i = 0; i < patches.length; i++) {
            for (int j = 0; j < patches[0].length; j++) {
                total += patches[i][j].getAnabolicHormone();
            }
        }
        return total / (patches.length * patches[0].length);
    }

    /**
     * Returns the average catabolic hormone level in the simulation.
     * @return The average catabolic hormone level in the simulation.
     */
    public double averageCatabolicHormone() {
        double total = 0;
        for (int i = 0; i < patches.length; i++) {
            for (int j = 0; j < patches[0].length; j++) {
                total += patches[i][j].getCatabolicHormone();
            }
        }
        return total / (patches.length * patches[0].length);
    }

    /**
     * The main method for the Simulation class.
     * It takes command line arguments for the simulation parameters, runs the simulation,
     * and writes the results to a CSV file.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        // Check if enough arguments are passed
        if (args.length < 6) {
            System.out.println("Usage: java Simulation " +
                    "<intensity> <lift> <hoursOfSleep> <daysBetweenWorkouts> " +
                    "<slowTwitchPercentage> <daysToSimulate>");
            return;
        }

        // Parse the command-line arguments
        int intensity = Integer.parseInt(args[0]);
        boolean lift = Boolean.parseBoolean(args[1]);
        double hoursOfSleep = Double.parseDouble(args[2]);
        int daysBetweenWorkouts = Integer.parseInt(args[3]);
        int slowTwitchPercentage = Integer.parseInt(args[4]);
        int days = Integer.parseInt(args[5]);

        // Create a new simulation
        Simulation simulation = new Simulation(
                intensity,
                lift,
                hoursOfSleep,
                daysBetweenWorkouts,
                slowTwitchPercentage
        );

        // Run the simulation for the specified number of days
        double[] muscleMass = new double[days + 1];
        double[] averageAnabolicHormone = new double[days + 1];
        double[] averageCatabolicHormone = new double[days + 1];

        // Initialize the first day
        muscleMass[0] = simulation.muscleMass();
        averageAnabolicHormone[0] = simulation.averageAnabolicHormone();
        averageCatabolicHormone[0] = simulation.averageCatabolicHormone();

        // Run the simulation for the specified number of days
        for (int i = 1; i <= days; i++) {
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
        }

        // Constructing the CSV filename based on parameters
        String sleepHoursFormatted = args[2].replace('.', 'n');
        String filename = String.format("%d_%s_%s_%d_%d_%d.csv", intensity, lift ?
                "true" : "false", sleepHoursFormatted, daysBetweenWorkouts,
                slowTwitchPercentage, days);

        // Write to CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Day,Muscle Mass,Anabolic Hormone,Catabolic Hormone");
            for (int i = 0; i <= days; i++) {
                writer.printf("%d,%.2f,%.2f,%.2f\n", i, muscleMass[i], averageAnabolicHormone[i],
                        averageCatabolicHormone[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

