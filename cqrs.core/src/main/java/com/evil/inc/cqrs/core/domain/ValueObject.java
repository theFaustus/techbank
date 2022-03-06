package com.evil.inc.cqrs.core.domain;

/**
 * Represents a DDD Value Object:
 * <p>
 * “When you care only about the attributes of an element of the model, classify it as a VALUE OBJECT.
 * Make it express the meaning of the attributes it conveys and give it related functionality.
 * Treat the VALUE OBJECT as immutable.
 * Don’t give it any identity and avoid the design complexities necessary to maintain ENTITIES. [Evans, p. 99]"
 * <p>
 * When you are trying to decide whether a concept is a Value, you should determine whether it possesses most of these characteristics:
 * “It measures, quantifies, or describes a thing in the domain.
 * • It can be maintained as immutable.
 * • It models a conceptual whole by composing related attributes as an integral unit.
 * • It is completely replaceable when the measurement or description changes.
 * • It can be compared with others using Value equality.
 * • It supplies its collaborators with Side-Effect-Free Behavior [Evans].”
 * <p>
 * Excerpt From: Vernon, Vaughn. “Implementing Domain-Driven Design.” iBooks.
 */
public abstract class ValueObject {

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

}
