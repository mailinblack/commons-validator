package org.apache.commons.validator.routines;

import java.util.Set;
import java.util.function.Supplier;

/**
 * The TLDs supplier - must be <b>lowercase</b> top level domains values.
 */
public interface TldSupplier extends Supplier<Set<String>> {
    // marker interface
}
