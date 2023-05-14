class Products {
    id;
    img;
    name;
    price;
    idUser;
    constructor(id, img, name, price) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        // this.idUser = idUser;
    }

    setID(id) {
        this.id = id;
    }

    getID() {
        return this.id;
    }

    setImg(img) {
        this.img = img;
    }

    getImg() {
        return this.img;
    }

    setName(name) {
        this.name = name;
    }

    getName() {
        return this.name;
    }

    setPrice(price) {
        this.price = price;
    }

    getPrice() {
        return this.price;
    }

    // setIdUser(idUser)  {
    //     this.idUser = idUser;
    // }

    // getIdUser() {
    //     return this.idUser;
    // }
}

let pd_1 = new Products(1, "https://product.hstatic.net/200000388065/product/you_0072_80e8a8dccc7547d9b77fcf01a5ebec48_master.jpg", "GUITAR PLUS F0 PERFORMER DC", 7000000);
let pd_2 = new Products(2, "https://product.hstatic.net/200000388065/product/you_0113_87019b2cd0b943c69fc75b054dddcd04_master.jpg", "GUITAR PLUS F0 PERFORMER AC", 7000000);
let pd_3 = new Products(3, "https://product.hstatic.net/200000388065/product/8c57c1c4c34b413888eee3eadece4eb6_e22a93894c054b639fac866d3d6e3135_master.jpeg", "MARTIN X SERIES D-X1E SITKA SPRUCE ACOUSTIC GUITAR", 13900000);
let pd_4 = new Products(4, "https://product.hstatic.net/200000388065/product/7d825f2ee41746009b35c0e4c66c839f_7d1b708932da4af3b47830796e21e53d_master.jpg", "GUITAR PLUS F0 LIMITED AC", 15000000);
let pd_5 = new Products(5, "https://product.hstatic.net/200000388065/product/dan-guitar-martin-d10e-1_29900f80ff1640cda2cd0f816c07564d_master.gif", "GUITAR ACOUSTIC MARTIN D10E-02", 24000000);
let pd_6 = new Products(6, "https://product.hstatic.net/200000388065/product/67b6fe7cf983459fa1389bbdddc2b5bb_dd1e315fd6b547f189418db7dd78315b_master.jpeg", "MARTIN X SERIES GPC-X2E ACOUSTIC GUITAR", 18500000);
let pd_7 = new Products(7, "https://product.hstatic.net/200000388065/product/a_13c76ae6fcd24e168f61bcccf73e7058_master.png", "TAYLOR ACADEMY 10 DREADNOUGHT ACOUSTIC GUITAR NATURAL", 15500000);
let pd_8 = new Products(8, "https://product.hstatic.net/200000388065/product/4924143dff3846e8bd715338f699bea4_42f1b2234c6345d2983b10d7a643ab5d_master.jpeg", "GUITAR ĐIỆN SQUIER MM STRAT HT BLK", 4000000);
let pd_9 = new Products(9, "https://product.hstatic.net/200000388065/product/ab9906e714924d139e37e58937567ab1_a61f3895e81c4c6fa5a56181350e9810_1024x1024.jpeg", "J&D ST-01 STANDARD STRATOCASTER ELECTRIC GUITAR RED", 4000000);
let pd_10 = new Products(10, "https://product.hstatic.net/200000388065/product/squier-aff-tele-mn-bsb_4707d914a9124625bb0185ceaf5b3e35_master.jpeg", "GUITAR ĐIỆN FENDER SQUIER AFINITY SERIES™ TELECASTER® SPECIAL BUTTERSCOTCH BLONDE", 6500000);
let pd_11 = new Products(11, "https://product.hstatic.net/200000388065/product/6e78ccc3799548da93662814824f1a2f_d222f773b1a64ffc8af7d23811b350e1_master.jpeg", "GUITAR BASS CORT ACTION BASS PLUS TRANS RED", 6000000);
let pd_12 = new Products(12, "https://product.hstatic.net/200000388065/product/d2717f84adb146949ca05cd8e7e9415b_033ade0432f64a629143f75547e30271_master.jpg", "CORT BASS ACTION DLX V AS NATURAL 5-STRING", 9000000);

let listProducts = JSON.parse(localStorage.getItem("list_product"));
if (listProducts == null) {
    listProducts = [];
}
console.log(listProducts);
if (listProducts.length == 0) {
    listProducts.push(pd_1);
    listProducts.push(pd_2);
    listProducts.push(pd_3);
    listProducts.push(pd_4);
    listProducts.push(pd_5);
    listProducts.push(pd_6);
    listProducts.push(pd_7);
    listProducts.push(pd_8);
    listProducts.push(pd_9);
    listProducts.push(pd_10);
    listProducts.push(pd_11);
    listProducts.push(pd_12);
}

let listSearch;
function actionSearch() {
    let inputSearch = document.getElementById("inputSearch").value;
    if (inputSearch.trim() == "") {
        showProduct();
    } else {
        listSearch = [];
        for (let i = 0; i < listProducts.length; i++) {
            if (listProducts[i].name.toLowerCase().search(inputSearch.toLowerCase()) !== -1) {
                listSearch.push(listProducts[i]);
            }
        }
        showListSearch()
    }
}

function showListSearch() {
    let drawTableAdmin = "";
    for (let i = 0; i < listSearch.length; i++) {
        drawTableAdmin += `<tr>
                <td>
                    <img style="width: 80px"
                        src="${listSearch[i].img}"
                        alt="">
                    <p style="padding: 10px">${listSearch[i].name}</p>
                </td>
                <td>${formatNumber(listSearch[i].price)}</td>
                <td>
                <button onclick="openModal(${listProducts[i].id})" class="btn-modal-s">Edit</button>
                    <button onclick="deleteProduct(${listSearch[i].id})">Delete</button>
                </td>
            </tr>`;
    }
    document.getElementById("draw-table").innerHTML = drawTableAdmin;
}

localStorage.removeItem("list_product");
localStorage.setItem("list_product", JSON.stringify(listProducts));
    

function formatNumber(number) {
    return new Intl.NumberFormat("de-DE", {
        style: "currency",
        currency: "VND",
    }).format(number);
}