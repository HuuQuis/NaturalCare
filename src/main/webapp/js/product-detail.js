let selectedColor = null;
let selectedSize = null;
let maxStock = 1;

function formatNumber(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function formatPrice(price) {
    const numPrice = parseFloat(price);
    return isNaN(numPrice) ? 'N/A' : formatNumber(numPrice) + ' VND';
}

function getCartMapFromCookie() {
    const cartMap = new Map();
    const cookies = document.cookie.split(";");
    for (let cookie of cookies) {
        const [key, value] = cookie.trim().split("=");
        if (key === "cart" && value) {
            const items = decodeURIComponent(value).split("|");
            for (let item of items) {
                const [idStr, qtyStr] = item.split(":");
                const id = parseInt(idStr);
                const qty = parseInt(qtyStr);
                if (!isNaN(id) && !isNaN(qty)) {
                    cartMap.set(id, qty);
                }
            }
        }
    }
    return cartMap;
}

function updateVariationInfo() {
    // Chỉ chạy nếu đang ở trang product-detail
    if (document.body.dataset.page !== 'product-detail') return;

    const variationInfo = document.getElementById('variation-info');
    const addToCartBtn = document.getElementById('add-to-cart-btn');
    if (!variationInfo) return;

    variationInfo.style.display = 'block';

    let found = false;
    const cartMap = getCartMapFromCookie();

    document.querySelectorAll('.variation-item').forEach(item => {
        const color = item.dataset.color;
        const size = item.dataset.size;
        const variationId = item.dataset.variationId;
        const colorMatch = !selectedColor || color === selectedColor;
        const sizeMatch = !selectedSize || size === selectedSize;

        if (colorMatch && sizeMatch) {
            document.getElementById('price').textContent = formatPrice(item.querySelector('.price-data').textContent);
            const stock = parseInt(item.querySelector('.stock-data').textContent);
            const sold = item.querySelector('.sold-data').textContent;
            document.getElementById('stock').textContent = formatNumber(stock);
            document.getElementById('sold').textContent = formatNumber(sold);

            const inCartQty = cartMap.get(parseInt(variationId)) || 0;
            maxStock = Math.max(0, stock - inCartQty);

            document.getElementById('cart-quantity').value = 1;
            found = true;
        }
    });

    if (!found) {
        ['price', 'stock', 'sold'].forEach(id => {
            const el = document.getElementById(id);
            if (el) el.textContent = 'N/A';
        });
        maxStock = 1;
    }

    if (addToCartBtn) {
        addToCartBtn.disabled = !found || maxStock === 0;
    }
}

function getSelectedVariationId() {
    let selectedId = null;
    document.querySelectorAll('.variation-item').forEach(item => {
        const color = item.dataset.color;
        const size = item.dataset.size;
        const colorMatch = !selectedColor || color === selectedColor;
        const sizeMatch = !selectedSize || size === selectedSize;

        if (colorMatch && sizeMatch && !selectedId) {
            selectedId = item.getAttribute('data-variation-id');
        }
    });
    return selectedId;
}

document.addEventListener('DOMContentLoaded', function () {
    // Toggle view more
    document.querySelectorAll('.view-toggle').forEach(toggle => {
        toggle.addEventListener('click', function () {
            const parent = toggle.closest('p');
            const shortText = parent.querySelector('.short-text');
            const fullText = parent.querySelector('.full-text');
            const isExpanded = fullText.style.display !== 'none';

            fullText.style.display = isExpanded ? 'none' : 'inline';
            shortText.style.display = isExpanded ? 'inline' : 'none';
            toggle.textContent = isExpanded ? 'View More' : 'View Less';
        });
    });

    // Color/Size Selection
    document.querySelectorAll('.color-option').forEach(button => {
        button.addEventListener('click', function () {
            document.querySelectorAll('.color-option').forEach(btn => btn.classList.remove('selected'));
            this.classList.add('selected');
            selectedColor = this.dataset.color;
            updateVariationInfo();
        });
    });

    document.querySelectorAll('.size-option').forEach(button => {
        button.addEventListener('click', function () {
            document.querySelectorAll('.size-option').forEach(btn => btn.classList.remove('selected'));
            this.classList.add('selected');
            selectedSize = this.dataset.size;
            updateVariationInfo();
        });
    });

    // Quantity + / -
    document.querySelectorAll('.qty-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const qtyInput = document.getElementById('cart-quantity');
            let quantity = parseInt(qtyInput.value) || 1;

            if (this.classList.contains('plus')) {
                if (quantity < maxStock) {
                    qtyInput.value = quantity + 1;
                } else {
                    alert("You cannot add more than the available stock.");
                }
            } else if (this.classList.contains('minus')) {
                if (quantity > 1) {
                    qtyInput.value = quantity - 1;
                }
            }
        });
    });

    // Quantity manual input
    document.getElementById('cart-quantity')?.addEventListener('input', function () {
        let value = parseInt(this.value);
        const addToCartBtn = document.getElementById('add-to-cart-btn');

        if (isNaN(value) || value < 1) {
            this.value = 1;
        } else if (value > maxStock) {
            alert("You can only add up to " + maxStock + " item(s).");
            this.value = maxStock;
        }

        // Disable button if maxStock is 0
        if (addToCartBtn) {
            addToCartBtn.disabled = maxStock === 0;
        }
    });

    // Add to cart
    document.getElementById('add-to-cart-btn')?.addEventListener('click', function () {
        const variationId = getSelectedVariationId();
        const quantity = parseInt(document.getElementById('cart-quantity')?.value) || 1;

        if (!variationId) {
            alert("Please select a variation before adding to cart.");
            return;
        }

        if (quantity > maxStock) {
            alert("Quantity exceeds available stock.");
            return;
        }

        fetch(contextPath + "/add-to-cart", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                variationId: variationId,
                quantity: quantity
            })
        })
            .then(res => {
                if (!res.ok) throw new Error();
                return res.text();
            })
            .then(text => {
                alert("Added to cart successfully!");
                updateVariationInfo();
            })
            .catch(err => alert("Failed to add to cart. Please try again later."));
    });
});

document.querySelectorAll('.variation-item').forEach(item => {
    const qty = parseInt(item.querySelector('.stock-data').textContent);
    if (qty === 0) {
        const colorId = item.dataset.color;
        const sizeId = item.dataset.size;

        // Disable color buttons
        document.querySelectorAll(`[data-color="${colorId}"]`).forEach(btn => {
            btn.disabled = true;
            btn.classList.add('out-of-stock');
        });

        // Disable size buttons
        document.querySelectorAll(`[data-size="${sizeId}"]`).forEach(btn => {
            btn.disabled = true;
            btn.classList.add('out-of-stock');
        });
    }
});

