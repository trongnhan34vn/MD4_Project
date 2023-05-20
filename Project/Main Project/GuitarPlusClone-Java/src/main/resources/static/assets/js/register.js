let firstName;
let lastName;
let gender;
let birth;
let email;
let password;
let rePassword;
let checkValidate = false;
let listUser;

function getGender() {
    gender = document.querySelector('input[name = gender]:checked').value;
    console.log(typeof gender);
}


function validate() {
    firstName = document.getElementById('firstName').value;
    lastName = document.getElementById('lastName').value;
    // gender = document.querySelector('input[name = gender]:checked').value;
    birth = document.getElementById('birth').value;
    email = document.getElementById('email').value;
    password = document.getElementById('password').value;
    rePassword = document.getElementById('re-password').value;

    // Check Validate FName
    if (firstName.trim() !== '') {
        document.getElementById('validateFName').innerHTML = "";
        checkValidate = true;
    } else {
        checkValidate = false;
        document.getElementById('validateFName').innerHTML = "First Name is required!"
        return
    }
    // Check Validate LName
    if (lastName.trim() === '') {
        checkValidate = false;
        document.getElementById('validateLName').innerHTML = "Last Name is required!"
        return
    } else {
        document.getElementById('validateLName').innerHTML = "";
        checkValidate = true;
    }

    // Check Validate Email
    if (email.trim() === '') {
        document.getElementById('validateEmail').innerHTML = 'Email is required!'
        checkValidate = false;
        return;
    } else {
        if (!validateEmail(email)) {
            document.getElementById('validateEmail').innerHTML = 'Email is invalid'
            checkValidate = false;
            return;
        } else {
            document.getElementById('validateEmail').innerHTML = "";
            checkValidate = true;
        }
    }

    // Check Validate Password & Re-Password
    if (password.trim() == '') {
        checkValidate = false;
        document.getElementById('validatePassword').innerHTML = "Password is required!";
        return;
    } else {
        if (password.length < 8) {
            checkValidate = false;
            document.getElementById('validatePassword').innerHTML = "Use 8 or more characters for your password!";
            return;
        } else {
            checkValidate = true;
            document.getElementById('validatePassword').innerHTML = "";
        }
    }
    // Check Validate RePassword
    if (rePassword !== password) {
        checkValidate = false;
        document.getElementById('validateRePassword').innerHTML = "Re-Password is not matched!";
        return;
    } else {
        checkValidate = true;
        document.getElementById('validateRePassword').innerHTML = "";
    }
}

// let cartList = JSON.parse(localStorage.getItem('cartList'));
//
// console.log(cartList);
// Tạo hàm đăng kí
// function register() {
//     console.log(checkValidate);
//     if (checkValidate) {
//         let id = 0;
//         let user = new User(id, firstName, lastName, gender, birth, email, password, cartList);
//         if (listUser.length == 0) {
//             id = 1;
//             user.setID(id);
//             listUser.push(user);
//         } else {
//             user.setID(listUser[listUser.length - 1].id + 1);
//             listUser.push(user);
//         }
//         localStorage.removeItem('list_user');
//         localStorage.setItem('list_user', JSON.stringify(listUser));
//         location.href = 'login.html';
//     } else {
//         document.getElementById('validatePassword').innerHTML = "Please input the password!";
//         document.getElementById('validateRePassword').innerHTML = "Please input the re-password!";
//         document.getElementById('validateFName').innerHTML = "Please input the first name!";
//         document.getElementById('validateLName').innerHTML = "Please input the last name!";
//         document.getElementById('validateEmail').innerHTML = "Please input email!";
//     }
// }


// Check định dạng Email
function validateEmail(mail) {
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
        return (true);
    }
    // alert("You have entered an invalid email address!")
    return (false);
}