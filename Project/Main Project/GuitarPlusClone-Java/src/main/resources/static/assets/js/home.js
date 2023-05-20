let listUsers = JSON.parse(localStorage.getItem("list_user"));
// let currentUser = JSON.parse(localStorage.getItem("current_user"));

// function showListProducts() {
//     let draw = "";
//     let link;
//     if (currentUser !== null) {
//         link = "javascript:void(0)"; // disable thẻ a. ko bị nhảy, không chuyển trang
//     } else {
//         link = "javascript:void(0)";
//     }
//     for (let i = 0; i < listProducts.length; i++) {
//         draw += `<div class="grid__column-12">
//                         <div class="best-sale-products-item-wrap">
//                             <div style="background-image: url(${listProducts[i].img
//         })"
//                                  class="best-sale-products-img">
//
//                             </div>
//                             <div class="best-sale-products-desc">
//                                 <h3 class="best-sale-products__title name-products">${listProducts[i].name
//         }</h3>
//                                 <h3 class="best-sale-products__price price">${listProducts[
//             i
//             ].price
//             .toString()
//             .replace(
//                 /\B(?=(\d{3})+(?!\d))/g,
//                 "."
//             )} <span>đ</span></h3>
//                             </div>
//                             <a id="${listProducts[i].id
//         }" class="best-sale-products__link cart-link add-cart" href=${link} onclick="getIdProducts(${listProducts[i].id
//         })">
//                                 <div class="best-sale-products__extention">
//                                     <span>Thêm vào giỏ hàng</span>
//                                 </div>
//                             </a>
//                         </div>
//                     </div>`;
//     }
//     document.querySelector(".draw").innerHTML = draw;
// }
//
// showListProducts();

// Kiểm tra đăng nhập

// if (currentUser !== null) {
//     document.getElementById("account").innerHTML =
//         currentUser.firstName + " " + currentUser.lastName;
//     document.querySelector(".account-info").innerHTML =
//         "Thông tin tài khoản";
//     document.querySelector(".account-info").href =
//         "account-information.html";
//     document.querySelector(".account-cart").innerHTML = "Giỏ hàng";
//     document.querySelector(".logOut").innerHTML = "Đăng xuất";
//     document.querySelector(".logOut").style.color = "red";
//     document.querySelector(".account-cart").href =
//         "cart-products.html";
// } else {
//     document.querySelector(".logOut").style.display = "none";
//     document.getElementById("cart-product").href = "login.html";
//     document.getElementById("mini-cart-product").href =
//         "login.html";
// }

// Lấy giỏ hàng của User về cartList

let cartList = JSON.parse(localStorage.getItem("cartList"));
if (cartList == null) {
    cartList = [];
}

// function getIdProducts(id) {
//     if (currentUser !== null) {
//         let product = findById(id);
//         let status = false;
//         showToast("Thêm vào giỏ hàng thành công", "fa-regular fa-circle-check");
//         for (let i = 0; i < cartList.length; i++) {
//             if (
//                 cartList[i].id == id &&
//                 cartList[i].userId == currentUser.id
//             ) {
//                 cartList[i].quantity += 1;
//                 status = true;
//                 break;
//             }
//         }
//         if (status == false) {
//             cartList.push({
//                 userId: currentUser.id,
//                 id: id,
//                 price: product.price,
//                 name: product.name,
//                 quantity: 1,
//                 image: product.img,
//             });
//         }
//         localStorage.setItem("cartList", JSON.stringify(cartList));
//         countProducts();
//         showMiniCart();
//     } else {
//         showToast("Vui lòng đăng nhập!", "fa-solid fa-circle-exclamation");
//     }
// }

function findById(id) {
    for (let i = 0; i < listProducts.length; i++) {
        if (listProducts[i].id == id) {
            return listProducts[i];
        }
    }
    return null;
}

// Hiển thị tổng sản phẩm trong giỏ hàng

// Lấy ra sản phẩm trong giỏ hàng theo id, push vào mảng, để tính toán và vẽ

function getCartUser() {
    let cartCurrentUser = [];
    if (currentUser !== null) {
        for (let i = 0; i < cartList.length; i++) {
            if (cartList[i].userId == currentUser.id) {
                cartCurrentUser.push(cartList[i]);
            }
        }
    }
    return cartCurrentUser;
}

// Tính tổng sản phẩm trong giỏ hàng "countProducts", sản phẩm còn lại trong giỏ "remainP"

// function countProducts() {
//     let remainProduct = 0;
//     let countProducts = 0;
//     let cartCurrentUser = getCartUser();
//     if (currentUser !== null) {
//         for (let i = 0; i < cartCurrentUser.length; i++) {
//             if (cartCurrentUser[i].userId == currentUser.id) {
//                 countProducts += cartCurrentUser[i].quantity;
//             }
//         }
//     }
//     if (cartCurrentUser.length < 4) {
//         remainProduct = 0;
//     } else {
//         remainProduct = countProducts - 4;
//     }
//
//     document.getElementById("count").innerHTML = countProducts;
//     document.getElementById("productsRemain").innerHTML =
//         remainProduct;
// }

countProducts();

//  Vẽ minicart

function showMiniCart() {
    let size;
    let showMiniCart = "";
    let cartCurrentUser = getCartUser();

    if (currentUser !== null) {
        size =
            cartCurrentUser.length >= 4
                ? 4
                : cartCurrentUser.length;
        for (let i = 0; i < size; i++) {
            showMiniCart += `<a href="cart-products.html" class="mini-cart-item">
                                                <div class="mini-cart-products">
                                                    <div class="mini-cart-img-contain">
                                                        <img src=${cartCurrentUser[i]
                .image
            } alt="">
                                                    </div>
                                                    <h3>${cartCurrentUser[i].name
            }</h3>
                                                </div>
                                                <div class="mini-cart-price">
                                                    <p>${formatNumber(
                cartCurrentUser[i].price
            )}</p>
                                                </div>
                                            </a>`;
        }
    }
    document.getElementById("showMiniCart").innerHTML =
        showMiniCart;
}

showMiniCart();

function formatNumber(number) {
    return new Intl.NumberFormat("de-DE", {
        style: "currency",
        currency: "VND",
    }).format(number);
}

// Show toasts
// function showToast(alert, icon) {
//     let toast = document.createElement("div");
//     toast.classList.add("toast");
//
//     const autoRemove = setTimeout(function () {
//         toast.remove();
//     }, 4000);
//
//     toast.style.animation = "slideIn ease .3s";
//     toast.innerHTML = `<div class="toast-message">
//                             <div class="toast-icon">
//                                 <i class="${icon}"></i>
//                             </div>
//                             <div class="toast-body">
//                                 <h3>
//                                     ${alert}
//                                 </h3>
//                             </div>
//                             <div onclick="closeToast()" class="toast-close">
//                                 <i class="fa-solid fa-xmark"></i>
//                             </div>
//                         </div>`;
//
//     const toastList = document.querySelector("#toast");
//     toastList.appendChild(toast);
//
//     setTimeout(function () {
//         toast.style.animation = `fadeOut ease 3s forwards`;
//     }, 2000);
// }

// Đăng xuất

function logOut() {
    localStorage.removeItem("current_user");
    localStorage.removeItem("idKey");
    location.reload();
}
