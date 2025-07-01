function loadCartItems() {
    fetch("cart-items")
        .then(res => res.text())
        .then(html => {
            document.getElementById("cart-items").innerHTML = html;
            attachCartEventListeners(); // reattach event listeners
        });
}

function attachCartEventListeners() {
    function updateCart(variationId, quantity, inputElement) {
        const price = parseInt(inputElement.dataset.price);

        fetch(`update-cart`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                variationId: variationId,
                quantity: quantity
            })
        })
            .then(res => res.text())
            .then(responseText => {
                const parts = responseText.split("|");
                const action = parts[0];

                if (action === "error") {
                    const message = parts[1] || "Unexpected error occurred";
                    alert(message);
                    loadCartItems();
                    return;
                }

                const id = parseInt(parts[1]);
                const qty = parseInt(parts[2]);

                if (action === "removed") {
                    const message = parts[3] || "Item removed from cart.";
                    alert(message);
                    showCartLoadingSpinner();
                    setTimeout(loadCartItems, 500);
                } else if (action === "updated") {
                    const lineTotalEl = inputElement.closest(".cart-item-details").querySelector(".line-total");
                    lineTotalEl.textContent = formatCurrency(price * quantity);
                }

                updateCartTotal();

                if (typeof updateVariationInfo === "function") {
                    updateVariationInfo(); // update stock if needed
                }
            })
            .catch(err => {
                alert("Error while updating cart. Please try again later.");
                console.error(err);
            });
    }

    function formatCurrency(value) {
        return value.toLocaleString("vi-VN");
    }

    function updateCartTotal() {
        let total = 0;
        document.querySelectorAll(".quantity-input").forEach(input => {
            const price = parseInt(input.dataset.price);
            const qty = parseInt(input.value);
            total += price * qty;
        });

        const totalEl = document.getElementById("cart-total");
        if (totalEl) {
            totalEl.textContent = formatCurrency(total);
        }
    }

    // Plus/minus buttons
    document.querySelectorAll(".qty-btn").forEach(btn => {
        btn.addEventListener("click", function () {
            const input = this.parentElement.querySelector(".quantity-input");
            let quantity = parseInt(input.value);
            const variationId = input.dataset.variationId;
            const max = parseInt(input.dataset.max);

            if (this.classList.contains("plus")) {
                if (quantity < max) {
                    quantity++;
                    input.value = quantity;
                    updateCart(variationId, quantity, input);
                } else {
                    alert("You cannot add more than the available stock.");
                }
            } else if (this.classList.contains("minus")) {
                if (quantity === 1) {
                    const confirmDelete = confirm("Are you sure you want to remove this item from your cart?");
                    if (confirmDelete) {
                        input.value = 0;
                        updateCart(variationId, 0, input);
                    }
                } else {
                    quantity = Math.max(0, quantity - 1);
                    input.value = quantity;
                    updateCart(variationId, quantity, input);
                }
            }
        });
    });

    // Manual input
    document.querySelectorAll(".quantity-input").forEach(input => {
        input.addEventListener("input", function () {
            let quantity = parseInt(this.value);
            const variationId = this.dataset.variationId;
            const max = parseInt(this.dataset.max);

            if (isNaN(quantity) || quantity < 0) {
                quantity = 1;
                this.value = quantity;
            } else if (quantity === 0) {
                const confirmDelete = confirm("Are you sure you want to remove this item from your cart?");
                if (!confirmDelete) {
                    quantity = 1;
                    this.value = quantity;
                }
            } else if (quantity > max) {
                alert("Maximum available stock is " + max + ".");
                quantity = max;
                this.value = quantity;
            }

            updateCart(variationId, quantity, this);
        });
    });
}

function showCartLoadingSpinner() {
    const cartItems = document.getElementById("cart-items");
    if (cartItems) {
        cartItems.innerHTML = `<div class="cart-spinner"></div>`;
    }
}

function closeCartModal() {
    document.getElementById("cart-modal").classList.remove("show");
    document.getElementById("cart-overlay").classList.remove("show");
    document.body.classList.remove("modal-open");
}

document.addEventListener("DOMContentLoaded", function () {
    const cartIcon = document.getElementById("cart-icon");
    const cartModal = document.getElementById("cart-modal");
    const closeBtn = document.getElementById("cart-close-btn");
    const overlay = document.getElementById("cart-overlay");

    let hoverTimer;

    cartIcon.addEventListener("mouseenter", function () {
        hoverTimer = setTimeout(() => {
            cartModal.classList.add("show");
            overlay.classList.add("show");
            document.body.classList.add("modal-open");

            document.getElementById("cart-items").innerHTML = `<div class="cart-spinner"></div>`;
            setTimeout(loadCartItems, 500);
        }, 1500);
    });

    cartIcon.addEventListener("mouseleave", function () {
        clearTimeout(hoverTimer);
    });

    closeBtn.addEventListener("click", closeCartModal);
});