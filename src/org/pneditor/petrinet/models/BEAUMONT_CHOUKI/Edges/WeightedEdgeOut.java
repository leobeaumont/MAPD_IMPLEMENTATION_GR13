package org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Edges;

import io.github.leobeaumont.Nodes.Place;
import io.github.leobeaumont.Nodes.Transition;

/**
 * Represents an outgoing edge with a specified weight.
 * <p>
 * {@code WeightedEdgeOut} extends {@link EdgeOut} and, when activated,
 * adds a number of tokens equal to its weight to the arrival {@link Place}.
 * </p>
 */
public class WeightedEdgeOut extends EdgeOut {

    private int weight;

    /**
     * Creates a new {@code WeightedEdgeOut} connecting a {@link Transition} to a {@link Place}
     * with the specified weight.
     *
     * @param origin  the origin {@link Transition} of the edge
     * @param arrival the arrival {@link Place} of the edge
     * @param weight  the weight of the edge (must be non-negative)
     * @throws IllegalArgumentException if {@code weight} is negative
     */
    public WeightedEdgeOut(Transition origin, Place arrival, int weight) throws IllegalArgumentException {
        super(origin, arrival);
        if (weight < 0) {
            throw new IllegalArgumentException(String.format(
                "WeightedEdgeOut(%d) -- The argument weight (%d) can't be negative", weight, weight));
        }
        this.setWeight(weight);
    }

    /**
     * Creates a new {@code WeightedEdgeOut} with the specified weight.
     *
     * @param weight the weight of the edge (must be non-negative)
     * @throws IllegalArgumentException if {@code weight} is negative
     */
    public WeightedEdgeOut(int weight) {
        super();
        if (weight < 0) {
            throw new IllegalArgumentException(String.format(
                "WeightedEdgeOut(%d) -- The argument weight (%d) can't be negative", weight, weight));
        }
        this.setWeight(weight);
    }

    /**
     * Activates this edge.
     * <p>
     * For {@code WeightedEdgeOut}, this adds a number of tokens equal to
     * the weight to the arrival {@link Place}.
     * </p>
     */
    public void activate() {
        Place arrivalPlace = this.getArrival();
        arrivalPlace.addTokens(this.getWeight());
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
