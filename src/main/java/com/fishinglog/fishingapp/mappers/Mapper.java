package com.fishinglog.fishingapp.mappers;

/**
 * Interface defining the mapping operations between two object types.
 *
 * @param <A> The source type from which the mapping is done.
 * @param <B> The target type to which the mapping is done.
 *
 * @since 2023-10-31
 */
public interface Mapper<A, B> {

    /**
     * Maps an object of type A to an object of type B.
     *
     * @param a The source object to map from.
     * @return The mapped object of type B.
     */
    B mapTo(A a);

    /**
     * Maps an object of type B back to an object of type A.
     *
     * @param b The source object to map from.
     * @return The mapped object of type A.
     */
    A mapFrom(B b);
}