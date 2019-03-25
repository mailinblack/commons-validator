package org.apache.commons.validator.routines;

import static java.util.Locale.ENGLISH;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.validator.routines.DomainValidator.DOMAIN_NAME_REGEX;
import static org.apache.commons.validator.routines.DomainValidator.MAX_DOMAIN_LENGTH;
import static org.apache.commons.validator.routines.DomainValidator.chompLeadingDot;
import static org.apache.commons.validator.routines.DomainValidator.unicodeToASCII;
import static org.apache.commons.validator.routines.DomainValidatorWithTldSupplier.InvalidReason.FORMAT;
import static org.apache.commons.validator.routines.DomainValidatorWithTldSupplier.InvalidReason.MAX_LENGTH_EXCEEDED;
import static org.apache.commons.validator.routines.DomainValidatorWithTldSupplier.InvalidReason.TLD;

import java.util.Optional;

public enum DomainValidatorWithTldSupplier {
    ;

    private static final RegexValidator DOMAIN_REGEXP = new RegexValidator(DOMAIN_NAME_REGEX);

    /**
     * Returns a {@link ValidationResult} - result of the validation of the specified <code>String</code> parsing
     * it and checking it is a valid domain name with a recognized top-level domain.
     * The parsing is case-insensitive.
     * <p>
     * Local domains are considered as invalid.
     *
     * @param tldSupplier the TLDs supplier to use
     * @param domain the parameter to check for domain name syntax
     * @return the {@link ValidationResult}
     *
     * @throws NullPointerException if one of the given parameters is {@code null}
     */
    public static ValidationResult isDomainValid(final TldSupplier tldSupplier, final String domain) {
        requireNonNull(tldSupplier);
        requireNonNull(domain);

        final String asciiDomain = unicodeToASCII(domain);

        if (asciiDomain.length() > MAX_DOMAIN_LENGTH) {
            return new ValidationResult(false, MAX_LENGTH_EXCEEDED);
        }

        final String[] groups = DOMAIN_REGEXP.match(asciiDomain);

        if(isNull(groups) || groups.length == 0) {
            return new ValidationResult(false, FORMAT);
        }

        final boolean isValidTld = isValidTld(tldSupplier, groups[0]);
        if(isValidTld) {
            return new ValidationResult(true, null);
        }
        return new ValidationResult(false, TLD);
    }

    private static boolean isValidTld(final TldSupplier tldSupplier, final String tld) {
        return tldSupplier.get().contains(chompLeadingDot(unicodeToASCII(tld).toLowerCase(ENGLISH)));
    }

    public enum InvalidReason {
        MAX_LENGTH_EXCEEDED,
        FORMAT,
        TLD
    }

    public static final class ValidationResult {

        private final boolean isValid;
        private final InvalidReason invalidReason;

        private ValidationResult(final boolean isValid, final InvalidReason invalidReason) {
            this.isValid = isValid;
            this.invalidReason = invalidReason;
        }

        public boolean isValid() {
            return isValid;
        }

        public Optional<InvalidReason> invalidReason() {
            return ofNullable(invalidReason);
        }
    }
}
