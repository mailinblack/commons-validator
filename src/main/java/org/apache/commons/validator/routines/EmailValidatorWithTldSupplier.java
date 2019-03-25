package org.apache.commons.validator.routines;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.validator.routines.DomainValidatorWithTldSupplier.isDomainValid;
import static org.apache.commons.validator.routines.EmailValidator.IP_DOMAIN_PATTERN;
import static org.apache.commons.validator.routines.EmailValidator.isValidUser;
import static org.apache.commons.validator.routines.EmailValidatorWithTldSupplier.InvalidReason.DOMAIN_MAX_LENGTH_EXCEEDED;
import static org.apache.commons.validator.routines.EmailValidatorWithTldSupplier.InvalidReason.DOMAIN_TLD;
import static org.apache.commons.validator.routines.EmailValidatorWithTldSupplier.InvalidReason.FORMAT;
import static org.apache.commons.validator.routines.EmailValidatorWithTldSupplier.InvalidReason.INVALID_IP_DOMAIN_FORMAT;
import static org.apache.commons.validator.routines.EmailValidatorWithTldSupplier.InvalidReason.USER_FORMAT;

import java.util.Optional;
import java.util.regex.Matcher;

public enum EmailValidatorWithTldSupplier {
    ;

    /**
     * <p>Checks if a field has a valid e-mail address.</p>
     *
     * @param tldSupplier the TLDs supplier to use
     * @param email The value validation is being performed on.  A <code>null</code>
     *              value is considered invalid.
     * @return the {@link ValidationResult}.
     *
     * @throws NullPointerException if one of the given parameters is {@code null}
     */
    public static ValidationResult isValid(final TldSupplier tldSupplier, final String email) {
        requireNonNull(tldSupplier);
        requireNonNull(email);

        if (email.endsWith(".")) {
            return new ValidationResult(false, FORMAT);
        }

        final Matcher emailMatcher = EmailValidator.EMAIL_PATTERN.matcher(email);

        if(!emailMatcher.matches()) {
            return new ValidationResult(false, FORMAT);
        }

        if(!isValidUser(emailMatcher.group(1))) {
            return new ValidationResult(false, USER_FORMAT);
        }

        return isValidDomain(tldSupplier, emailMatcher.group(2));
    }

    protected static ValidationResult isValidDomain(final TldSupplier tldSupplier, final String domain) {
        final Matcher ipDomainMatcher = IP_DOMAIN_PATTERN.matcher(domain);
        if (ipDomainMatcher.matches()) {

            if(!InetAddressValidator.getInstance().isValid(ipDomainMatcher.group(1))) {
                return new ValidationResult(false, INVALID_IP_DOMAIN_FORMAT);
            }
            return new ValidationResult(true, null);
        }

        final DomainValidatorWithTldSupplier.ValidationResult domainResult = isDomainValid(tldSupplier, domain);
        if(domainResult.isValid()) {
            return new ValidationResult(true, null);
        }
        if(domainResult.invalidReason().isPresent()) {
            return new ValidationResult(false, toEmailInvalidReason(domainResult.invalidReason().get()));
        }
        throw new IllegalStateException("Invalid result without reason!");
    }

    private static InvalidReason toEmailInvalidReason(final DomainValidatorWithTldSupplier.InvalidReason reason) {
        switch(reason) {
            case MAX_LENGTH_EXCEEDED:
                return DOMAIN_MAX_LENGTH_EXCEEDED;
            case FORMAT:
                return FORMAT;
            case TLD:
                return DOMAIN_TLD;
            default:
                throw new UnsupportedOperationException("Unsupported reason: " + reason);
        }
    }

    public enum InvalidReason {
        FORMAT,
        INVALID_IP_DOMAIN_FORMAT,
        USER_FORMAT,
        DOMAIN_MAX_LENGTH_EXCEEDED,
        DOMAIN_TLD
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
