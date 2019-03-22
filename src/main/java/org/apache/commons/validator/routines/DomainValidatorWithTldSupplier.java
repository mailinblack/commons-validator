package org.apache.commons.validator.routines;

import static java.util.Locale.ENGLISH;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.validator.routines.DomainValidator.DOMAIN_NAME_REGEX;
import static org.apache.commons.validator.routines.DomainValidator.MAX_DOMAIN_LENGTH;
import static org.apache.commons.validator.routines.DomainValidator.chompLeadingDot;
import static org.apache.commons.validator.routines.DomainValidator.unicodeToASCII;

public enum DomainValidatorWithTldSupplier {
    ;

    private static final RegexValidator DOMAIN_REGEXP = new RegexValidator(DOMAIN_NAME_REGEX);

    /**
     * Returns true if the specified <code>String</code> parses
     * as a valid domain name with a recognized top-level domain.
     * The parsing is case-insensitive.
     * <p>
     * Local domains are considered as invalid.
     *
     * @param tldSupplier the TLDs supplier to use
     * @param domain the parameter to check for domain name syntax
     * @return true if the parameter is a valid domain name - local domains are considered as invalid
     *
     * @throws NullPointerException if one of the given parameters is {@code null}
     */
    public static boolean isDomainValid(final TldSupplier tldSupplier, final String domain) {
        requireNonNull(tldSupplier);
        requireNonNull(domain);

        final String asciiDomain = unicodeToASCII(domain);

        if (asciiDomain.length() > MAX_DOMAIN_LENGTH) {
            return false;
        }

        final String[] groups = DOMAIN_REGEXP.match(asciiDomain);

        if(isNull(groups) || groups.length == 0) {
            return false;
        }

        return isValidTld(tldSupplier, groups[0]);
    }

    private static boolean isValidTld(final TldSupplier tldSupplier, final String tld) {
        return tldSupplier.get().contains(chompLeadingDot(unicodeToASCII(tld).toLowerCase(ENGLISH)));
    }
}
