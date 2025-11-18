package io.github.leobeaumont;

/**
 * Represents an incoming edge with a specified weight.
 * <p>
 * {@code WeightedEdgeIn} extends {@link EdgeIn} and can only be activated
 * if its origin {@link Place} contains at least as many tokens as its weight.
 * Activation removes a number of tokens equal to the weight from the origin place.
 * </p>
 */
public class WeightedEdgeIn extends EdgeIn {

    private int weight;

    /**
     * Creates a new {@code WeightedEdgeIn} connecting a {@link Place} to a {@link Transition}
     * with the specified weight.
     *
     * @param origin  the origin {@link Place} of the edge
     * @param arrival the arrival {@link Transition} of the edge
     * @param weight  the weight of the edge (must be non-negative)
     * @throws IllegalArgumentException if {@code weight} is negative
     */
    public WeightedEdgeIn(Place origin, Transition arrival, int weight) throws IllegalArgumentException {
        super(origin, arrival);
        if (weight < 0) {
            throw new IllegalArgumentException(String.format(
                "WeightedEdgeIn(%d) -- The argument weight (%d) can't be negative", weight, weight));
        }
        this.setWeight(weight);
    }

    /**
     * Creates a new {@code WeightedEdgeIn} with the specified weight.
     *
     * @param weight the weight of the edge (must be non-negative)
     * @throws IllegalArgumentException if {@code weight} is negative
     */
    public WeightedEdgeIn(int weight) {
        super();
        if (weight < 0) {
            throw new IllegalArgumentException(String.format(
                "WeightedEdgeIn(%d) -- The argument weight (%d) can't be negative", weight, weight));
        }
        this.setWeight(weight);
    }

    /**
     * Determines whether this edge can be activated.
     * <p>
     * A {@code WeightedEdgeIn} is activable if its origin {@link Place} contains
     * at least as many tokens as its weight.
     * </p>
     *
     * @return {@code true} if the edge can be activated; {@code false} otherwise
     */
    public boolean isActivable() {
        Place originPlace = this.getOrigin();
        int tokenInPlace = originPlace.getNbTokens();
        return tokenInPlace >= this.getWeight();
    }

    /**
     * Activates this edge.
     * <p>
     * For {@code WeightedEdgeIn}, this removes a number of tokens equal to
     * the weight from the origin {@link Place}.
     * </p>
     */
    public void activate() {
        Place originPlace = this.getOrigin();
        originPlace.removeTokens(this.getWeight());
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight of this edge.
     *
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
