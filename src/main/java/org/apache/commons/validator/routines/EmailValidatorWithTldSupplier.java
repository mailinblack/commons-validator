package org.apache.commons.validator.routines;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.validator.routines.EmailValidator.IP_DOMAIN_PATTERN;
import static org.apache.commons.validator.routines.EmailValidator.isValidUser;

import java.util.regex.Matcher;

public enum EmailValidatorWithTldSupplier {
    ;

    /**
     * <p>Checks if a field has a valid e-mail address.</p>
     *
     * @param tldSupplier the TLDs supplier to use
     * @param email The value validation is being performed on.  A <code>null</code>
     *              value is considered invalid.
     * @return true if the email address is valid.
     *
     * @throws NullPointerException if one of the given parameters is {@code null}
     */
    public static boolean isValid(final TldSupplier tldSupplier, final String email) {
        requireNonNull(tldSupplier);
        requireNonNull(email);

        if (email.endsWith(".")) {
            return false;
        }

        final Matcher emailMatcher = EmailValidator.EMAIL_PATTERN.matcher(email);

        return emailMatcher.matches() &&
               isValidUser(emailMatcher.group(1)) &&
               isValidDomain(tldSupplier, emailMatcher.group(2));
    }

    protected static boolean isValidDomain(final TldSupplier tldSupplier, final String domain) {
        final Matcher ipDomainMatcher = IP_DOMAIN_PATTERN.matcher(domain);
        if (ipDomainMatcher.matches()) {
            return InetAddressValidator.getInstance().isValid(ipDomainMatcher.group(1));
        }
        return DomainValidatorWithTldSupplier.isDomainValid(tldSupplier, domain);
    }
}
