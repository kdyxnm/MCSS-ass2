import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FindBestComboSimulation {
    private int GRID_SIZE = 17;
    private Patch[][] patches;
    private MuscleFiber[][] muscleFibers;
    private int intensity; // [50, 100] step size 1
    private boolean lift;
    private double hoursOfSleep; // [0, 12] step size 0.5
    private int daysBetweenWorkouts; // [1, 30] step size 1
    private int slowTwitchPercentage; // [0, 100] step size 1

    private static final int DAYS = 365;
    private static final boolean INIT_LIFT = false;
    private static final double INIT_HOURSOFSLEEP = 4.0;
    private static final int INIT_DAYSBETWEENWORKOUTS = 1;
    private static final int INIT_INTENSITY = 50;
    private static final double FINAL_HOURSOFSLEEP = 12.0;
    private static final int FINAL_DAYSBETWEENWORKOUTS = 30;
    private static final int FINAL_INTENSITY = 100;



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
        // Assume Muscle Mass in the NetLogo plot is calculated as:
        // sum of all fiber sizes / 100
        return mass / 100.0;
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
    
    public static Data simulate(int intensity, 
                                boolean lift, double hoursOfSleep, 
                                int daysBetweenWorkouts, 
                                int slowTwitchPercentage, 
                                int days, 
                                Data bestData,
                                Logger logger) {
        double totalMuscleMass = 0.0;
        double totalAnabolicHormone = 0.0;
        double totalCatabolicHormone = 0.0;
        for (int j = 0; j < 10; j++) {
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
        Data curData =  new Data(
                intensity,
                lift,
                hoursOfSleep,
                daysBetweenWorkouts,
                slowTwitchPercentage,
                totalMuscleMass / 10,
                totalAnabolicHormone / 10,
                totalCatabolicHormone / 10
        );
        logger.info("Current Data: " + curData.toString());
        if (bestData == null || curData.getMuscleMass() > bestData.getMuscleMass()) {
            bestData = curData;
        }
        return bestData;
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        int slowTwitchPercentage = Integer.parseInt(args[0]);
        Data bestData = null;
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {
            File directory = new File("BestComboLog");
            if (! directory.exists()){
                directory.mkdir();
            }

            fh = new FileHandler("BestComboLog/BestCombo_" + slowTwitchPercentage + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int intensity = INIT_INTENSITY; intensity <= FINAL_INTENSITY; intensity++) {
            for (double hoursOfSleep = INIT_HOURSOFSLEEP; hoursOfSleep <= FINAL_HOURSOFSLEEP; hoursOfSleep += 0.5) {
                for (int daysBetweenWorkouts = INIT_DAYSBETWEENWORKOUTS; daysBetweenWorkouts <= FINAL_DAYSBETWEENWORKOUTS; daysBetweenWorkouts++) {
                    bestData = simulate(intensity, false, hoursOfSleep, daysBetweenWorkouts, slowTwitchPercentage, DAYS, bestData, logger);
                    bestData = simulate(intensity, true, hoursOfSleep, daysBetweenWorkouts, slowTwitchPercentage, DAYS, bestData, logger);
                }
            }
        }
        logger.info("Best Data: " + bestData.toString());
    }
}

