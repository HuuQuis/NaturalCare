function loadCartItems() {
    fetch("cart-items")
        .then(res => res.text())
        .then(html => {
            document.getElementById("cart-items").innerHTML = html;
            attachCartEventListeners(); // gắn lại sự kiện cho các nút trong giỏ
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
            .then(res => {
                if (!res.ok) throw new Error("Server error or not found");
                return res.text();
            })
            .then(responseText => {
                const [action, idStr, qtyStr] = responseText.split("|");
                const id = parseInt(idStr);
                const qty = parseInt(qtyStr);

                if (action === "removed") {
                    showCartLoadingSpinner();
                    setTimeout(() => {
                        loadCartItems();
                    }, 500);
                } else if (action === "updated") {
                    const lineTotalEl = inputElement.closest(".cart-item-details").querySelector(".line-total");
                    lineTotalEl.textContent = formatCurrency(price * quantity);
                }

                updateCartTotal();
            })
            .catch(err => {
                alert("Error when updating cart. Please try again later.");
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

    // Nút cộng/trừ
    document.querySelectorAll(".qty-btn").forEach(btn => {
        btn.addEventListener("click", function () {
            const input = this.parentElement.querySelector(".quantity-input");
            let quantity = parseInt(input.value);
            const variationId = input.dataset.variationId;

            if (this.classList.contains("plus")) {
                quantity++;
                input.value = quantity;
                updateCart(variationId, quantity, input);
            } else if (this.classList.contains("minus")) {
                if (quantity === 1) {
                    const confirmDelete = confirm("Bạn có chắc muốn xoá sản phẩm khỏi giỏ hàng?");
                    if (confirmDelete) {
                        quantity = 0;
                        input.value = quantity;
                        updateCart(variationId, quantity, input);
                    }
                } else {
                    quantity = Math.max(0, quantity - 1);
                    input.value = quantity;
                    updateCart(variationId, quantity, input);
                }
            }
        });
    });

    // Nhập thủ công
    document.querySelectorAll(".quantity-input").forEach(input => {
        input.addEventListener("change", function () {
            let quantity = parseInt(this.value);
            const variationId = this.dataset.variationId;

            if (isNaN(quantity) || quantity < 0) {
                quantity = 1;
                this.value = quantity;
            } else if (quantity === 0) {
                const confirmDelete = confirm("Bạn có chắc muốn xoá sản phẩm khỏi giỏ hàng?");
                if (!confirmDelete) {
                    quantity = 1;
                    this.value = quantity;
                }
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
        }, 500);
    });

    cartIcon.addEventListener("mouseleave", function () {
        clearTimeout(hoverTimer);
    });

    closeBtn.addEventListener("click", closeCartModal);
});

