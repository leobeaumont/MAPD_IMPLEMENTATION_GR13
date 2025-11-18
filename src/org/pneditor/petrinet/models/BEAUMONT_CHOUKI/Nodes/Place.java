package org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Nodes;

/**
 * Represents a place in a Petri net, used to store and manage tokens.
 * <p>
 * A {@code Place} holds a non-negative number of tokens and provides
 * methods to add or remove tokens safely.
 * </p>
 */
public class Place extends Node {

    private int nbTokens;

    /**
     * Creates a new {@code Place} with zero tokens.
     */
    public Place() {
        setNbTokens(0);
    }

    /**
     * Creates a new {@code Place} initialized with the specified number of tokens.
     * <p>
     * If the provided number of tokens is negative, it defaults to zero and a warning
     * message is printed.
     * </p>
     *
     * @param nbTokens the initial number of tokens (defaults to 0 if negative)
     */
    public Place(int nbTokens) {
        if (nbTokens >= 0) {
            setNbTokens(nbTokens);
        } else {
            System.out.printf("Place -- Invalid nbTokens: %d (nbTokens defaulted to 0)%n", nbTokens);
            setNbTokens(0);
        }
    }

    /**
     * Removes a specified number of tokens from this place.
     *
     * @param amount the number of tokens to remove
     * @throws IllegalArgumentException if {@code amount} is negative or greater than the current number of tokens
     */
    public void removeTokens(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException(String.format(
                "Place.removeTokens(%d) -- The argument amount (%d) is negative", amount, amount));
        }

        int tokens = getNbTokens();
        if (tokens >= amount) {
            this.nbTokens -= amount;
        } else {
            throw new IllegalArgumentException(String.format(
                "Place.removeTokens(%d) -- The argument amount (%d) is greater than nbTokens (%d)",
                amount, amount, tokens));
        }
    }

    /**
     * Adds a specified number of tokens to this place.
     *
     * @param amount the number of tokens to add
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void addTokens(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(String.format(
                "Place.addTokens(%d) -- The argument amount (%d) is negative", amount, amount));
        }

        this.nbTokens += amount;
    }

    /**
     * Returns the current number of tokens in this place.
     *
     * @return the number of tokens
     */
    public int getNbTokens() {
        return nbTokens;
    }

    /**
     * Sets the number of tokens in this place.
     *
     * @param nbTokens the number of tokens to set
     */
    public void setNbTokens(int nbTokens) {
        this.nbTokens = nbTokens;
    }
}
