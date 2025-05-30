function searchByName(input) {
    var searchList = document.getElementById("searchList");
    var txtSearch = input.value.trim();

    if (txtSearch.length === 0) {
        searchList.style.display = "none";
        return;
    }

    $.ajax({
        url: contextPath + "/search",
        type: "GET",
        data: {
            txt: txtSearch
        },
        success: function(data) {
            searchList.innerHTML = data;
            searchList.style.display = "block";
        },
        error: function() {
            searchList.innerHTML = "Error loading results";
        }
    });
}

function selectProduct(productId) {
    window.location.href = contextPath + "/productDetail?product_id=" + productId;
}

// Close search list when clicking outside
document.addEventListener('click', function(e) {
    var searchList = document.getElementById("searchList");
    var searchInput = document.getElementById("searchInput");
    if (e.target !== searchInput && !searchList.contains(e.target)) {
        searchList.style.display = "none";
    }
});
