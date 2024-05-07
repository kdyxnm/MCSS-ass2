public class MuscleFiber {
    private double fiberSize;
    private double maxSize;
    private static final double MIN_SIZE = 1.0;

    public MuscleFiber(int slowTwitchPercentage) {
        this.maxSize = 4.0;
        for (int i = 0; i < 20; i++) {
            if (Math.random() * 100.0 > slowTwitchPercentage) {
                maxSize++;
            }
        }
        this.fiberSize = (0.2 + Math.random() * 0.4) * maxSize;
        regulateMuscleFiber();
    }

    public void regulateMuscleFiber() {
        // Adjust size to be within allowed limits
        if (fiberSize < MIN_SIZE) {
            fiberSize = MIN_SIZE;
        }
        if (fiberSize > maxSize) {
            fiberSize = maxSize;
        }
    }

    public void developMuscle(double anabolicFactor, double catabolicFactor) {
        // grow
        fiberSize -= 0.20 * Math.log10(catabolicFactor);
        fiberSize += 0.20 * Math.min(Math.log10(anabolicFactor), 1.05 * Math.log10(catabolicFactor));
        regulateMuscleFiber();
    }

    public double getFiberSize() {
        return fiberSize;
    }

    public void setFiberSize(double size) {
        this.fiberSize = size;
        regulateMuscleFiber();
    }
}

