package ee.argo.decathlon.strategy;

public class HundredMetersScoring implements ScoringStrategy {
    @Override
    public double calculate(int score) {
        if (score >= 18.0) return 0;
        return 25.4347 * Math.pow((18.0 - score), 1.81);
    }
}