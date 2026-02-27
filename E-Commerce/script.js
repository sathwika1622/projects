
function getCart() {
let cart = localStorage.getItem("cart");
return cart ? JSON.parse(cart) : [];
}

function saveCart(cart) {
localStorage.setItem("cart", JSON.stringify(cart));
}

function addToCart(id, name, price) {


let cart = getCart();
let item = cart.find(p => p.id === id);

if (item) {
    item.quantity++;
} else {
    cart.push({
        id: id,
        name: name,
        price: price,
        quantity: 1
    });
}

saveCart(cart);
alert("Item added to cart!");


}

function displayCart() {


let cart = getCart();
let table = document.getElementById("cartTable");

if (!table) return; // prevents error on index page

let total = 0;

cart.forEach((item, index) => {

    let row = table.insertRow();

    row.insertCell(0).innerText = item.name;
    row.insertCell(1).innerText = "₹" + item.price;

    let qtyCell = row.insertCell(2);
    qtyCell.innerHTML =
        `<input type="number" value="${item.quantity}" min="1"
        onchange="updateQuantity(${index}, this.value)">`;

    let itemTotal = item.price * item.quantity;
    row.insertCell(3).innerText = "₹" + itemTotal;

    row.insertCell(4).innerHTML =
        `<button onclick="removeItem(${index})">Delete</button>`;

    total += itemTotal;
});

let grand = document.getElementById("grandTotal");
if (grand) grand.innerText = "Total: ₹" + total;

}

function updateQuantity(index, value) {


let cart = getCart();
cart[index].quantity = parseInt(value);

saveCart(cart);
location.reload();

}

function removeItem(index) {


let cart = getCart();
cart.splice(index, 1);

saveCart(cart);
location.reload();

}