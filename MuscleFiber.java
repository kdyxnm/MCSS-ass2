/**
 * The MuscleFiber class represents a muscle fiber in the body.
 * It includes properties such as fiber size and maximum size,
 * and methods to regulate and develop the muscle fiber.
 */
public class MuscleFiber {
    private double fiberSize; // The size of the muscle fiber
    private double maxSize; // The maximum size the muscle fiber can reach
    private static final double MIN_SIZE = 1.0; // The minimum size the muscle fiber can be

    /**
     * Constructs a new MuscleFiber with the given slow twitch percentage.
     * @param slowTwitchPercentage The percentage of slow twitch muscles.
     */
    public MuscleFiber(int slowTwitchPercentage) {
        this.maxSize = 4.0;
        // Randomly generate muscle fiber size based on slow twitch percentage
        for (int i = 0; i < 20; i++) {
            if (Math.random() * 100.0 > slowTwitchPercentage) {
                maxSize++;
            }
        }
        this.fiberSize = (0.2 + Math.random() * 0.4) * maxSize;
        regulateMuscleFiber();
    }

    /**
     * Regulates the size of the muscle fiber to be within the allowed limits.
     */
    public void regulateMuscleFiber() {
        // Adjust size to be within allowed limits
        if (fiberSize < MIN_SIZE) {
            fiberSize = MIN_SIZE;
        }
        if (fiberSize > maxSize) {
            fiberSize = maxSize;
        }
    }

    /**
     * Develops the muscle fiber based on the given anabolic and catabolic factors.
     * @param anabolicFactor The anabolic factor.
     * @param catabolicFactor The catabolic factor.
     */
    public void developMuscle(double anabolicFactor, double catabolicFactor) {
        // grow
        fiberSize -= 0.20 * Math.log10(catabolicFactor);
        fiberSize += 0.20 * Math.min(Math.log10(anabolicFactor),
                1.05 * Math.log10(catabolicFactor));
        regulateMuscleFiber();
    }

    /**
     * Returns the size of the muscle fiber.
     * @return The size of the muscle fiber.
     */
    public double getFiberSize() {
        return fiberSize;
    }

    /**
     * Sets the size of the muscle fiber.
     * @param size The new size of the muscle fiber.
     */
    public void setFiberSize(double size) {
        this.fiberSize = size;
        regulateMuscleFiber();
    }
}

