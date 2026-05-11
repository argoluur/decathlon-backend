package ee.argo.decathlon.strategy;

public class LongJumpScoring implements ScoringStrategy {
    @Override
    public double calculate(int score) {
        if (score <= 220) return 0;
        return 0.14354 * Math.pow((score - 220.0), 1.40);
    }
}