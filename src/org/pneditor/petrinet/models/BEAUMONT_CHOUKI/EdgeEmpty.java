package io.github.leobeaumont;

/**
 * Represents an incoming edge that empties its origin {@link Place} upon activation.
 * <p>
 * {@code EdgeEmpty} extends {@link EdgeIn} and defines behavior where activation
 * removes all tokens from the origin place.
 * </p>
 * <p>
 * The edge can only be activated if its origin {@link Place} contains at least one token.
 * </p>
 */
public class EdgeEmpty extends EdgeIn {

    /**
     * Creates a new {@code EdgeEmpty} connecting a {@link Place} to a {@link Transition}.
     *
     * @param origin  the origin {@link Place} of the edge
     * @param arrival the arrival {@link Transition} of the edge
     */
    public EdgeEmpty(Place origin, Transition arrival) {
        super(origin, arrival);
    }

    /**
     * Creates an uninitialized {@code EdgeEmpty} instance.
     */
    public EdgeEmpty() {
        super();
    }

    /**
     * Determines whether this edge can be activated.
     * <p>
     * An {@code EdgeEmpty} is activable if its origin {@link Place} contains
     * at least one token.
     * </p>
     *
     * @return {@code true} if the edge can be activated; {@code false} otherwise
     */
    public boolean isActivable() {
        Place originPlace = this.getOrigin();
        return originPlace.getNbTokens() > 0;
    }

    /**
     * Activates this edge.
     * <p>
     * When activated, this implementation removes all tokens from
     * the origin {@link Place}.
     * </p>
     */
    public void activate() {
        Place originPlace = this.getOrigin();
        originPlace.setNbTokens(0);
    }
}
