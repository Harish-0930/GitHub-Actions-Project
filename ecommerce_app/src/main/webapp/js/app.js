// ===== Cart State =====
const CART_KEY = 'ecommerce_cart';

const PRODUCTS = [
    { id: 1, name: 'Gaming Laptop Pro', category: 'Laptops', price: 1299.99, oldPrice: 1499.99, icon: '💻', bg: '#EFF6FF', badge: 'Sale', rating: 4.8, reviews: 124 },
    { id: 2, name: 'Wireless Headphones', category: 'Audio', price: 149.99, oldPrice: null, icon: '🎧', bg: '#F0FDF4', badge: 'New', rating: 4.6, reviews: 89 },
    { id: 3, name: 'Smartphone X15', category: 'Phones', price: 799.99, oldPrice: 899.99, icon: '📱', bg: '#FFF7ED', badge: 'Sale', rating: 4.7, reviews: 256 },
    { id: 4, name: 'Mechanical Keyboard', category: 'Accessories', price: 89.99, oldPrice: null, icon: '⌨️', bg: '#F5F3FF', badge: 'New', rating: 4.5, reviews: 67 },
    { id: 5, name: '4K Monitor 27"', category: 'Monitors', price: 449.99, oldPrice: 549.99, icon: '🖥️', bg: '#FFF1F2', badge: 'Sale', rating: 4.9, reviews: 143 },
    { id: 6, name: 'Wireless Mouse', category: 'Accessories', price: 39.99, oldPrice: null, icon: '🖱️', bg: '#ECFEFF', badge: null, rating: 4.3, reviews: 198 },
    { id: 7, name: 'HD Webcam Pro', category: 'Cameras', price: 69.99, oldPrice: null, icon: '📷', bg: '#FFF7ED', badge: 'New', rating: 4.4, reviews: 52 },
    { id: 8, name: 'USB-C Hub 7-in-1', category: 'Accessories', price: 49.99, oldPrice: 59.99, icon: '🔌', bg: '#F0FDF4', badge: 'Sale', rating: 4.2, reviews: 88 }
];

const COUPONS = { SAVE10: 10, SAVE20: 20, WELCOME5: 5, SUMMER15: 15 };

// ===== Cart Functions =====
function getCart() {
    try {
        return JSON.parse(localStorage.getItem(CART_KEY)) || [];
    } catch {
        return [];
    }
}

function saveCart(cart) {
    localStorage.setItem(CART_KEY, JSON.stringify(cart));
    updateCartCount();
}

function addToCart(productId) {
    const product = PRODUCTS.find(p => p.id === productId);
    if (!product) return;
    const cart = getCart();
    const existing = cart.find(item => item.id === productId);
    if (existing) {
        existing.qty += 1;
    } else {
        cart.push({ id: product.id, name: product.name, category: product.category, price: product.price, icon: product.icon, bg: product.bg, qty: 1 });
    }
    saveCart(cart);
    showToast(`${product.name} added to cart!`, 'success');
}

function removeFromCart(productId) {
    const cart = getCart().filter(item => item.id !== productId);
    saveCart(cart);
}

function updateQty(productId, delta) {
    const cart = getCart();
    const item = cart.find(i => i.id === productId);
    if (!item) return;
    item.qty = Math.max(1, item.qty + delta);
    saveCart(cart);
    renderCart();
}

function getCartTotal(cart) {
    return cart.reduce((sum, item) => sum + item.price * item.qty, 0);
}

function getCartCount() {
    return getCart().reduce((sum, item) => sum + item.qty, 0);
}

function updateCartCount() {
    const count = getCartCount();
    document.querySelectorAll('.cart-count').forEach(el => {
        el.textContent = count;
    });
}

// ===== Toast =====
function showToast(message, type = '') {
    let toast = document.getElementById('toast');
    if (!toast) {
        toast = document.createElement('div');
        toast.id = 'toast';
        toast.className = 'toast';
        document.body.appendChild(toast);
    }
    toast.textContent = message;
    toast.className = `toast ${type} show`;
    clearTimeout(toast._timer);
    toast._timer = setTimeout(() => { toast.className = `toast ${type}`; }, 3000);
}

// ===== Render Products (products.html & index.html) =====
function renderProducts(containerId, limit) {
    const container = document.getElementById(containerId);
    if (!container) return;
    const list = limit ? PRODUCTS.slice(0, limit) : PRODUCTS;
    container.innerHTML = list.map(p => `
        <div class="product-card">
            <div class="product-image" style="background:${p.bg}">
                ${p.badge ? `<span class="product-badge${p.badge === 'New' ? ' new' : ''}">${p.badge}</span>` : ''}
                <span style="font-size:4rem">${p.icon}</span>
            </div>
            <div class="product-info">
                <div class="product-category">${p.category}</div>
                <div class="product-name">${p.name}</div>
                <div class="product-rating">
                    <span class="stars">${'★'.repeat(Math.floor(p.rating))}${'☆'.repeat(5 - Math.floor(p.rating))}</span>
                    <span class="rating-count">(${p.reviews})</span>
                </div>
                <div class="product-footer">
                    <div class="product-price">
                        ${p.oldPrice ? `<span class="old-price">$${p.oldPrice.toFixed(2)}</span>` : ''}
                        $${p.price.toFixed(2)}
                    </div>
                    <button class="add-to-cart-btn" onclick="handleAddToCart(${p.id}, this)">Add to Cart</button>
                </div>
            </div>
        </div>
    `).join('');
}

function handleAddToCart(id, btn) {
    addToCart(id);
    btn.textContent = '✓ Added';
    btn.classList.add('added');
    setTimeout(() => { btn.textContent = 'Add to Cart'; btn.classList.remove('added'); }, 1500);
}

// ===== Render Cart (cart.html) =====
function renderCart() {
    const cart = getCart();
    const tableBody = document.getElementById('cart-body');
    const emptyState = document.getElementById('cart-empty');
    const cartTable = document.getElementById('cart-table');
    if (!tableBody) return;

    if (cart.length === 0) {
        if (emptyState) emptyState.style.display = 'block';
        if (cartTable) cartTable.style.display = 'none';
        updateSummary(0, 0);
        return;
    }

    if (emptyState) emptyState.style.display = 'none';
    if (cartTable) cartTable.style.display = 'block';

    tableBody.innerHTML = cart.map(item => `
        <tr>
            <td>
                <div class="cart-item-info">
                    <div class="cart-item-img" style="background:${item.bg}">${item.icon}</div>
                    <div>
                        <div class="cart-item-name">${item.name}</div>
                        <div class="cart-item-category">${item.category}</div>
                    </div>
                </div>
            </td>
            <td>$${item.price.toFixed(2)}</td>
            <td>
                <div class="qty-control">
                    <button class="qty-btn" onclick="updateQty(${item.id}, -1)">−</button>
                    <span class="qty-value">${item.qty}</span>
                    <button class="qty-btn" onclick="updateQty(${item.id}, 1)">+</button>
                </div>
            </td>
            <td><strong>$${(item.price * item.qty).toFixed(2)}</strong></td>
            <td><button class="remove-btn" onclick="handleRemove(${item.id})">✕ Remove</button></td>
        </tr>
    `).join('');

    const subtotal = getCartTotal(cart);
    updateSummary(subtotal, currentDiscount);
}

let currentDiscount = 0;

function updateSummary(subtotal, discountPercent) {
    const discountAmount = subtotal * discountPercent / 100;
    const afterDiscount = subtotal - discountAmount;
    const tax = afterDiscount * 0.08;
    const shipping = subtotal > 0 ? (subtotal >= 500 ? 0 : 9.99) : 0;
    const total = afterDiscount + tax + shipping;

    setText('summary-subtotal', `$${subtotal.toFixed(2)}`);
    setText('summary-discount', discountPercent > 0 ? `-$${discountAmount.toFixed(2)}` : '$0.00');
    setText('summary-tax', `$${tax.toFixed(2)}`);
    setText('summary-shipping', shipping === 0 && subtotal > 0 ? 'FREE' : `$${shipping.toFixed(2)}`);
    setText('summary-total', `$${total.toFixed(2)}`);
}

function setText(id, value) {
    const el = document.getElementById(id);
    if (el) el.textContent = value;
}

function handleRemove(id) {
    removeFromCart(id);
    renderCart();
    showToast('Item removed from cart');
}

function applyCoupon() {
    const input = document.getElementById('coupon-input');
    const msg = document.getElementById('coupon-msg');
    if (!input || !msg) return;
    const code = input.value.trim().toUpperCase();
    if (COUPONS[code]) {
        currentDiscount = COUPONS[code];
        msg.textContent = `✓ Coupon applied! ${currentDiscount}% off your order.`;
        msg.className = 'coupon-msg success';
        renderCart();
        showToast(`Coupon ${code} applied!`, 'success');
    } else {
        msg.textContent = '✕ Invalid coupon code. Try SAVE10, SAVE20, WELCOME5, or SUMMER15.';
        msg.className = 'coupon-msg error';
    }
}

// ===== Contact Form =====
function handleContactSubmit(e) {
    e.preventDefault();
    showToast('Message sent! We will get back to you soon.', 'success');
    e.target.reset();
}

// ===== Init =====
document.addEventListener('DOMContentLoaded', () => {
    updateCartCount();
    renderProducts('products-grid');
    renderProducts('featured-grid', 4);
    renderCart();

    const contactForm = document.getElementById('contact-form');
    if (contactForm) contactForm.addEventListener('submit', handleContactSubmit);

    const couponApplyBtn = document.getElementById('coupon-apply-btn');
    if (couponApplyBtn) couponApplyBtn.addEventListener('click', applyCoupon);

    const couponInputEl = document.getElementById('coupon-input');
    if (couponInputEl) {
        couponInputEl.addEventListener('keydown', e => { if (e.key === 'Enter') applyCoupon(); });
    }
});
