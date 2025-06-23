let addresses = [];
let editingId = null;

function loadProvinces() {
    fetch('https://provinces.open-api.vn/api/p/')
        .then(res => res.json())
        .then(provinces => {
            const provinceSelect = document.getElementById('provinceSelect');
            provinceSelect.innerHTML = '<option value="">-- Select Province --</option>';
            provinces.forEach(p => {
                provinceSelect.add(new Option(p.name, p.code));
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

    // Quay lại address list
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
    card.innerHTML = `
        <div class="address-details">
            <div class="address-text">
                ${address.detail}<br>
                <span class="ward-district-province">
                    ${address.wardName}, ${address.districtName}, ${address.provinceName}
                </span>
            </div>
            <div><strong>Distance:</strong> ${address.distanceKm} km</div>
        </div>
        <div class="address-actions">
            <button class="btn-address primary" onclick="editAddress('${address.addressId}')">Edit</button>
            <button class="btn-address secondary" onclick="deleteAddress('${address.addressId}')">Delete</button>
        </div>
    `;
    return card;
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

            // 1. Hiển thị modal form
            document.getElementById('addressModal').style.display = 'none';
            document.getElementById('addAddressModal').style.display = 'block';
            document.body.classList.add('modal-open');

            // 2. Gán detail
            document.getElementById('detail').value = address.detail;

            // 3. Load provinces
            fetch('https://provinces.open-api.vn/api/p/')
                .then(res => res.json())
                .then(provinces => {
                    const provinceSelect = document.getElementById('provinceSelect');
                    provinceSelect.innerHTML = '<option value="">-- Select Province --</option>';
                    provinces.forEach(p => provinceSelect.add(new Option(p.name, p.code)));

                    provinceSelect.value = String(address.provinceCode);

                    // 4. Load districts của province đã chọn
                    return fetch(`https://provinces.open-api.vn/api/p/${address.provinceCode}?depth=2`);
                })
                .then(res => res.json())
                .then(provinceData => {
                    if (!provinceData.districts || provinceData.districts.length === 0) {
                        throw new Error("Districts not found in province data.");
                    }

                    const districtSelect = document.getElementById('districtSelect');
                    districtSelect.innerHTML = '<option value="">-- Select District --</option>';
                    provinceData.districts.forEach(d => districtSelect.add(new Option(d.name, d.code)));

                    districtSelect.value = String(address.districtCode);

                    // 5. Load wards của district đã chọn
                    return fetch(`https://provinces.open-api.vn/api/d/${address.districtCode}?depth=2`);
                })
                .then(res => res.json())
                .then(districtData => {
                    if (!districtData.wards || districtData.wards.length === 0) {
                        throw new Error("Wards not found in district data.");
                    }

                    const wardSelect = document.getElementById('wardSelect');
                    wardSelect.innerHTML = '<option value="">-- Select Ward --</option>';
                    districtData.wards.forEach(w => wardSelect.add(new Option(w.name, w.code)));

                    wardSelect.value = String(address.wardCode);
                })
                .catch(err => {
                    console.error("Error during province/district/ward loading:", err);
                    showError("Failed to load province/district/ward details.");
                });

        })
        .catch(err => {
            console.error("Failed to fetch address details:", err);
            showError('Failed to load address details.');
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
        .catch(() => showError('Failed to delete address.'));
}

function isAddAddressFormDirty() {
    const province = document.getElementById('provinceSelect').value;
    const district = document.getElementById('districtSelect').value;
    const ward = document.getElementById('wardSelect').value;
    const detail = document.getElementById('detail').value.trim();

    return province || district || ward || detail;
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
            if (!formData.get('provinceCode') || !formData.get('districtCode') || !formData.get('wardCode') || !formData.get('detail')) {
                showError("Please fill all fields.");
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

                        // Reset thủ công các trường
                        document.getElementById('addAddressForm').reset();
                        document.getElementById('districtSelect').innerHTML = '';
                        document.getElementById('wardSelect').innerHTML = '';

                        closeAddAddressModal();
                        openAddressModal();
                        editingId = null;
                    }else {
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
