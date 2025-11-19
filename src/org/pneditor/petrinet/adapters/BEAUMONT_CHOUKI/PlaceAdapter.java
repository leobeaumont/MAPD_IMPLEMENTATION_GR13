package org.pneditor.petrinet.adapters.BEAUMONT_CHOUKI;

import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Nodes.Place;

/**
 * Adapter that bridges the BEAUMONT_CHOUKI {@link Place} model
 * with the PNEditor {@link AbstractPlace} API.
 *
 * <p>This class follows the Adapter design pattern by exposing
 * the interface required by PNEditor while delegating all
 * token-related operations to the underlying {@code Place} instance
 * from the custom model.</p>
 */
public class PlaceAdapter extends AbstractPlace {

    /**
     * The underlying BEAUMONT_CHOUKI place being adapted.
     */
    private final Place adaptee;

    /**
     * Creates a new {@code PlaceAdapter}.
     *
     * @param adaptee The place instance from the BEAUMONT_CHOUKI model to adapt.
     * @param label   The label to be used by the PNEditor {@code AbstractPlace}.
     */
    public PlaceAdapter(final String label, final Place adaptee) {
        super(label);
        this.adaptee = adaptee;
    }

    /**
     * Returns the underlying adapted place from the BEAUMONT_CHOUKI model.
     *
     * @return The adaptee place.
     */
    public Place getAdaptee() {
        return adaptee;
    }

    /**
     * Adds a single token to this place.
     * <p>
     * This method delegates to {@link Place#addTokens(int)} with an amount of {@code 1}.
     * </p>
     */
    @Override
    public void addToken() {
        adaptee.addTokens(1);
    }

    /**
     * Removes a single token from this place.
     * <p>
     * This method delegates to {@link Place#removeTokens(int)} with an amount of {@code 1}.
     * Any {@link IllegalArgumentException} thrown by the adaptee is propagated.
     * </p>
     *
     * @throws IllegalArgumentException if there are no tokens available to remove.
     */
    @Override
    public void removeToken() {
        adaptee.removeTokens(1);
    }

    /**
     * Returns the current number of tokens in this place.
     *
     * @return The number of tokens stored in the underlying adaptee.
     */
    @Override
    public int getTokens() {
        return adaptee.getNbTokens();
    }

    /**
     * Sets the number of tokens in this place.
     *
     * @param tokens The new number of tokens.
     * @throws IllegalArgumentException if {@code tokens} is negative.
     */
    @Override
    public void setTokens(final int tokens) {
        if (tokens < 0) {
            throw new IllegalArgumentException(
                "PlaceAdapter.setTokens(" + tokens + ") -- token count cannot be negative."
            );
        }
        adaptee.setNbTokens(tokens);
    }
}
