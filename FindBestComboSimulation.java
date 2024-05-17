import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The FindBestComboSimulation class represents a simulation to find the best combination
 * of workout parameters to maximize muscle mass.
 */
public class FindBestComboSimulation {
    private static final int GRID_SIZE = 17; // The size of the grid
    private Patch[][] patches; // The patches in the grid
    private MuscleFiber[][] muscleFibers; // The muscle fibers in the grid
    private int intensity; // The intensity of the workout
    private boolean lift; // Whether the person lifts or not
    private double hoursOfSleep; // The hours of sleep the person gets
    private int daysBetweenWorkouts; // The days between workouts
    private int slowTwitchPercentage; // The percentage of slow twitch muscles

    // Constants for the simulation parameters
    private static final boolean INIT_LIFT = false;
    private static final double INIT_HOURSOFSLEEP = 4.0;
    private static final int INIT_DAYSBETWEENWORKOUTS = 1;
    private static final int INIT_INTENSITY = 50;
    private static final double FINAL_HOURSOFSLEEP = 12.0;
    private static final int FINAL_DAYSBETWEENWORKOUTS = 30;
    private static final int FINAL_INTENSITY = 100;


    /**
     * Constructs a new FindBestComboSimulation with the given parameters.
     * @param intensity The intensity of the workout.
     * @param lift Whether the person lifts or not.
     * @param hoursOfSleep The hours of sleep the person gets.
     * @param daysBetweenWorkouts The days between workouts.
     * @param slowTwitchPercentage The percentage of slow twitch muscles.
     */
    public FindBestComboSimulation(
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

        // Initialize the patches and muscle fibers
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
     * Performs the daily activity for the simulation.
     */
    public void performDailyActivity() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j].updateHormonesFromDailyActivities(muscleFibers[i][j].getFiberSize());
            }
        }
    }

    /**
     * Lifts weights for the simulation.
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
     * Sleeps for the simulation.
     */
    public void sleep() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j].updateHormonesFromSleep(hoursOfSleep);
            }
        }
    }

    /**
     * Regulates the hormones for the simulation.
     */
    public void regulateHormones() {
        // buffer matrix to store the diffused hormones
        Patch[][] bufferPatches = new Patch[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                bufferPatches[i][j] = new Patch(0.0, 0.0);
            }
        }

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
     * Develops muscle for the simulation.
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
     * Returns the muscle mass for the simulation.
     * @return The muscle mass for the simulation.
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
     * Returns the average anabolic hormone for the simulation.
     * @return The average anabolic hormone for the simulation.
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
     * Returns the average catabolic hormone for the simulation.
     * @return The average catabolic hormone for the simulation.
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
     * Simulates the given parameters and returns the best data.
     * @param intensity The intensity of the workout.
     * @param lift Whether the person lifts or not.
     * @param hoursOfSleep The hours of sleep the person gets.
     * @param daysBetweenWorkouts The days between workouts.
     * @param slowTwitchPercentage The percentage of slow twitch muscles.
     * @param days The number of days to simulate.
     * @param max_round The number of rounds to simulate.
     * @param bestData The best data found so far.
     * @param logger The logger to log the results.
     * @return The best data found.
     */
    public static Data simulate(int intensity,
                                boolean lift, double hoursOfSleep,
                                int daysBetweenWorkouts,
                                int slowTwitchPercentage,
                                int days,
                                int max_round,
                                Data bestData,
                                Logger logger) {
        double totalMuscleMass = 0.0;
        double totalAnabolicHormone = 0.0;
        double totalCatabolicHormone = 0.0;
        for (int j = 0; j < max_round; j++) {
            FindBestComboSimulation simulation = new FindBestComboSimulation(
                    intensity,
                    lift,
                    hoursOfSleep,
                    daysBetweenWorkouts,
                    slowTwitchPercentage
            );
            double[] muscleMass = new double[days + 1];
            double[] averageAnabolicHormone = new double[days + 1];
            double[] averageCatabolicHormone = new double[days + 1];

            muscleMass[0] = simulation.muscleMass();
            averageAnabolicHormone[0] = simulation.averageAnabolicHormone();
            averageCatabolicHormone[0] = simulation.averageCatabolicHormone();

            // Run the simulation for the given number of days
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

            totalMuscleMass += muscleMass[days];
            totalAnabolicHormone += averageAnabolicHormone[days];
            totalCatabolicHormone += averageCatabolicHormone[days];
        }
        // Calculate the average muscle mass, anabolic hormone, and catabolic hormone
        Data curData =  new Data(
                intensity,
                lift,
                hoursOfSleep,
                daysBetweenWorkouts,
                slowTwitchPercentage,
                totalMuscleMass / days,
                totalAnabolicHormone / 10,
                totalCatabolicHormone / 10
        );
        logger.info("Current Data: " + curData.toString());
        if (bestData == null || curData.getMuscleMass() > bestData.getMuscleMass()) {
            bestData = curData;
        }
        return bestData;
    }

    /**
     * The main method for the FindBestComboSimulation class.
     * It takes command line arguments for the simulation parameters, runs the simulation,
     * and writes the results to a log file.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        int slowTwitchPercentage = Integer.parseInt(args[0]);
        int days = Integer.parseInt(args[1]);
        int max_round = Integer.parseInt(args[2]);
        Data bestData = null;
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        // Create the log file
        try {
            File directory = new File("BestComboLog");
            if (! directory.exists()){
                directory.mkdir();
            }

            fh = new FileHandler("BestComboLog/BestCombo_" +
                    slowTwitchPercentage + "_" + days + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Run the simulation for all possible combinations of parameters
        for (int intensity = INIT_INTENSITY; intensity <= FINAL_INTENSITY; intensity++) {
            for (double hoursOfSleep = INIT_HOURSOFSLEEP;
                 hoursOfSleep <= FINAL_HOURSOFSLEEP; hoursOfSleep += 0.5) {
                for (int daysBetweenWorkouts = INIT_DAYSBETWEENWORKOUTS;
                     daysBetweenWorkouts <= FINAL_DAYSBETWEENWORKOUTS; daysBetweenWorkouts++) {
                    bestData = simulate(intensity, false, hoursOfSleep,
                            daysBetweenWorkouts, slowTwitchPercentage, days,
                            max_round, bestData, logger);
                    bestData = simulate(intensity, true, hoursOfSleep,
                            daysBetweenWorkouts, slowTwitchPercentage, days,
                            max_round, bestData, logger);
                }
            }
        }
        logger.info("Best Data: " + bestData.toString());
    }
}

