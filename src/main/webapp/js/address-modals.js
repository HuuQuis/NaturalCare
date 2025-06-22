let addresses = [];

function loadProvinces() {
    fetch('https://provinces.open-api.vn/api/p/')
        .then(res => res.json())
        .then(provinces => {
            const provinceSelect = document.getElementById('provinceSelect');
            provinceSelect.innerHTML = '<option value="">-- Select Province --</option>';
            provinces.forEach(p => {
                const paddedCode = String(p.code).padStart(2, '0'); // ví dụ: 1 -> "01"
                provinceSelect.add(new Option(p.name, paddedCode));
            });
        });
}

function openAddressModal() {
    document.getElementById('addressModal').style.display = 'block';
    document.body.classList.add('modal-open');
    document.body.style.top = `-${window.scrollY}px`;
    loadAddresses();
}

function closeAddressModal() {
    document.getElementById('addressModal').style.display = 'none';
    document.body.classList.remove('modal-open');
    window.scrollTo(0, parseInt(document.body.style.top || '0') * -1);
    document.body.style.top = '';
}

function addNewAddress() {
    document.getElementById('addAddressModal').style.display = 'block';
    document.body.classList.add('modal-open');
    loadProvinces();
}

function closeAddAddressModal() {
    document.getElementById('addAddressModal').style.display = 'none';
    document.body.classList.remove('modal-open');
    document.getElementById('addAddressForm').reset();
    document.getElementById('districtSelect').innerHTML = '';
    document.getElementById('wardSelect').innerHTML = '';
}

function loadAddresses() {
    showLoading(true);
    clearMessages();

    fetch('address?action=list')
        .then(res => res.json())
        .then(data => {
            showLoading(false);
            addresses = data.addresses || [];
            renderAddresses(addresses);
        })
        .catch(() => {
            showLoading(false);
            showError("Failed to load addresses.");
        });
}

function renderAddresses(list) {
    const container = document.getElementById('addressListContainer');
    const emptyState = document.getElementById('emptyState');
    container.innerHTML = '';
    if (list.length === 0) {
        emptyState.style.display = 'block';
        return;
    }
    emptyState.style.display = 'none';
    list.forEach(addr => container.appendChild(createAddressCard(addr)));
}

function createAddressCard(address) {
    const card = document.createElement('div');
    card.className = 'address-card';
    card.innerHTML = `
        <div class="address-details">
            <div class="address-text">
                ${address.detail}<br>
                <span class="ward-district-province">Loading...</span>
            </div>
            <div><strong>Distance:</strong> ${address.distanceKm} km</div>
        </div>
        <div class="address-actions">
            <button class="btn-address secondary" onclick="deleteAddress('${address.addressId}')">Delete</button>
        </div>
    `;
    fetch(`https://provinces.open-api.vn/api/w/${address.wardCode}?depth=2`)
        .then(res => res.json())
        .then(ward => {
            card.querySelector('.ward-district-province').textContent =
                `${ward.name}, ${ward.district.name}, ${ward.district.province.name}`;
        })
        .catch(() => {
            card.querySelector('.ward-district-province').textContent = "Unknown location";
        });
    return card;
}

function deleteAddress(id) {
    if (!confirm('Are you sure?')) return;
    fetch('address?action=delete&addressId=' + id)
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                showSuccess(data.message);
                loadAddresses();
            } else {
                showError(data.message);
            }
        })
        .catch(() => showError('Failed to delete address.'));
}

function showLoading(show) {
    document.getElementById('loadingState').style.display = show ? 'block' : 'none';
    document.getElementById('addressListContainer').style.display = show ? 'none' : 'block';
    document.getElementById('emptyState').style.display = 'none';
}

function showSuccess(msg) {
    const box = document.getElementById('messageContainer');
    box.innerHTML = `<div class="success-message">${msg}</div>`;
    setTimeout(clearMessages, 5000);
}

function showError(msg) {
    const box = document.getElementById('messageContainer');
    box.innerHTML = `<div class="error-message">${msg}</div>`;
    setTimeout(clearMessages, 5000);
}

function clearMessages() {
    document.getElementById('messageContainer').innerHTML = '';
}

document.addEventListener('DOMContentLoaded', () => {
    const provinceSelect = document.getElementById('provinceSelect');
    const districtSelect = document.getElementById('districtSelect');
    const wardSelect = document.getElementById('wardSelect');
    const addAddressForm = document.getElementById('addAddressForm');

    if (provinceSelect) {
        provinceSelect.addEventListener('change', function () {
            fetch(`https://provinces.open-api.vn/api/p/${this.value}?depth=2`)
                .then(res => res.json())
                .then(data => {
                    districtSelect.innerHTML = '<option value="">-- Select District --</option>';
                    data.districts.forEach(d => {
                        const paddedCode = String(d.code).padStart(3, '0'); // ví dụ: 1 -> "001"
                        districtSelect.add(new Option(d.name, paddedCode));
                    });
                    wardSelect.innerHTML = '';
                });
        });

    }

    if (districtSelect) {
        districtSelect.addEventListener('change', function () {
            fetch(`https://provinces.open-api.vn/api/d/${this.value}?depth=2`)
                .then(res => res.json())
                .then(data => {
                    wardSelect.innerHTML = '<option value="">-- Select Ward --</option>';
                    data.wards.forEach(w => {
                        const paddedCode = String(w.code).padStart(5, '0'); // ví dụ: 1 -> "00001"
                        wardSelect.add(new Option(w.name, paddedCode));
                    });
                });
        });

    }

    if (addAddressForm) {
        addAddressForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const formData = new FormData(this);
            if (!formData.get('provinceCode') || !formData.get('districtCode') || !formData.get('wardCode') || !formData.get('detail')) {
                showError("Please fill all fields.");
                return;
            }

            fetch('address?action=add', {
                method: 'POST',
                body: formData
            })
                .then(res => res.json())
                .then(data => {
                    if (data.success) {
                        showSuccess(data.message);
                        closeAddAddressModal();
                        loadAddresses();
                    } else {
                        showError(data.message);
                    }
                })
                .catch(() => showError("Error occurred while adding address."));
        });
    }
});

// Make global for inline onclick attributes
window.openAddressModal = openAddressModal;
window.closeAddressModal = closeAddressModal;
window.addNewAddress = addNewAddress;
window.closeAddAddressModal = closeAddAddressModal;
window.deleteAddress = deleteAddress;
