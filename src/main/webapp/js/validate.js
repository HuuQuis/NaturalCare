/**
 * Form Validation Library for NaturalCare
 * Reusable validation functions for various forms
 */

// Validation constants
const VALIDATION_RULES = {
    USERNAME: { MIN_LENGTH: 6, MAX_LENGTH: 50 },
    PASSWORD: { MIN_LENGTH: 6, MAX_LENGTH: 100 },
    NAME: { MIN_LENGTH: 2, MAX_LENGTH: 50 },
    PHONE: { LENGTH: 10 }
};

const PATTERNS = {
    EMAIL: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
    PHONE: /^0\d{9}$/,
    NO_SPACES: /^\S*$/,
    STRONG_PASSWORD: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d@$!%*?&]{6,}$/
};

const FIELD_LABELS = {
    username: 'Username',
    password: 'Password',
    confirmPassword: 'Confirm Password',
    firstName: 'First Name',
    lastName: 'Last Name',
    email: 'Email',
    phone: 'Phone'
};

/**
 * Main validation function for registration form
 * @param {string} formId - ID of the form to validate
 * @returns {boolean} - true if form is valid, false otherwise
 */
function validateRegistrationForm(formId = 'registerForm') {
    const form = document.getElementById(formId);
    if (!form) {
        console.error('Form not found:', formId);
        return false;
    }

    const inputs = {
        username: form.querySelector('input[name="username"]'),
        password: form.querySelector('input[name="password"]'),
        confirmPassword: form.querySelector('input[name="password-confirm"]'),
        firstName: form.querySelector('input[name="firstName"]'),
        lastName: form.querySelector('input[name="lastName"]'),
        email: form.querySelector('input[name="email"]'),
        phone: form.querySelector('input[name="phone"]'),
    };

    // Clear all errors first
    clearAllErrors();

    let isValid = true;

    // Validate each field
    isValid &= validateRequired(inputs);
    isValid &= validateUsername(inputs.username);
    isValid &= validatePassword(inputs.password);
    isValid &= validatePasswordConfirm(inputs.password, inputs.confirmPassword);
    isValid &= validateName(inputs.firstName, 'firstName');
    isValid &= validateName(inputs.lastName, 'lastName');
    isValid &= validateEmail(inputs.email);
    isValid &= validatePhone(inputs.phone);

    if (isValid) {
        // Add loading state
        const submitBtn = form.querySelector('button[type="button"]') || form.querySelector('button[type="submit"]');
        if (submitBtn) {
            submitBtn.disabled = true;
            submitBtn.textContent = 'Processing...';
        }
        form.submit();
    } else {
        // Focus on first error field
        focusFirstError();
    }

    return isValid;
}

/**
 * Validate required fields
 */
function validateRequired(inputs) {
    let isValid = true;
    for (const [key, input] of Object.entries(inputs)) {
        if (input && !input.value.trim()) {
            showError(key, FIELD_LABELS[key] + ' is required!');
            isValid = false;
        }
    }
    return isValid;
}

/**
 * Validate username
 */
function validateUsername(input) {
    if (!input) return true;
    const value = input.value.trim();
    if (!value) return true; // Skip if empty (handled by required)

    if (!PATTERNS.NO_SPACES.test(value)) {
        showError('username', 'Username cannot contain spaces!');
        return false;
    }
    if (value.length < VALIDATION_RULES.USERNAME.MIN_LENGTH) {
        showError('username', 'Username must be at least ' + VALIDATION_RULES.USERNAME.MIN_LENGTH + ' characters!');
        return false;
    }
    if (value.length > VALIDATION_RULES.USERNAME.MAX_LENGTH) {
        showError('username', 'Username cannot exceed ' + VALIDATION_RULES.USERNAME.MAX_LENGTH + ' characters!');
        return false;
    }
    return true;
}

/**
 * Validate password
 */
function validatePassword(input) {
    if (!input) return true;
    const value = input.value;
    if (!value) return true; // Skip if empty

    if (!PATTERNS.NO_SPACES.test(value)) {
        showError('password', 'Password cannot contain spaces!');
        return false;
    }
    if (value.length < VALIDATION_RULES.PASSWORD.MIN_LENGTH) {
        showError('password', 'Password must be at least ' + VALIDATION_RULES.PASSWORD.MIN_LENGTH + ' characters!');
        return false;
    }
    if (value.length > VALIDATION_RULES.PASSWORD.MAX_LENGTH) {
        showError('password', 'Password cannot exceed ' + VALIDATION_RULES.PASSWORD.MAX_LENGTH + ' characters!');
        return false;
    }
    return true;
}

/**
 * Validate password confirmation
 */
function validatePasswordConfirm(passwordInput, confirmInput) {
    if (!passwordInput || !confirmInput) return true;
    const password = passwordInput.value;
    const confirm = confirmInput.value;
    
    if (!confirm) return true; // Skip if empty
    
    if (password !== confirm) {
        showError('confirmPassword', 'Passwords do not match!');
        return false;
    }
    return true;
}

/**
 * Validate name fields (firstName, lastName)
 */
function validateName(input, fieldKey) {
    if (!input) return true;
    const value = input.value.trim();
    if (!value) return true; // Skip if empty

    if (value.length < VALIDATION_RULES.NAME.MIN_LENGTH) {
        showError(fieldKey, FIELD_LABELS[fieldKey] + ' must be at least ' + VALIDATION_RULES.NAME.MIN_LENGTH + ' characters!');
        return false;
    }
    if (value.length > VALIDATION_RULES.NAME.MAX_LENGTH) {
        showError(fieldKey, FIELD_LABELS[fieldKey] + ' cannot exceed ' + VALIDATION_RULES.NAME.MAX_LENGTH + ' characters!');
        return false;
    }
    return true;
}

/**
 * Validate email
 */
function validateEmail(input) {
    if (!input) return true;
    const value = input.value.trim();
    if (!value) return true; // Skip if empty

    if (!PATTERNS.EMAIL.test(value)) {
        showError('email', 'Please enter a valid email address!');
        return false;
    }
    return true;
}

/**
 * Validate phone
 */
function validatePhone(input) {
    if (!input) return true;
    const value = input.value.trim();
    if (!value) return true; // Skip if empty

    if (!PATTERNS.PHONE.test(value)) {
        showError('phone', 'Phone must start with 0 and be exactly 10 digits!');
        return false;
    }
    return true;
}

/**
 * Show error message for a specific field
 */
function showError(fieldKey, message) {
    const errorElement = document.getElementById(fieldKey + 'Error');
    if (errorElement) {
        errorElement.textContent = message;
        errorElement.style.display = 'block';
    }
}

/**
 * Clear all error messages
 */
function clearAllErrors() {
    const errorElements = document.querySelectorAll('.error-message-red');
    errorElements.forEach(element => {
        element.textContent = '';
        element.style.display = 'none';
    });
}

/**
 * Clear error for specific field
 */
function clearError(fieldKey) {
    const errorElement = document.getElementById(fieldKey + 'Error');
    if (errorElement) {
        errorElement.textContent = '';
        errorElement.style.display = 'none';
    }
}

/**
 * Focus on first field with error
 */
function focusFirstError() {
    const firstError = document.querySelector('.error-message-red:not(:empty)');
    if (firstError) {
        const fieldId = firstError.id.replace('Error', '');
        const field = document.getElementById(fieldId);
        if (field) {
            field.focus();
            field.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    }
}

/**
 * Enable real-time validation for registration form
 * Call this function to enable blur validation
 */
function enableRealTimeValidation(formId = 'registerForm') {
    const form = document.getElementById(formId);
    if (!form) return;

    // Username validation on blur
    const usernameField = form.querySelector('[name="username"]');
    if (usernameField) {
        usernameField.addEventListener('blur', function() {
            clearError('username');
            validateUsername(this);
        });
    }

    // Password validation on blur
    const passwordField = form.querySelector('[name="password"]');
    if (passwordField) {
        passwordField.addEventListener('blur', function() {
            clearError('password');
            validatePassword(this);
        });
    }

    // Confirm password validation on blur
    const confirmPasswordField = form.querySelector('[name="password-confirm"]');
    if (confirmPasswordField) {
        confirmPasswordField.addEventListener('blur', function() {
            clearError('confirmPassword');
            validatePasswordConfirm(passwordField, this);
        });
    }

    // First name validation on blur
    const firstNameField = form.querySelector('[name="firstName"]');
    if (firstNameField) {
        firstNameField.addEventListener('blur', function() {
            clearError('firstName');
            validateName(this, 'firstName');
        });
    }

    // Last name validation on blur
    const lastNameField = form.querySelector('[name="lastName"]');
    if (lastNameField) {
        lastNameField.addEventListener('blur', function() {
            clearError('lastName');
            validateName(this, 'lastName');
        });
    }

    // Email validation on blur
    const emailField = form.querySelector('[name="email"]');
    if (emailField) {
        emailField.addEventListener('blur', function() {
            clearError('email');
            validateEmail(this);
        });
    }

    // Phone validation on blur
    const phoneField = form.querySelector('[name="phone"]');
    if (phoneField) {
        phoneField.addEventListener('blur', function() {
            clearError('phone');
            validatePhone(this);
        });
    }
}

/**
 * Generic login form validation
 */
function validateLoginForm(formId = 'loginForm') {
    const form = document.getElementById(formId);
    if (!form) return false;

    clearAllErrors();
    
    const username = form.querySelector('[name="username"]');
    const password = form.querySelector('[name="password"]');
    
    let isValid = true;
    
    if (!username.value.trim()) {
        showError('username', 'Username is required!');
        isValid = false;
    }
    
    if (!password.value.trim()) {
        showError('password', 'Password is required!');
        isValid = false;
    }
    
    if (isValid) {
        const submitBtn = form.querySelector('button[type="button"]') || form.querySelector('button[type="submit"]');
        if (submitBtn) {
            submitBtn.disabled = true;
            submitBtn.textContent = 'Logging in...';
        }
        form.submit();
    } else {
        focusFirstError();
    }
    
    return isValid;
} 