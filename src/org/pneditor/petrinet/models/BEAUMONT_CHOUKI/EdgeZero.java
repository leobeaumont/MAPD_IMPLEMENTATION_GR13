package io.github.leobeaumont;

/**
 * Represents an incoming edge that activates only when its origin {@link Place} is empty.
 * <p>
 * {@code EdgeZero} extends {@link EdgeIn} and defines behavior where activation is possible
 * only if the origin place has no tokens. When activated, this edge performs no action.
 * </p>
 */
public class EdgeZero extends EdgeIn {

    /**
     * Creates a new {@code EdgeZero} connecting a {@link Place} to a {@link Transition}.
     *
     * @param origin  the origin {@link Place} of the edge
     * @param arrival the arrival {@link Transition} of the edge
     */
    public EdgeZero(Place origin, Transition arrival) {
        super(origin, arrival);
    }

    /**
     * Creates an uninitialized {@code EdgeZero} instance.
     */
    public EdgeZero() {
        super();
    }

    /**
     * Determines whether this edge can be activated.
     * <p>
     * An {@code EdgeZero} is activable if its origin {@link Place} contains no tokens.
     * </p>
     *
     * @return {@code true} if the edge can be activated; {@code false} otherwise
     */
    public boolean isActivable() {
        Place originPlace = this.getOrigin();
        return originPlace.getNbTokens() == 0;
    }

    /**
     * Activates this edge.
     * <p>
     * For {@code EdgeZero}, this method performs no operation.
     * </p>
     */
    public void activate() {
        // No action required
    }
}
