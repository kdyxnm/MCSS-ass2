import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Simulation {
    private int GRID_SIZE = 17;
    private Patch[][] patches;
    private MuscleFiber[][] muscleFibers;
    private int intensity; // [50, 100] step size 1
    private boolean lift;
    private double hoursOfSleep; // [0, 12] step size 0.5
    private int daysBetweenWorkouts; // [1, 30] step size 1
    private int slowTwitchPercentage; // [0, 100] step size 1

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

        patches = new Patch[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j] = new Patch();
            }
        }

        muscleFibers = new MuscleFiber[17][17];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                muscleFibers[i][j] = new MuscleFiber(slowTwitchPercentage);
            }
        }

    }

    public void performDailyActivity() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j].updateHormonesFromDailyActivities(muscleFibers[i][j].getFiberSize());
            }
        }
    }

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

    public void sleep() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                patches[i][j].updateHormonesFromSleep(hoursOfSleep);
            }
        }
    }

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

    public double muscleMass() {
        double mass = 0;
        for (int i = 0; i < patches.length; i++) {
            for (int j = 0; j < patches[0].length; j++) {
                mass += muscleFibers[i][j].getFiberSize();
            }
        }
        return mass / (patches.length * patches[0].length);
    }

    public double averageAnabolicHormone() {
        double total = 0;
        for (int i = 0; i < patches.length; i++) {
            for (int j = 0; j < patches[0].length; j++) {
                total += patches[i][j].getAnabolicHormone();
            }
        }
        return total / (patches.length * patches[0].length);
    }

    public double averageCatabolicHormone() {
        double total = 0;
        for (int i = 0; i < patches.length; i++) {
            for (int j = 0; j < patches[0].length; j++) {
                total += patches[i][j].getCatabolicHormone();
            }
        }
        return total / (patches.length * patches[0].length);
    }

    public static void main(String[] args) {
        // Check if enough arguments are passed
        if (args.length < 6) {
            System.out.println("Usage: java Simulation <intensity> <lift> <hoursOfSleep> <daysBetweenWorkouts> <slowTwitchPercentage> <daysToSimulate>");
            return;
        }

        // Parse the command-line arguments
        int intensity = Integer.parseInt(args[0]);
        boolean lift = Boolean.parseBoolean(args[1]);
        double hoursOfSleep = Double.parseDouble(args[2]);
        int daysBetweenWorkouts = Integer.parseInt(args[3]);
        int slowTwitchPercentage = Integer.parseInt(args[4]);
        int days = Integer.parseInt(args[5]);


        Simulation simulation = new Simulation(
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
        String filename = String.format("%d_%s_%s_%d_%d_%d.csv", intensity, lift ? "true" : "false", sleepHoursFormatted, daysBetweenWorkouts, slowTwitchPercentage, days);

        // Write to CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Day,Muscle Mass,Anabolic Hormone,Catabolic Hormone");
            for (int i = 0; i <= days; i++) {
                writer.printf("%d,%.2f,%.2f,%.2f\n", i, muscleMass[i], averageAnabolicHormone[i], averageCatabolicHormone[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

