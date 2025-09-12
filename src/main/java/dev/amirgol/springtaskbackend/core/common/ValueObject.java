package dev.amirgol.springtaskbackend.core.common;

import java.util.Collection;
import java.util.Objects;

public abstract class ValueObject {
    public abstract Collection<Object> getAtomicValues();

    @Override
    public boolean equals(Object obj) {
        // 1. NullCheck
        if(obj == this) return true;
        if(Objects.isNull(obj) || this.getClass() != obj.getClass()) return false;
        // 2. ValueObject Check
        ValueObject other = (ValueObject) obj;
        // 3. Equality Check
        return valuesAreEqual(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAtomicValues().toArray());
    }

    private boolean valuesAreEqual(ValueObject other) {
        return Objects.equals(getAtomicValues(), other.getAtomicValues());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + getAtomicValues();
    }
}
