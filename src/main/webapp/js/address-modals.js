let addresses = [];
let editingId = null;

function loadProvinces() {
    fetch('https://provinces.open-api.vn/api/p/')
        .then(res => res.json())
        .then(provinces => {
            const provinceSelect = document.getElementById('provinceSelect');
            provinceSelect.innerHTML = '<option value="">-- Select Province --</option>';
            provinces.forEach(p => provinceSelect.add(new Option(p.name, p.code)));
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
    closeAddressModal();
    document.getElementById('addAddressModal').style.display = 'block';
    document.body.classList.add('modal-open');
    loadProvinces();
}

function closeAddAddressModal() {
    if (isAddAddressFormDirty()) {
        const confirmLeave = confirm("You have unsaved changes. Data will be lost. Do you want to go back?");
        if (!confirmLeave) return;
    }

    document.getElementById('addAddressModal').style.display = 'none';
    document.body.classList.remove('modal-open');
    document.getElementById('addAddressForm').reset();
    document.getElementById('districtSelect').innerHTML = '';
    document.getElementById('wardSelect').innerHTML = '';
    editingId = null;

    openAddressModal();
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
    card.setAttribute('data-id', address.addressId);

    const leafIconHtml = address.isDefault
        ? `<span class="default-leaf active" title="Default address">🍃</span>`
        : '';

    card.innerHTML = `
        <div class="address-details">
            <div class="address-header">
                <div class="address-text">
                    ${address.detail}<br>
                    <span class="ward-district-province">
                        ${address.wardName}, ${address.districtName}, ${address.provinceName}
                    </span>
                </div>
                ${leafIconHtml}
            </div>
            <div><strong>Distance:</strong> ${parseFloat(address.distanceKm).toFixed(2)} km</div>
        </div>
        <div class="address-actions">
            <button class="btn-address primary" onclick="editAddress('${address.addressId}')">Edit</button>
            <button class="btn-address secondary" onclick="deleteAddress('${address.addressId}')">Delete</button>
            ${!address.isDefault ? `
                <button class="btn-leaf" onclick="setDefaultAddress(${address.addressId})">
                    Set as default
                </button>
            ` : ''}
        </div>
    `;

    return card;
}

function setDefaultAddress(addressId) {
    fetch('address?action=setDefault&addressId=' + addressId, {
        method: 'POST'
    })
        .then(res => res.json())
        .then(data => {
            if (!data.success) return showError(data.message);

            addresses.forEach(addr => {
                addr.isDefault = addr.addressId === addressId;
            });

            const container = document.getElementById('addressListContainer');
            container.innerHTML = '';
            addresses.forEach(addr => container.appendChild(createAddressCard(addr)));

            const newDefaultCard = document.querySelector(`.address-card[data-id="${addressId}"]`);
            if (newDefaultCard) {
                const leaf = newDefaultCard.querySelector('.default-leaf');
                if (leaf) {
                    leaf.classList.add('active');
                }
            }

            showSuccess("Set default address successfully.");
        })
        .catch(() => showError("Failed to set default address."));
}

function editAddress(id) {
    fetch('address?action=get&addressId=' + id)
        .then(res => res.json())
        .then(data => {
            if (!data.success) {
                showError(data.message);
                return;
            }

            const address = data.address;
            editingId = address.addressId;

            document.getElementById('addressModal').style.display = 'none';
            document.getElementById('addAddressModal').style.display = 'block';
            document.body.classList.add('modal-open');

            document.getElementById('detail').value = address.detail;

            fetch('https://provinces.open-api.vn/api/p/')
                .then(res => res.json())
                .then(provinces => {
                    const provinceSelect = document.getElementById('provinceSelect');
                    provinceSelect.innerHTML = '<option value="">-- Select Province --</option>';
                    provinces.forEach(p => provinceSelect.add(new Option(p.name, p.code)));

                    provinceSelect.value = String(address.provinceCode);
                    return fetch(`https://provinces.open-api.vn/api/p/${address.provinceCode}?depth=2`);
                })
                .then(res => res.json())
                .then(provinceData => {
                    const districtSelect = document.getElementById('districtSelect');
                    districtSelect.innerHTML = '<option value="">-- Select District --</option>';
                    provinceData.districts.forEach(d => districtSelect.add(new Option(d.name, d.code)));

                    districtSelect.value = String(address.districtCode);
                    return fetch(`https://provinces.open-api.vn/api/d/${address.districtCode}?depth=2`);
                })
                .then(res => res.json())
                .then(districtData => {
                    const wardSelect = document.getElementById('wardSelect');
                    wardSelect.innerHTML = '<option value="">-- Select Ward --</option>';
                    districtData.wards.forEach(w => wardSelect.add(new Option(w.name, w.code)));

                    wardSelect.value = String(address.wardCode);
                })
                .catch(err => {
                    console.error("Error loading province/district/ward:", err);
                    showError("Failed to load location details.");
                });
        })
        .catch(err => {
            console.error("Failed to fetch address:", err);
            showError("Failed to load address details.");
        });
}

function deleteAddress(id) {
    if (!confirm('Are you sure to delete this address?')) return;

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
        .catch(() => showError("Failed to delete address."));
}

function isAddAddressFormDirty() {
    const province = document.getElementById('provinceSelect').value;
    const district = document.getElementById('districtSelect').value;
    const ward = document.getElementById('wardSelect').value;
    const detail = document.getElementById('detail').value.trim();
    return province || district || ward || detail;
}

function validateDetailClient(detail) {
    const trimmed = detail.trim();

    if (trimmed.length === 0) {
        return "Detail must not be empty or whitespace only.";
    }

    if (trimmed.length > 100) {
        return "Detail must not exceed 100 characters.";
    }

    const pattern = /^[a-zA-Z0-9À-ỹ\s]+$/;
    if (!pattern.test(trimmed)) {
        return "Detail can only contain letters, numbers, and spaces.";
    }

    return null;
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
    const err = document.createElement('div');
    err.className = 'error-message';
    err.textContent = msg;

    document.body.appendChild(err);

    setTimeout(() => {
        err.remove();
    }, 3000);
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
                    data.districts.forEach(d => districtSelect.add(new Option(d.name, d.code)));
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
                    data.wards.forEach(w => wardSelect.add(new Option(w.name, w.code)));
                });
        });
    }

    if (addAddressForm) {
        addAddressForm.addEventListener('submit', function (e) {
            e.preventDefault();

            const formData = new FormData(this);
            const province = formData.get('provinceCode');
            const district = formData.get('districtCode');
            const ward = formData.get('wardCode');
            const detail = formData.get('detail');

            if (!province || !district || !ward || !detail) {
                showError("Please fill all fields.");
                return;
            }

            const validationMsg = validateDetailClient(detail);
            if (validationMsg) {
                showError(validationMsg);
                document.getElementById('detail').focus();
                return;
            }

            const action = editingId ? 'update' : 'add';
            if (editingId) formData.append('addressId', editingId);

            fetch('address?action=' + action, {
                method: 'POST',
                body: formData
            })
                .then(res => res.json())
                .then(data => {
                    if (data.success) {
                        showSuccess(data.message);
                        document.getElementById('addAddressForm').reset();
                        document.getElementById('districtSelect').innerHTML = '';
                        document.getElementById('wardSelect').innerHTML = '';
                        closeAddAddressModal();
                        openAddressModal();
                        editingId = null;
                    } else {
                        showError(data.message);
                    }
                })
                .catch(() => showError("Error occurred while adding address."));
        });
    }
});

// Expose functions for inline use in HTML
window.openAddressModal = openAddressModal;
window.closeAddressModal = closeAddressModal;
window.addNewAddress = addNewAddress;
window.closeAddAddressModal = closeAddAddressModal;
window.deleteAddress = deleteAddress;
