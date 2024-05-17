/**
 * The Patch class represents a patch of muscle fibers in the body.
 * It includes properties such as anabolic and catabolic hormone levels,
 * and methods to update and regulate these levels.
 */
public class Patch {
    private double anabolicHormone; // The level of anabolic hormones
    private double catabolicHormone; // The level of catabolic hormones

    // Constants for hormone regulation
    private static final double DIFFUSE_RATE = 0.75;
    private static final double ANABOLIC_HORMONE_MAX = 200.0;
    private static final double ANABOLIC_HORMONE_MIN = 50.0;
    private static final double CATABOLIC_HORMONE_MAX = 250.0;
    private static final double CATABOLIC_HORMONE_MIN = 52.0;

    /**
     * Default constructor for the Patch class.
     * Initializes anabolic and catabolic hormone levels to their default starting values.
     */
    public Patch() {
        this.anabolicHormone = 50.0;  // default starting value
        this.catabolicHormone = 52.0; // default starting value
        // No need to regulate hormones here, since they are already within the allowed limits
        //regulateHormones();
    }

    /**
     * Constructs a new Patch with the given anabolic and catabolic hormone levels.
     * @param anabolicHormone The level of anabolic hormones.
     * @param catabolicHormone The level of catabolic hormones.
     */
    public Patch(double anabolicHormone, double catabolicHormone) {
        this.anabolicHormone = anabolicHormone;
        this.catabolicHormone = catabolicHormone;
    }

    /**
     * Diffuses hormones to the neighboring patches in the grid.
     * @param grid The grid of patches.
     * @param x The x-coordinate of the current patch in the grid.
     * @param y The y-coordinate of the current patch in the grid.
     */
    public void diffuse(Patch[][] grid, int x, int y) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        double anabolicShare = (anabolicHormone * DIFFUSE_RATE) / 8.0;
        double catabolicShare = (catabolicHormone * DIFFUSE_RATE) / 8.0;

        double anabolicRemaining = anabolicHormone;
        double catabolicRemaining = catabolicHormone;

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // Check if the neighboring patch is within the grid
            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length) {
                grid[nx][ny].receiveHormones(anabolicShare, catabolicShare);
                anabolicRemaining -= anabolicShare;
                catabolicRemaining -= catabolicShare;
            }
        }

        // Set the remaining hormone levels back to the patch
        this.anabolicHormone = anabolicRemaining;
        this.catabolicHormone = catabolicRemaining;
    }

    /**
     * Updates hormone levels based on daily activities.
     * @param fiberSize The size of the muscle fiber.
     */
    public void updateHormonesFromDailyActivities(double fiberSize) {
        double logFiberSize = Math.log10(fiberSize);
        anabolicHormone += 2.5 * logFiberSize;
        catabolicHormone += 2.0 * logFiberSize;
    }

    /**
     * Updates hormone levels based on weight lifting.
     * @param fiberSize The size of the muscle fiber.
     * @param intensity The intensity of the workout.
     */
    public void updateHormonesFromWeightLifting(double fiberSize, int intensity) {
        double probability = Math.pow(intensity / 100.0, 2);
        if (Math.random() * 1.0 < probability) {
            double logFiberSize = Math.log10(fiberSize);
            anabolicHormone += logFiberSize * 55.0;
            catabolicHormone += logFiberSize * 44.0;
        }
    }

    /**
     * Updates hormone levels based on sleep.
     * @param hoursOfSleep The hours of sleep.
     */
    public void updateHormonesFromSleep(double hoursOfSleep) {
        anabolicHormone -= 0.48 * Math.log10(anabolicHormone) * hoursOfSleep;
        catabolicHormone -= 0.5 * Math.log10(catabolicHormone) * hoursOfSleep;
    }

    /**
     * Balances hormone levels to be within the allowed limits.
     */
    public void balanceHormones() {
        anabolicHormone = Math.max(ANABOLIC_HORMONE_MIN,
                Math.min(this.anabolicHormone, ANABOLIC_HORMONE_MAX));
        catabolicHormone = Math.max(CATABOLIC_HORMONE_MIN,
                Math.min(this.catabolicHormone, CATABOLIC_HORMONE_MAX));
    }

    /**
     * Receives hormones from neighboring patches.
     * @param anabolic The amount of anabolic hormones received.
     * @param catabolic The amount of catabolic hormones received.
     */
    public void receiveHormones(double anabolic, double catabolic) {
        this.anabolicHormone += anabolic;
        this.catabolicHormone += catabolic;
    }

    /**
     * Returns the level of anabolic hormones.
     * @return The level of anabolic hormones.
     */
    public double getAnabolicHormone() {
        return anabolicHormone;
    }

    /**
     * Returns the level of catabolic hormones.
     * @return The level of catabolic hormones.
     */
    public double getCatabolicHormone() {
        return catabolicHormone;
    }

    /**
     * Sets the level of anabolic hormones.
     * @param v The new level of anabolic hormones.
     */
    public void setAnabolicHormone(double v) {
        this.anabolicHormone = v;
    }

    /**
     * Sets the level of catabolic hormones.
     * @param v The new level of catabolic hormones.
     */
    public void setCatabolicHormone(double v) {
        this.catabolicHormone = v;
    }
}

